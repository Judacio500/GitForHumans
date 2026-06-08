package controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.UserDAO;
import db.DAOInterface.IUserDAO;
import model.UserBean;
import auxiliars.utils.SecurityUtil;

@WebServlet("/register")
public class RegisterController extends HttpServlet 
{
    private static final long serialVersionUID = 1L;
    private IUserDAO userDAO;

    @Override
    public void init() throws ServletException 
    {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String plainPassword = request.getParameter("password");

        // Hash the plain text password using BCrypt before setting it to the model
        String hashedPassword = SecurityUtil.hashPassword(plainPassword);
        UserBean newUser = new UserBean(name, email, hashedPassword);

        try 
        {
            boolean isInserted = userDAO.insert(newUser);

            if (isInserted) 
            {
                // Redirect to login page to complete the PRG pattern workflow
                response.sendRedirect(request.getContextPath() + "/login");
            } 
            else 
            {
                request.setAttribute("errorMessage", "Registration failed. Email might already be in use.");
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: Database Error during user registration");
        }
    }
}