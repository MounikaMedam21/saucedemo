package com.saucedemo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Utility to read configuration properties

public class ConfigReader {
    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public static String getUsername() {
        return properties.getProperty("username");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }

    public static String getLockedUsername() {
        return properties.getProperty("locked.username");
    }

    public static String getLockedPassword() {
        return properties.getProperty("locked.password");
    }

    public static String getLockedUserErrorMessage() {
        return properties.getProperty("locked.user.error");
    }
}