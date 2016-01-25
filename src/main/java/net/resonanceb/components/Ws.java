package net.resonanceb.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

public class Ws {

    public static void main(String[] args) throws Exception {

        String endpoint = "http://localhost:7001/hello-ws/hello";
        String request = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
                + "<SOAP-ENV:Body>"
                + "<m:message xmlns:m=\"urn:my.awesome.namespace\">NAAAAMMMMEEE</m:message>"
                + "</SOAP-ENV:Body>"
                +"</SOAP-ENV:Envelope>";

        sendSoap(endpoint, request);
    }

    /**
     * Send the fully constructed provided soap message to the provided web service endpoint.
     * 
     * @param endpoint web service endpoint
     * @param request soap request message
     * @return {@link org.json.JSONObject} that is the soap response from the soap request
     * @throws java.io.IOException
     * @throws org.json.JSONException
     */
    public static JSONObject sendSoap(String endpoint, String request) throws IOException, JSONException {
        JSONObject data = new JSONObject();

        // Convert the input string to a byte array
        byte[] input = request.getBytes();

        // Create the connection
        URL url = new URL(endpoint);
        URLConnection connection = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection)connection;

        // Setup the HTTP parameters
        httpConn.setRequestProperty("Content-Length", String.valueOf(input.length));
        httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);

        // Send the XML
        OutputStream out = httpConn.getOutputStream();
        out.write(input);
        out.close();

        // Read the response
        InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
        BufferedReader in = new BufferedReader(isr);
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            response.append(line);
        }

        data.put("response", response);

        return data;
    }
}