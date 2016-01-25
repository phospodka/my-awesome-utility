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

import net.resonanceb.cache.JmsCache;
import org.json.JSONObject;

import net.resonanceb.components.Jms;

/**
 * Servlet implementation class JmsSendServlet
 */
@WebServlet("/jmssendit")
public class JmsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public JmsServlet() {
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
        String view = request.getContextPath()  + "/jms.jsp";

        // connection config
        String factory = request.getParameter("factory");
        String selection = request.getParameter("jmsselection");
        String topic = request.getParameter("topic");
        String queue = request.getParameter("queue");

        // connection credentials
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // jms action
        String action = request.getParameter("jmsactionselection");
        String delay = request.getParameter("delay");
        String message = request.getParameter("message");

        JSONObject data = new JSONObject();

        try {
            Jms jms = new Jms(factory, selection, topic, queue, username, password);

            if ("jmsreceive".equals(action)) {
                // start the listener to be able to receive messages
                jms.startListener();

                // delay before the close so we can actually have a poll time
                delay = (delay == null || "".equals(delay) ? "0" : delay);
                Thread.sleep(Long.parseLong(delay) * 1000);

                // add current cache of message to the return
                data = data.put("recievedmessages", JmsCache.getCacheAsJson());
            } else if ("jmssend".equals(action)) {
                // write the message to the destination
                String destination = jms.writeMessage(message);

                // put helpful data in the return
                data.put("status", "jms message successfully sent");
                data.put("message", message);
                data.put("date", new Date());
                data.put("destination", destination);
            } else if ("jmsclear".equals(action)) {
                // clear the cache
                JmsCache.clear();

                // put something in the return
                data.put("status", "jms cache of messages cleared");
            }

            // close the jms connection and sessions
            jms.close();
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            request.getSession().setAttribute("error", "Error attempting to perform jms action.\n" + sw.toString());
        } finally {
            request.getSession().setAttribute("data", data);
            request.getSession().setAttribute("new", true);
            response.sendRedirect(view);
        }
    }
}