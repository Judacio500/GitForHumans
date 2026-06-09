package controller;

import java.util.*;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        UUID repoId = UUID.fromString(idParam);

        try
        {
            RepositoryBean repo = repositoryDAO.selectById(repoId);
            List<MetadataBean> metadata = metadataDAO.selectByRepositoryId(repoId);

            request.setAttribute("repoMetaData", metadata);
            request.setAttribute("repository", repo);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: Database Error during query");
        }

        request.getRequestDispatcher("/WEB-INF/views/repository.jsp").forward(request, response);
    }
}