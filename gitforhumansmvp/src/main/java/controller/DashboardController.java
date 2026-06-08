package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet 
{
    private static final long serialVersionUID = 1L;

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

        // Forward to the secure view
        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }
}