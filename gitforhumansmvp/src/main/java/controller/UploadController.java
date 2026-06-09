package controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import db.MetadataDAO;
import db.RepositoryDAO;
import db.DAOInterface.IMetadataDAO;
import db.DAOInterface.IRepositoryDAO;
import model.MetadataBean;
import model.RepositoryBean;

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

        String repoIdStr = request.getParameter("repositoryId");
        Part filePart = request.getPart("fileUpload");

        if (repoIdStr == null || filePart == null || filePart.getSubmittedFileName().isEmpty()) 
        {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        UUID repoId = UUID.fromString(repoIdStr);
        String fileName = filePart.getSubmittedFileName();
        long fileSize = filePart.getSize();

        try 
        {
            RepositoryBean repo = repositoryDAO.selectById(repoId);

            if (repo == null) 
            {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Repository not found");
                return;
            }

            Path rootPath = Paths.get(repo.getGit_path());
            Path lfsPath = rootPath.resolve("lfs_vault");
            Path targetFilePath = lfsPath.resolve(fileName);

            try (InputStream fileContent = filePart.getInputStream()) 
            {
                Files.copy(fileContent, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error escribiendo el archivo en el disco");
                return;
            }

            MetadataBean metadata = new MetadataBean();
            metadata.setIdRepository(repoId);
            metadata.setFileName(fileName);
            metadata.setByteSize(fileSize);
            metadata.setFilePath(targetFilePath.toString());

            try 
            {
                metadataDAO.insert(metadata);
            
                response.sendRedirect(request.getContextPath() + "/repository?id=" + repoId.toString());
            }
            catch (SQLException e) 
            {
                e.printStackTrace();
                Files.deleteIfExists(targetFilePath);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: registering metadata in the DB");
            }

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: With the infrastructure query");
        }
    }
}