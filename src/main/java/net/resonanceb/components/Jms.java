package net.resonanceb.components;

import java.util.Date;
import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.resonanceb.cache.JmsCache;
import org.json.JSONObject;

/**
 * reference http://oreilly.com/catalog/javmesser/chapter/ch02.html
 */
public class Jms implements MessageListener, ExceptionListener {

    private Session pubSession;
    private Session subSession;
    private MessageConsumer consumer;
    private MessageProducer producer;
    private Connection connection;

    @SuppressWarnings("unused") //TODO not entirely sure what I want to do with this
    private String username;

    /**
     * Constructor that sets up an starts the {@link Connection} and {@link Session} so that jms
     * communication be begin.
     * 
     * @param factory connection factory jndi name
     * @param selection queue or topic toggle
     * @param topic jndi name
     * @param queue jndi name
     * @param username to connect with
     * @param password to connect with
     * @throws JMSException
     * @throws javax.naming.NamingException
     */
    public Jms(String factory, String selection, String topic, String queue, String username, String password)
            throws JMSException, NamingException {
        // Get initial context
        Context context = getInitialContext();

        Connection connection = null;

        if ("topicselection".equals(selection)) {
            connection = initTopic(context, factory, topic, username, password);
        } else if("queueselection".equals(selection)) {
            connection = initQueue(context, factory, queue, username, password);
        }

        // Start the JMS connection; allows messages to be delivered
        if (connection != null) {
            connection.start();
        }
    }

    /**
     * Initialize the {@link TopicConnection} and {@link TopicSession} to be able to talk to the {@link Topic}.
     * 
     * @param context {@link javax.naming.Context} to perform lookups
     * @param factory connection factory jndi name
     * @param jndi topic jndi name
     * @param username to connect to topic with
     * @param password to connect to topic with
     * @return jms {@link Connection}
     * @throws JMSException
     * @throws javax.naming.NamingException
     */
    private Connection initTopic(Context context, String factory, String jndi, String username, String password)
            throws JMSException, NamingException {
        // Lookup JMS connection factory
        TopicConnectionFactory conFactory = (TopicConnectionFactory)context.lookup(factory);

        TopicConnection connection;

        // Create connection to JMS topic factory
        if (username == null || "".equals(username) || password == null || "".equals(password)) {
            username = null;
            connection = conFactory.createTopicConnection();
        } else {
            connection = conFactory.createTopicConnection(username, password);
        }

        // Create two JMS session objects
        TopicSession pubSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        TopicSession subSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        // Lookup JMS topic
        Topic topic = (Topic)context.lookup(jndi);

        // Create a JMS publisher and subscriber
        TopicPublisher publisher = pubSession.createPublisher(topic);
        TopicSubscriber subscriber = subSession.createSubscriber(topic);

        // Initialize the application
        set(connection, pubSession, subSession, publisher, subscriber, username);

        return connection;
    }

    /**
     * Initialize the {@link QueueConnection} and {@link QueueSession} to be able to talk to the {@link Queue}.
     * 
     * @param context {@link javax.naming.Context} to perform lookups
     * @param factory connection factory jndi name
     * @param jndi queue jndi name
     * @param username to connect to queue with
     * @param password to connect to queue with
     * @return jms {@link Connection}
     * @throws JMSException
     * @throws javax.naming.NamingException
     */
    private Connection initQueue(Context context, String factory, String jndi, String username, String password)
            throws JMSException, NamingException {
        // Lookup JMS connection factory
        QueueConnectionFactory conFactory = (QueueConnectionFactory)context.lookup(factory);

        QueueConnection connection;

        // Create connection to JMS queue factory
        if (username == null || "".equals(username) || password == null || "".equals(password)) {
            username = null;
            connection = conFactory.createQueueConnection();
        } else {
            connection = conFactory.createQueueConnection(username, password);
        }

        // Create two JMS session objects
        QueueSession pubSession = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        QueueSession subSession = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

        // Lookup JMS queue
        Queue queue = (Queue)context.lookup(jndi);

        // Create a JMS publisher and subscriber
        QueueSender publisher = pubSession.createSender(queue);
        QueueReceiver subscriber = subSession.createReceiver(queue);

        // Initialize the application
        set(connection, pubSession, subSession, publisher, subscriber, username);

        return connection;
    }

    /**
     * Set some global variables in a global way.
     * 
     * @param con {@link Connection}
     * @param pubSess {@link Session}
     * @param subSess {@link Session}
     * @param pub {@link MessageProducer}
     * @param sub {@link MessageConsumer}
     * @param user {@link String}
     */
    public void set(Connection con, Session pubSess, Session subSess, MessageProducer pub, MessageConsumer sub, String user) {
        connection = con;
        pubSession = pubSess;
        subSession = subSess;
        producer = pub;
        consumer = sub;
        username = user;
    }

    /**
     * Let us know when there is an exception.
     */
    @Override
    public void onException(JMSException e) {
        System.err.println("an error has occurred in onException: " + e);
        e.printStackTrace();
    }

    /**
     * Handle jms message receive.
     * 
     * @param message {@link Message} that was received
     */
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage)message;

            JSONObject json = new JSONObject();
            json.put("destination", textMessage.getJMSDestination());
            json.put("id", textMessage.getJMSMessageID());
            json.put("text", textMessage.getText());
            json.put("time", new Date(textMessage.getJMSTimestamp()));

            JmsCache.add(json);
        } catch (Exception e) {
            System.err.println("an error has occurred in onMessage: " + e);
            e.printStackTrace();
        }
    }

    /**
     * Set a JMS message listener for the {@link Topic} or {@link Queue}.
     * 
     * @throws JMSException
     */
    public void startListener() throws JMSException {
        if (consumer instanceof TopicSubscriber) {
            TopicSubscriber subscriber = (TopicSubscriber)consumer;
            subscriber.setMessageListener(this);
        } else if (consumer instanceof QueueReceiver) {
            QueueReceiver receiver = (QueueReceiver)consumer;
            receiver.setMessageListener(this);
        }
    }

    /**
     * Write a message to the object configure {@link Topic} or {@link Queue}.
     * 
     * @param text to include in the message
     * @throws JMSException
     */
    public String writeMessage(String text) throws JMSException {
        String destination = null;

        TextMessage message = pubSession.createTextMessage();
        message.setText(text);

        if (producer instanceof TopicPublisher) {
            TopicPublisher publisher = (TopicPublisher)producer;
            publisher.publish(message);
            destination = publisher.getDestination().toString();
        } else if(producer instanceof QueueSender) {
            QueueSender sender = (QueueSender)producer;
            sender.send(message);
            destination = sender.getDestination().toString();
        }

        return destination;
    }

    /**
     * Close the {@link Connection} and {@link Session}.
     * 
     * @throws JMSException
     */
    public void close() throws JMSException {
        if (pubSession != null) {
            pubSession.close();
        }

        if (subSession != null) {
            subSession.close();
        }

        if (connection != null) {
            connection.close();
        }
    }

    /**
     * Establishes a {@link javax.naming.Context} to perform jndi lookups.
     * 
     * @return {@link javax.naming.Context} to perform jndi lookups against
     * @throws javax.naming.NamingException
     */
    private static Context getInitialContext() throws NamingException {
        Hashtable<String, String> env = new Hashtable<>();
        //env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        //env.put(Context.PROVIDER_URL, "t3://localhost:7001");

        return new InitialContext(env);
    }
}