package edu.ccrm.config;

/**
 * An example of the Singleton Design Pattern.
 * This class holds application-wide configuration settings. The Singleton pattern
 * guarantees that there is only ever one instance of this class created.
 */
public class AppConfig{

    //A private static final instance of the class itself
    //This is created the moment the class is loaded by the JVM
    private static final AppConfig INSTANCE = new AppConfig();

    private final int maxCreditsPerSemester = 18;
    private final String dataDirectory = "data";

    //A private constructor, which prevents anyone else from creating an instance
    private AppConfig(){
        System.out.println("AppConfig Singleton initialized.");
    }

    //A public static method to get the single instance.
    public static AppConfig getInstance(){
        return INSTANCE;
    }

    //getters for our configuration properties.
    public int getMaxCreditsPerSemester(){
        return maxCreditsPerSemester;
    }

    public String getDataDirectory(){
        return dataDirectory;
    }
}