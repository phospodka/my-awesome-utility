package net.resonanceb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import net.resonanceb.util.LoginUtil;

/**
 * Servlet implementation class Whoami
 */
@WebServlet("/whoami")
public class WhoamiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public WhoamiServlet() {
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

        JSONObject data = new JSONObject();

        try {
            data = LoginUtil.getLoginData(request);
        } catch (JSONException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            request.getSession().setAttribute("error", "Error attempting to find who I am.\n" + sw.toString());
        } finally {
            request.getSession().setAttribute("data", data);
            request.getSession().setAttribute("new", true);
            response.sendRedirect(view);
        }
    }
}