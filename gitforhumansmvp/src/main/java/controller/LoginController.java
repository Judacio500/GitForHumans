package controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.UserDAO;
import db.DAOInterface.IUserDAO;
import model.UserBean;
import auxiliars.utils.SecurityUtil;

@WebServlet("/login")
public class LoginController extends HttpServlet 
{
    private static final long serialVersionUID = 1L;

    // Declare the variable using the "what", not the "how"
    // this means that if in the future the specific DAO changes
    // we don't have to change every ocurrence of this variable
    private IUserDAO userDAO;

    @Override
    public void init() throws ServletException 
    {
        // Instantiate the DAO just once
        // when the servlet initializes
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try
        {
            UserBean user = userDAO.selectByEmail(email);
            if(user != null && SecurityUtil.verifyPassword(password, user.getPassword()))
            {
                HttpSession session = request.getSession(true);

                session.setAttribute("loggedUser", user);

                response.sendRedirect(request.getContextPath() + "/dashboard");
            }
            else
            {
                request.setAttribute("errorMessage", "Invalid email or password.");
                
                request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            }

        } 
        catch (SQLException e) 
        {
            e.printStackTrace(); 
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: Database Error");
        }
    }
}