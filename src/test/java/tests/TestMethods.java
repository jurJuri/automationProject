package tests;

import globals.GlobalVariables;
import org.testng.Assert;
import page.LoginPage;
import page.RegisterPage;
import utilities.BaseInformation;

public class TestMethods {

    public void firstTestMethod() {
        BaseInformation.getDriver().get(GlobalVariables.URL);

        RegisterPage registerPage = new RegisterPage();
        registerPage.clickAccountButton();
        registerPage.clickRegisterButton();
        System.out.println("title: " + BaseInformation.getDriver().getTitle());
        registerPage.setFirstName(GlobalVariables.name);
        registerPage.setLastName(GlobalVariables.lastname);
        registerPage.setEmail();
        registerPage.setPassword();
        registerPage.setConfirmPassword();
        registerPage.clickRegisterButtonForm();
        Assert.assertTrue(registerPage.checkRegisterPageOpen(), "Success message was not displayed!");
        registerPage.clickAccountButton();
        registerPage.clickLogoutButton();
    }

    public void LoginTestMethod() {
        BaseInformation.getDriver().get(GlobalVariables.URL);
        LoginPage loginPage = new LoginPage();
        RegisterPage registerPage = new RegisterPage();

        registerPage.clickAccountButton();
        loginPage.clickLoginButtonAccount();
        loginPage.setEmail();
        loginPage.setPassword();
        loginPage.clickLoginButtonRegister();
        loginPage.verifyLoggedInUser();

    }
    public void secondTestMethod() {
        BaseInformation.getDriver().get(GlobalVariables.URL);
        RegisterPage registerPage = new RegisterPage();
        LoginTestMethod();
        registerPage.clickAccountButton();
        registerPage.clickLogoutButton();

    }
}
