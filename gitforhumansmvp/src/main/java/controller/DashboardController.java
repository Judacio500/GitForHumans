package controller;

import java.io.IOException;
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

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet 
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
        // Fetch session WITHOUT creating a new one if it doesn't exist
        HttpSession session = request.getSession(false);

        // Access Control: Redirect to login if session is null or user is missing
        if (session == null || session.getAttribute("loggedUser") == null) 
        {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        UserBean loggedUser = (UserBean) session.getAttribute("loggedUser");
        UUID userId;
        
        if (loggedUser != null) 
        {
            userId = loggedUser.getIdUser();
        } 
        else 
        {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Since selectByCreatorId is a query and throws a SQL exception
        // we have to catch it here in the upper level
        try
        {
            List<RepositoryBean> repos = repositoryDAO.selectByCreatorId(userId);

            request.setAttribute("repositoryList", repos);
        } 
        catch(SQLException e)
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: Database Error during repository query");
        }
        // Forward to the secure view
        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }
}