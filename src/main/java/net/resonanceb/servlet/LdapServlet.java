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

import net.resonanceb.components.Ldap;

/**
 * Servlet implementation class LdapServlet
 */
@WebServlet("/ldapit")
public class LdapServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LdapServlet() {
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
        String view = request.getContextPath()  + "/ldap.jsp";

        // connection config
        String url = request.getParameter("url");
        String userObject = request.getParameter("userobject");
        String userBase = request.getParameter("userbase");
        String groupBase = request.getParameter("groupbase");

        // connection credentials
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // search selection
        String searchselection = request.getParameter("searchselection");
        String search = request.getParameter("search");

        JSONObject data = new JSONObject();

        try {
            // add the ldap data to the json return object
            data = Ldap.performLdapSearch(username, password, url, userObject, userBase, groupBase, searchselection, search);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            request.getSession().setAttribute("error", "Error attempting to perform ldap search.\n" + sw.toString());
        } finally {
            request.getSession().setAttribute("data", data);
            request.getSession().setAttribute("new", true);
            response.sendRedirect(view);
        }
    }
}