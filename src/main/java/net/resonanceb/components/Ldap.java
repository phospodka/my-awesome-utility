package net.resonanceb.components;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Ldap {

    /**
     * Performs a ldap search against the provided url and using the provided connection info.  Takes an argument to
     * select from a category of search options and a search filter that is constrained by the selected category.
     * 
     * @param username bind username to connect to ldap with
     * @param password bind password to connect to ldap with
     * @param url full ldap connection url
     * @param userObject user object class (i.e. cn)
     * @param userBase user bind string
     * @param groupBase group/role bind string
     * @param option search option to perform
     * @param search search string to filter results
     * @return {@link org.json.JSONObject}
     * @throws javax.naming.NamingException
     * @throws org.json.JSONException
     */
    public static JSONObject performLdapSearch(String username, String password, String url, String userObject, String userBase,
            String groupBase, String option, String search) throws NamingException, JSONException {
        // return object
        JSONObject rval = new JSONObject();

        // ldap search filter
        String filter = null;

        // get ldap context for jndi look
        LdapContext context = getInitialContext(username, password, url, userObject, userBase);

        try {
            SearchControls sc = new SearchControls();
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

            if ("usersearch".equals(option)) {
                filter = "cn=" + search;
            }
            else if ("groupsearch".equals(option)) {
                String[] attributeFilter = {"cn", "uniquemember" };
                sc.setReturningAttributes(attributeFilter);
                filter = "cn=" + search;
            }
            else if ("membershipsearh".equals(option)) {
                String[] attributeFilter = {"cn"};
                sc.setReturningAttributes(attributeFilter);
                filter = "(&(cn=*)(uniquemember=cn=" + search + "," + userBase + "))";
            }

            NamingEnumeration<SearchResult> results = context.search("", filter, sc);
            rval = transformToJson(results);
        } finally {
            context.close();
        }

        return rval;
    }

    /**
     * Transforms a {@link javax.naming.NamingEnumeration<javax.naming.directory.SearchResult>} into a {@link org.json.JSONObject}
     * 
     * @param results {@link javax.naming.NamingEnumeration<javax.naming.directory.SearchResult>}
     * @return {@link org.json.JSONObject} representation of search results
     * @throws org.json.JSONException
     * @throws javax.naming.NamingException
     */
    private static JSONObject transformToJson(NamingEnumeration<SearchResult> results) throws JSONException, NamingException {
        JSONObject rval = new JSONObject();
        JSONArray resultArray = new JSONArray();

        // break it down!
        if (results.hasMoreElements()) {
            while (results.hasMore()) {
                JSONObject result = new JSONObject();
                SearchResult sr = results.next();
                Attributes attributes = sr.getAttributes();
                NamingEnumeration<String> ids = attributes.getIDs();

                while (ids.hasMoreElements()) {
                    String id = ids.next();
                    Attribute attribute = attributes.get(id);

                    if (attribute.size() > 1) {
                        JSONArray elementArray = new JSONArray();
                        for (int i = 0; i < attribute.size(); ++i) {
                            elementArray.put(attribute.get(i));
                        }
                        result.put(id, elementArray);
                    } else {
                        result.put(id, attribute.get(0));
                    }
                }

                resultArray.put(result);
            }
        }

        rval.put("searchresults", resultArray);

        return rval;
    }

    /**
     * Sets up the environment for {@link javax.naming.ldap.LdapContext}. Takes a users's username and
     * password for the LDAP credentials and other connection configuration info.
     * 
     * @param username bind username to connect to ldap with
     * @param password bind password to connect to ldap with
     * @param url full ldap connection url
     * @param userObject user object class (i.e. cn)
     * @param userBase user bind string
     * @return returns the {@link javax.naming.ldap.LdapContext} for doing LDAP operations
     * @throws javax.naming.NamingException
     */
    private static LdapContext getInitialContext(String username, String password, String url, String userObject, String userBase)
            throws NamingException {
        LdapContext context;

        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, url);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, userObject + "=" + username + "," + userBase);
        env.put(Context.SECURITY_CREDENTIALS, password);
        context = new InitialLdapContext(env, new Control[0]);

        return context;
    }
}