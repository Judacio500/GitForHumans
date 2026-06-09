package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.RepositoryDAO;
import db.DAOInterface.IRepositoryDAO;
import model.RepositoryBean;

@WebServlet("/Viewer")
public class ViewerController extends HttpServlet 
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
        String fileName = request.getParameter("file");

        if (repoIdStr == null || fileName == null) 
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

            Path filePath = Paths.get(repo.getGit_path()).resolve("source_code").resolve(fileName);

            if (!Files.exists(filePath)) 
            {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
                return;
            }

            String fileContent = new String(Files.readAllBytes(filePath), "UTF-8");

            request.setAttribute("fileName", fileName);
            request.setAttribute("fileContent", fileContent);
            request.setAttribute("repositoryId", repoIdStr);

            request.getRequestDispatcher("/WEB-INF/views/viewer.jsp").forward(request, response);

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }
}