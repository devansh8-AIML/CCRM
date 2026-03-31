package edu.ccrm.config;

/**
 * Singleton configuration class for the CCRM application. Provides centralized
 * access to application settings like data folder and credit limits.
 *
 * @author VITyarthi
 * @version 1.1
 */
public class AppConfig {

    private static final AppConfig INSTANCE = new AppConfig();

    private final String dataFolder;
    private final int maxCredits;

    private AppConfig() {
        // Allow overriding via system properties for flexibility
        this.dataFolder = System.getProperty("data.folder", "data");
        this.maxCredits = Integer.parseInt(System.getProperty("max.credits", "24"));
    }

    /**
     * Gets the singleton instance of AppConfig.
     *
     * @return the AppConfig instance
     */
    public static AppConfig getInstance() {
        return INSTANCE;
    }

    /**
     * Gets the data folder path for storing application data.
     *
     * @return the data folder path
     */
    public String getDataFolder() {
        return dataFolder;
    }

    /**
     * Gets the maximum credits a student can enroll in.
     *
     * @return the maximum credit limit
     */
    public int getMaxCredits() {
        return maxCredits;
    }
}
