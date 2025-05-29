package config;

import org.testng.ITestContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class EnvironmentConfig {

    private static Properties properties;

    public static void init(ITestContext context) {
        String env = context.getCurrentXmlTest().getParameter("env");

        if (env == null || env.isEmpty()) {
            throw new RuntimeException("TestNG 'env' parameter is missing in PetStore_DEV.xml");
        }

        String fileName = "config/" + env + ".properties";
        properties = new Properties();

        try (InputStream input = EnvironmentConfig.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new RuntimeException("Could not find properties file: " + fileName);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load environment properties from " + fileName, e);
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("baseUrl");
    }
}