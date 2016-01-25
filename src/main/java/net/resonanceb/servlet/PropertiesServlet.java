package net.resonanceb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import net.resonanceb.util.FileUtil;

/**
 * Servlet implementation class EnvironmentPropertiesServlet
 */
@WebServlet("/properties")
public class PropertiesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PropertiesServlet() {
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
            // Get the system properties
            JSONObject sys = new JSONObject();
            Properties props = System.getProperties();
            Enumeration<Object> keys = props.keys();

            while(keys.hasMoreElements()) {
                String key = keys.nextElement().toString();
                sys.put(key, props.getProperty(key));
            }

            // Get the application specific properties
            JSONObject app = new JSONObject();
            props = FileUtil.loadProperties();
            keys = props.keys();

            while(keys.hasMoreElements()) {
                String key = keys.nextElement().toString();
                app.put(key, props.getProperty(key));
            }

            // add all the properties to the json return object
            data.put("systemprops", sys);
            data.put("appprops", app);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            request.getSession().setAttribute("error", "Error attempting to gather environment properties.\n" + sw.toString());
        } finally {
            request.getSession().setAttribute("data", data);
            request.getSession().setAttribute("new", true);
            response.sendRedirect(view);
        }
    }
}