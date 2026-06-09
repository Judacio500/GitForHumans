package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.RepositoryDAO;
import db.DAOInterface.IRepositoryDAO;
import model.RepositoryBean;
import model.UserBean;

@WebServlet("/NewRepository")
public class NewRepositoryController extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private IRepositoryDAO repositoryDAO;
    
    @Override
    public void init() throws ServletException 
    {
        repositoryDAO = new RepositoryDAO();
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
        UUID userId = loggedUser.getIdUser();

        String repoName = request.getParameter("repositoryName");
        String description = request.getParameter("description");

        String baseStoragePath = System.getenv("VAULT_STORAGE_PATH");

        if (baseStoragePath == null || baseStoragePath.isEmpty()) 
        {
            baseStoragePath = System.getProperty("user.home") + "/CodeVaultRepos";
        }

        Path rootPath = Paths.get(baseStoragePath, repoName);
        Path gitPath = rootPath.resolve("source_code");
        Path lfsPath = rootPath.resolve("lfs_vault");

        try 
        {
            Files.createDirectories(gitPath);
            Files.createDirectories(lfsPath);

            RepositoryBean newRepo = new RepositoryBean();
            newRepo.setName(repoName);
            newRepo.setDescription(description);
            newRepo.setIdCreator(userId);
            newRepo.setGit_path(rootPath.toString()); 
            
            try
            {
                repositoryDAO.insert(newRepo);
        
                response.sendRedirect(request.getContextPath() + "/dashboard");
            }
            catch(SQLException e)
            {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: Database Error Inserting the Repository");
            }
            
        } 
        catch(IOException e) 
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el sistema de archivos");
            return;
        }
    }
    
}
