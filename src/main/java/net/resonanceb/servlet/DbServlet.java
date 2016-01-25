package net.resonanceb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.resonanceb.components.Db;
import org.json.JSONObject;

/**
 * Servlet implementation class DbServlet
 */
@WebServlet("/dbit")
public class DbServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DbServlet() {
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
        String view = request.getContextPath()  + "/db.jsp";

        // connection config
        String dbselection = request.getParameter("dbselection");
        String connstring = request.getParameter("connstring");
        String driver = request.getParameter("driver");
        String jndi = request.getParameter("jndi");

        // connection credentials
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // sql input
        String sql = request.getParameter("sql");

        JSONObject data = new JSONObject();

        try {
            // add the sql data to the json return object
            data = Db.executeSql(dbselection, driver, connstring, jndi, username, password, sql);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            request.getSession().setAttribute("error", "Error attempting to execute sql.\n" + sw.toString());
        } finally {
            request.getSession().setAttribute("data", data);
            request.getSession().setAttribute("new", true);
            response.sendRedirect(view);
        }
    }
}