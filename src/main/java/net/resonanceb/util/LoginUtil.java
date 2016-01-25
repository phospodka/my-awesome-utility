package net.resonanceb.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginUtil {
    /**
     * Determine which roles a user belongs to by inspecting the {@link HttpServletRequest} and
     * set of known roles.
     * 
     * @param request {@link HttpServletRequest} to pull info from
     * @return {@link org.json.JSONArray} of roles user is in
     * @throws java.io.IOException
     */
    public static JSONArray parseGroups(HttpServletRequest request) throws IOException {
        JSONArray roles = new JSONArray();

        String rolestring = FileUtil.getProperty("authorization.roles.list");
        Set<String> setroles = new HashSet<>(Arrays.asList(rolestring.split(",")));

        for (String role : setroles) {
            if (request.isUserInRole(role)) {
                roles.put(role);
            }
        }

        return roles;
    }

    /**
     * Get the subject data about a user.
     * 
     * @param request {@link HttpServletRequest} to pull info from
     * @return {@link org.json.JSONObject} of users login data
     * @throws java.io.IOException
     * @throws org.json.JSONException
     */
    public static JSONObject getLoginData(HttpServletRequest request) throws IOException, JSONException{
        JSONObject data = new JSONObject();
        data.put("principal", (request.getUserPrincipal() != null ?request.getUserPrincipal().getClass().toString() : JSONObject.NULL));
        data.put("username", (request.getRemoteUser() != null ? request.getRemoteUser() : JSONObject.NULL));
        data.put("roles", LoginUtil.parseGroups(request));

        return data;
    }
}