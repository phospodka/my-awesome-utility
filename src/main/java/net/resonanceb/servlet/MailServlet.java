package net.resonanceb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.resonanceb.components.Mail;
import org.json.JSONObject;

/**
 * Servlet implementation class MailTest
 */
@WebServlet("/mailit")
public class MailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MailServlet() {
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
        String view = request.getContextPath()  + "/mail.jsp";

        // Mail config data
        String server = request.getParameter("server");
        String from = request.getParameter("from");

        // Mail content data
        String to = request.getParameter("to");
        String subject = request.getParameter("subject");
        String body = request.getParameter("body");

        JSONObject data = new JSONObject();

        try {
            // add the mail data to the json return object
            // we set the details here to have it available should the transmit recieve an error
            data.put("date", "Attempted email @ " + new Date());
            data.put("to", to);
            data.put("from",from);
            data.put("subject", subject);
            data.put("body", body);
            data.put("server", server);
            data.put("success", false);

            // reset success to the return of sendMail if there is no exception!
            data = data.put("success", Mail.sendMail(server, from, to, subject, body));
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            request.getSession().setAttribute("error", "Error emailing message.\n" + sw.toString());
        } finally {
            request.getSession().setAttribute("data", data);
            request.getSession().setAttribute("new", true);
            response.sendRedirect(view);
        }
    }
}