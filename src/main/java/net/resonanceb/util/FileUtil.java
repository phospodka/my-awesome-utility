package net.resonanceb.util;

import java.io.IOException;
import java.util.Properties;

public class FileUtil {
    /**
     * Load the properties file from the classpath
     * @return {@link java.util.Properties}
     * @throws java.io.IOException
     */
    public static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(FileUtil.class.getClassLoader().getResourceAsStream("config.properties"));

        return properties;
    }

    /**
     * Retrieve a property by key name from the classpath properties resource.
     * @param key {@link String} key
     * @return {@link String} value of the property
     * @throws java.io.IOException
     */
    public static String getProperty(String key) throws IOException {
        Properties properties = loadProperties();

        return properties.getProperty(key);
    }
}