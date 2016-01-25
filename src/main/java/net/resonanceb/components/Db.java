package net.resonanceb.components;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Db {

    /**
     * Execute a sql statement against a database.  Handles multiple paths to establish a connection based on the selection
     * parameter.  It can connect with a direct connection string or by a jndi lookup against an existing data source.
     * 
     * @param selection option to select to get a database connection
     * @param driver to use to establish a jdbc connection
     * @param connectionString connection string to establish the jdbc connection
     * @param jndi name to lookup to establish the jdbc connection
     * @param username to use to connect to the database
     * @param password to use to connect to the database
     * @param sql to have the database execute
     * @return the query results transformed into a {@link org.json.JSONObject}
     * @throws ClassNotFoundException
     * @throws org.json.JSONException
     * @throws javax.naming.NamingException
     * @throws java.sql.SQLException
     */
    public static JSONObject executeSql(String selection, String driver, String connectionString, String jndi, String username, String password, String sql)
            throws ClassNotFoundException, JSONException, NamingException, SQLException {
        // return object constructs
        JSONObject data = new JSONObject();
        JSONArray array = new JSONArray();

        // connection variables (must be closed)
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // establish a connection
            if ("jndiselection".equals(selection)) {
                conn = getConnection(jndi);
            } else if ("connstringselection".equals(selection)) {
                conn = getConnection(driver, connectionString, username, password);
            }

            // execute the sql
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            // get results data to help organize
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();

            // Convert the result set into a json structure
            while (rs.next()) {
                JSONObject obj = new JSONObject();

                for (int i = 1; i <= cols; ++i) {
                    String columnName = rsmd.getColumnName(i);

                    // Add individual columns to the json object
                    // Try to see if the value is in a json format first
                    try {
                        JSONObject intermediateJson = new JSONObject(rs.getString(columnName));
                        obj.put(columnName, intermediateJson);
                    } catch (Exception e) {
                        // Catching Exception is bad and I generally frown upon this practice of try/catch
                        // to do logic, but I really do not have another way to validate json format and
                        // act if that fails
                        obj.put(columnName, rs.getString(columnName));
                    }
                }

                array.put(obj);
            }

        } finally {
            // close all the things
            if (rs != null) {
                rs.close();
            }

            if (stmt != null) {
                stmt.close();
            }

            if (conn != null) {
                conn.close();
            }

            // put whatever results we have into the return object
            data.put("sqlresults", array);
        }

        return data;
    }

    /**
     * Gets a {@link java.sql.Connection} to a database from a straight up connection string.
     * 
     * @param driver JDBC driver to lookup for connection
     * @param connectionString JDBC connection string
     * @param username user to connect to database as
     * @param password password for user
     * @return {@link java.sql.Connection} to the desired database
     * @throws ClassNotFoundException
     * @throws java.sql.SQLException
     */
    private static Connection getConnection(String driver, String connectionString, String username, String password)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);

        return DriverManager.getConnection(connectionString, username, password);
    }

    /**
     * Establishes a {@link java.sql.Connection} to a database from a jndi lookup.
     * 
     * @param jndi naming string database resides at
     * @return {@link java.sql.Connection} to the desired database
     * @throws javax.naming.NamingException
     * @throws java.sql.SQLException
     */
    private static Connection getConnection(String jndi) throws NamingException, SQLException {
        Context context = getInitialContext();
        DataSource ds = (DataSource)context.lookup(jndi);

        return ds.getConnection();
    }

    /**
     * Establishes a {@link javax.naming.Context} to perform jndi lookups.
     * 
     * @return {@link javax.naming.Context} to perform jndi lookups against
     * @throws javax.naming.NamingException
     */
    private static Context getInitialContext() throws NamingException {
        Hashtable<String, String> env = new Hashtable<>();

        return new InitialContext(env);
    }
}