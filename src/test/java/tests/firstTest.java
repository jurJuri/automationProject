package tests;

import globals.GlobalVariables;
import page.RegisterPage;
import utilities.BaseInformation;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class firstTest {

    RegisterPage registerPage = new RegisterPage();

    @AfterTest
    public void quit() {
        BaseInformation.quit();
    }

    @Test
    public void test() {
        registerPage.getUrl(GlobalVariables.URL);
        registerPage.clickRegisterButton();
        registerPage.setFirstName("Test");
        registerPage.setLastName("TestLastName");
        registerPage.setEmail("testEmail");
        registerPage.setPassword();
        registerPage.setConfirmPassword();
        registerPage.clickRegisterButtonForm();
        Assert.assertTrue(registerPage.checkRegister());
    }

}
