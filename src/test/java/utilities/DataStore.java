package utilities;
import java.io.*;
import java.util.Properties;

public class DataStore {
    private static final String FILE_PATH = "test_data.properties";

    // Save the email to a file
    public static void saveEmail(String email) {
        try (OutputStream output = new FileOutputStream(FILE_PATH)) {
            Properties prop = new Properties();
            prop.setProperty("last_email", email);
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    // Read the email from the file
    public static String getEmail() {
        try (InputStream input = new FileInputStream(FILE_PATH)) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("last_email");
        } catch (IOException ex) {
            return null; // Return null if file doesn't exist yet
        }
    }
}