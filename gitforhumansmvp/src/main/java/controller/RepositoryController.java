package controller;

import java.util.*;
import java.io.IOException;
import java.sql.SQLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auxiliars.utils.Git.GitService;
import db.MetadataDAO;
import db.RepositoryDAO;
import db.DAOInterface.IMetadataDAO;
import db.DAOInterface.IRepositoryDAO;
import model.MetadataBean;
import model.RepositoryBean;

@WebServlet("/repository")
public class RepositoryController extends HttpServlet 
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loggedUser") == null) 
        {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String idParam = request.getParameter("id"); 
        
        if (idParam == null || idParam.isEmpty()) 
        {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        UUID repoId = UUID.fromString(idParam);

        try
        {
            RepositoryBean repo = repositoryDAO.selectById(repoId);
            
            if (repo == null) 
            {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Repository not found");
                return;
            }
            
            List<MetadataBean> metadata = metadataDAO.selectByRepositoryId(repoId);

            String gitFolderPath = Paths.get(repo.getGit_path()).resolve("source_code").toString();
            
            List<Map<String, String>> commitHistory = new ArrayList<>();
            try 
            {
                commitHistory = GitService.getHistory(gitFolderPath);
            }
            catch (Exception e) 
            {
                System.err.println("No se pudo cargar el historial de Git: " + e.getMessage());
            }

            List<String> gitFiles = new ArrayList<>();
            Path sourceCodeFolder = Paths.get(repo.getGit_path()).resolve("source_code");
            
            try 
            {
                if (Files.exists(sourceCodeFolder)) 
                {
                    gitFiles = Files.list(sourceCodeFolder)
                                    .filter(p -> !p.toFile().isDirectory())
                                    .map(p -> p.getFileName().toString())
                                    .collect(Collectors.toList());
                }
            } 
            catch (IOException e) 
            {
                System.err.println("Error leyendo la carpeta source_code: " + e.getMessage());
            }

            request.setAttribute("repoMetaData", metadata);
            request.setAttribute("gitFiles", gitFiles); // 
            request.setAttribute("repository", repo);
            request.setAttribute("commitHistory", commitHistory);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: Database Error during query");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/repository.jsp").forward(request, response);
    }
}