package tests;

import org.testng.annotations.Listeners; // This is the annotation
import utilities.BaseInformation;
import utilities.TestListener;          // This is your custom classimport utilities.BaseInformation;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class TestClass {

    TestMethods tests = new TestMethods();
    @AfterTest
    public void quit() {
        BaseInformation.quit();
    }

    @Test
    public void test() {
        tests.fifthTestMethod();
    }

}
