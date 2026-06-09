package controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import auxiliars.utils.PropertyReader;
import auxiliars.utils.Git.GitService;
import db.MetadataDAO;
import db.RepositoryDAO;
import db.DAOInterface.IMetadataDAO;
import db.DAOInterface.IRepositoryDAO;
import model.MetadataBean;
import model.RepositoryBean;
import model.UserBean;

@WebServlet("/UploadAsset")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 100,      // 100MB max por archivo
    maxRequestSize = 1024 * 1024 * 150    // 150MB max por petición
)
public class UploadController extends HttpServlet 
{
    private static final long serialVersionUID = 1L;

    private IRepositoryDAO repositoryDAO;
    private IMetadataDAO metadataDAO;

    @Override
    public void init() throws ServletException 
    {
        repositoryDAO = new RepositoryDAO();
        metadataDAO = new MetadataDAO();
    }

@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loggedUser") == null) 
        {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        UserBean loggedUser = (UserBean) session.getAttribute("loggedUser");
        String repoIdStr = request.getParameter("repositoryId");
        Part filePart = request.getPart("fileUpload");
        String bulletPoints = request.getParameter("bulletPoints");

        String fileName = (filePart != null) ? extractFileName(filePart) : "";

        if (repoIdStr == null || filePart == null || fileName.isEmpty()) 
        {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        UUID repoId = UUID.fromString(repoIdStr);
        long fileSize = filePart.getSize();

        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) 
        {
            extension = fileName.substring(i + 1).toLowerCase();
        }

        String allowedExts = PropertyReader.getProperty("source.properties", "source.extensions");
        List<String> codeExtensions = Arrays.asList(allowedExts.split(","));
        boolean isSourceCode = codeExtensions.contains(extension);

        try 
        {
            RepositoryBean repo = repositoryDAO.selectById(repoId);

            if (repo == null) 
            {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Repository not found");
                return;
            }

            Path rootPath = Paths.get(repo.getGit_path());
            Path targetFilePath;

            if (isSourceCode)
            {
                targetFilePath = rootPath.resolve("source_code").resolve(fileName);
            }
            else
            {
                targetFilePath = rootPath.resolve("lfs_vault").resolve(fileName);
            }

            // 2. Escritura Física Incondicional
            try (InputStream fileContent = filePart.getInputStream()) 
            {
                Files.copy(fileContent, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error escribiendo el archivo");
                return;
            }

            if (isSourceCode)
            {
                try
                {
                    String gitFolderPath = rootPath.resolve("source_code").toString();
                    String commitMsg = "Auto-commit: " + fileName;
                    
                    if (bulletPoints != null && !bulletPoints.trim().isEmpty()) 
                    {
                        commitMsg += " | " + bulletPoints.replace("\n", " ");
                    }
                    
                    String dummyEmail = loggedUser.getName().replaceAll("\\s+", "").toLowerCase() + "@codevault.local";
                    GitService.autoCommit(gitFolderPath, commitMsg, loggedUser.getName(), dummyEmail);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: Git tracking failed");
                    return;
                }
            }
            else
            {
                if (bulletPoints != null && !bulletPoints.trim().isEmpty())
                {
                    Path changelogPath = rootPath.resolve("lfs_vault").resolve(fileName + "_changelog.txt");
                    String logEntry = "--- Cambio por " + loggedUser.getName() + " ---\n" + bulletPoints + "\n\n";
                    Files.write(changelogPath, logEntry.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }

                // Verificación de duplicados para la UI
                List<MetadataBean> existingFiles = metadataDAO.selectByRepositoryId(repoId);
                boolean isDuplicate = false;
                
                if (existingFiles != null) 
                {
                    for (MetadataBean mb : existingFiles) 
                    {
                        if (mb.getFileName().equals(fileName)) 
                        {
                            isDuplicate = true;
                            break;
                        }
                    }
                }

                if (!isDuplicate) 
                {
                    MetadataBean metadata = new MetadataBean();
                    metadata.setIdRepository(repoId);
                    metadata.setFileName(fileName);
                    metadata.setByteSize(fileSize);
                    metadata.setFilePath(targetFilePath.toString());
                    metadata.setIdUserUpload(loggedUser.getIdUser());
                    metadata.setChangeType("Upload");

                    try 
                    {
                        metadataDAO.insert(metadata);
                    }
                    catch (SQLException e) 
                    {
                        e.printStackTrace();
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error insertando metadata");
                        return;
                    }
                }
            }

            response.sendRedirect(request.getContextPath() + "/repository?id=" + repoId.toString());

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error de infraestructura BD");
        }
    }

    private String extractFileName(Part part) 
    {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) 
        {
            if (s.trim().startsWith("filename")) 
            {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}