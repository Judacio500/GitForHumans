package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.RepositoryDAO;
import db.DAOInterface.IRepositoryDAO;
import model.RepositoryBean;

@WebServlet("/DownloadVault")
public class DownloadVaultController extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private IRepositoryDAO repositoryDAO;

    @Override
    public void init() throws ServletException 
    {
        repositoryDAO = new RepositoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) 
        {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String repoIdStr = request.getParameter("repositoryId");
        if (repoIdStr == null || repoIdStr.isEmpty()) 
        {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        try 
        {
            UUID repoId = UUID.fromString(repoIdStr);
            RepositoryBean repo = repositoryDAO.selectById(repoId);

            if (repo == null) 
            {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Repository not found");
                return;
            }

            File repoDir = Paths.get(repo.getGit_path()).toFile();

            if (!repoDir.exists()) 
            {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Vault folder not found on server");
                return;
            }

            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + repo.getName() + "_clone.zip\"");

            try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) 
            {
                zipDirectory(repoDir, repo.getName(), zos);
            }

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error finding repository");
        }
    }

    private void zipDirectory(File folder, String parentFolder, ZipOutputStream zos) throws IOException 
    {
        File[] files = folder.listFiles();
        if (files == null) return;

        for (File file : files) 
        {
            if (file.isDirectory()) 
            {
                zipDirectory(file, parentFolder + "/" + file.getName(), zos);
                continue;
            }
            
            try (FileInputStream fis = new FileInputStream(file)) 
            {
                ZipEntry zipEntry = new ZipEntry(parentFolder + "/" + file.getName());
                zos.putNextEntry(zipEntry);

                byte[] buffer = new byte[4096];
                int length;
                while ((length = fis.read(buffer)) > 0) 
                {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
            }
        }
    }
}