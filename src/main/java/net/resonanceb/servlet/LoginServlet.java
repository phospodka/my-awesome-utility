package net.resonanceb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import net.resonanceb.util.LoginUtil;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String view = request.getContextPath()  + "/";

        //user credentials
        String username = request.getParameter("j_username");
        String password = request.getParameter("j_password");

        try {
            // perform the programmatic login
            request.login(username, password);

            // add the sql data to the json return object
            JSONObject data = LoginUtil.getLoginData(request);

            // add the session data here, as we only want error data if it failed
            request.getSession().setAttribute("data", data);
            request.getSession().setAttribute("loggedin", true);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            request.getSession().setAttribute("error", "Error attempting to authenticate.\n" + sw.toString());

            // if an exception occurred, set the view to login page
            view = view + "/login.jsp";
        } finally {
            request.getSession().setAttribute("new", true);
            response.sendRedirect(view);
        }
    }
}