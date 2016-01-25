My Awesome Utility
===============================
A set of utilities created by Peter Hospodka, except for jquery and vkbeautify stuff whose files headers have their 
attributions.

Purpose
-------------------------------

The utilities are focused on being used in a WAR, and therefore useful for testing setup of a server it is 
deployed on.  All functions should work on either Weblogic or Jboss (though login has a catch and I have not 
actually deployed this to a Jboss env, but there is no reason it should not work). It comes with:

Usage
-------------------------------

- **Login** - Tests login / logout capabilities of your security realm.  Works in Weblogic and Jboss (Jboss
  may require the realm name my_awesome_realm until I get to work with that environment more; you can edit the
  jboss-web.xml to change it).

- **Database** - Test connectivity to a database with sql.  Works with jndi lookup to test your server setup,
  and jdbc connection string for arbitrary db connections originating from the server.
- **Format** - Formats CSS, JSON, SQL (selects only), XML into pretty printed versions as best as able.
  Useful for generated json and hibernate debug sql.
- **Ldap** - Probably the most useful for general use.  This will perform three commonly needed ldap queries.
    + Search for all information (minus groups) about a specific user.
    + Search for all users of a specific group.
    + Search for all groups that a specific user belongs to.
- **Jms** - Provides a mechanism to publish a JMS message to and consume a JMS message from a topic or queue.
  To perform both at the same time, two windows must be used and the consume  (receive) option must be
  activated before the message is published (sent).
- **Mail** - Provides a mechanism to send an email from the server the WAR is deployed to for testing server to
  mail server connectivity.
- **Web Service** - A simple web service client that will send a soap message to an endpoint and display the response.
- **Properties** - Displays all the system properties available to the JVM and the application properties from the
  WAR's config.properties file. (configurable in web.xml)
- **Whoami** - Can check what your logged in subject looks like i.e. your username and roles (roles must be predefined
  in the properties and various .xmls)

Config
-------------------------------

The config.properties control all the default text that appears in fields and can be customized.  I haven't made that 
quite external or anything so you'd have to edit it by hand.

State
-------------------------------

This is in a very early state.  I wrote this a long time ago and pieced it together from scraps I had left and decided 
to put it out there.  It, however, has not been tested in years and needs lots of improvement.

License
-------------------------------

See LICENSE file.  MPL 2.0.

Plans
-------------------------------

- Remove JSP's.  There is no excuse and will make certain improvements easier and gets rid of dependencies.
- Fix it so input is not cleared after submit.  That is just annoying.
- Externalize config so it does not need to be packaged.
- Tests ಠ_ಠ
- Update Jms component to not require two windows.
- Server support needs work.  Jboss and weblogic configs not ideal as they are.  Support others like tomcat more effectively.
- Login component needs re-thought.
- Ldap component is too naive and hard coded on paths.
- Java features cleanup.  A lot has changed since I first wrote this.
- Fix up maven.  Trimmed down a sample from a giant project.  May have more in there than I need.
- Switch to logback config.
