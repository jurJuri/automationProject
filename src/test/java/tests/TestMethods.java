package tests;

import globals.GlobalVariables;
import org.testng.Assert;
import page.LoginPage;
import page.RegisterPage;
import page.StylingPage;
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
    public void thirdTestMethod() {
        // Precondition: Login (Assuming you have this in your LoginTestMethod)
        LoginTestMethod();

        StylingPage stylingPage = new StylingPage();
        stylingPage.hoverOverWomanMenu();
        stylingPage.clickViewAllWomen();

        // 2. Capture color BEFORE hover
        String colorBefore = stylingPage.getProductLinkColor();
        System.out.println("Color before hover: " + colorBefore);

        // 3. Perform the Hover
        stylingPage.hoverOverProductLink();

        // 4. Capture color AFTER hover
        String colorAfter = stylingPage.getProductLinkColor();
        System.out.println("Color after hover: " + colorAfter);

        // 5. Assert the change
        Assert.assertNotEquals(colorBefore, colorAfter,
                "The text color did not change on hover! Both were: " + colorAfter);    }
}
