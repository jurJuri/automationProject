package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {
    public static void takeScreenshot(String testName) {
        // 1. Create a timestamp so files don't overwrite each other
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        // 2. Cast the driver to TakesScreenshot
        File srcFile = ((TakesScreenshot) BaseInformation.getDriver()).getScreenshotAs(OutputType.FILE);

        File folder = new File(System.getProperty("user.dir") + "/screenshots/");
        if (!folder.exists()) {
            folder.mkdir();
        }

        // 3. Define the destination path
        String path = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + timeStamp + ".png";

        try {
            // 4. Copy the file to the path
            FileUtils.copyFile(srcFile, new File(path));
            System.out.println("Screenshot saved at: " + path);
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}