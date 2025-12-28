package utilities;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        // This code runs AUTOMATICALLY only when a test fails
        System.out.println("Test Failed: " + result.getName());

        // We pass the name of the failing test to our screenshot method
        ScreenshotUtils.takeScreenshot(result.getName());
    }

    // You can leave other methods (onTestStart, onTestSuccess) empty
}