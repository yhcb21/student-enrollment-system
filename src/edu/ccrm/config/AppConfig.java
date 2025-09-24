package edu.ccrm.config;

/**
 * Application configuration using the Singleton Design Pattern.
 */
public class AppConfig {
    private static AppConfig instance;
    private final String dataDirectory = "data";
    private final String backupDirectory = "backup";
    private final int maxCreditsPerSemester = 18;

    private AppConfig() {
        // Private constructor to prevent instantiation
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }
    
    // Getters
    public String getDataDirectory() {
        return dataDirectory;
    }

    public String getBackupDirectory() {
        return backupDirectory;
    }

    public int getMaxCreditsPerSemester() {
        return maxCreditsPerSemester;
    }
}