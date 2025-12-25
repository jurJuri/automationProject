package tests;

import utilities.BaseInformation;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class TestClass {

    TestMethods tests = new TestMethods();
    @AfterTest
    public void quit() {
        BaseInformation.quit();
    }

    @Test
    public void test() {
        tests.secondTestMethod();
    }

}
