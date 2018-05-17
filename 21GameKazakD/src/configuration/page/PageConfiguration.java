package configuration.page;

import java.util.ResourceBundle;

/**
 * Configuration class to work with properties.
 */
public class PageConfiguration {

    private static PageConfiguration instance;
    private ResourceBundle resourceBundle;
    private static final String BUNDLE_NAME = "configuration/page/pages";
    public static final String LOGIN_PAGE_PATH =  "LOGIN_PAGE_PATH";
    public static final String REGISTRATION_PAGE_PATH =  "REGISTRATION_PAGE_PATH";
    public static final String MODE_PAGE_PATH =  "MODE_PAGE_PATH";
    public static final String TABLE_PAGE_PATH =  "TABLE_PAGE_PATH";
    public static final String TABLE2_PAGE_PATH =  "TABLE2_PAGE_PATH";
    public static final String RATING_PAGE_PATH =  "RATING_PAGE_PATH";
    public static final String USERS_PAGE_PATH =  "USERS_PAGE_PATH";
    public static final String MESSAGES_PAGE_PATH =  "MESSAGES_PAGE_PATH";

    /**
     * @return Instance of configuration.
     */
    public static PageConfiguration getInstance() {
        if (instance == null) {
            instance = new PageConfiguration();
            instance.resourceBundle =  ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }
    /**
     * @param key Key of property from properties file.
     * @return Property value by it's key from properties file.
     */
    public String getProperty(String key) {
        return (String)resourceBundle.getObject(key);
    }
}
