package page;

import elements.RegisterPageElements;
import elements.LoginElements;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utilities.BaseInformation;
import utilities.BasePageObject;
import org.openqa.selenium.support.PageFactory;
import globals.GlobalVariables;
import utilities.DataStore;

import javax.xml.crypto.Data;

public class LoginPage {
    BasePageObject basePageObject = new BasePageObject(BaseInformation.getBaseInformation());
    RegisterPageElements registerPageElements =  new RegisterPageElements();
    LoginElements loginPageElements = new LoginElements();

    public LoginPage(){
        PageFactory.initElements(BaseInformation.getDriver(), this);
    }

    // 1. Navigate to: https://ecommerce.tealiumdemo.com/
    // 2. Click on Account then Sign in.
    public void clickLoginButtonAccount(){
        basePageObject
                .getWaitUtils()
                .waitForElementClickable(loginPageElements.LoginButtonAccount)
                .click();
    }
    // 3. Login with the credentials created from Test 1.

    public void setEmail(){
        basePageObject
                .getWaitUtils()
                .waitForElementVisible(loginPageElements.InputEmail)
                .sendKeys(DataStore.getEmail());
    }

    public void setPassword(){
        loginPageElements.InputPassword.sendKeys(GlobalVariables.password);
    }
    public void clickLoginButtonRegister(){
        WebElement button = loginPageElements.LoginButton;
        basePageObject.getWaitUtils().waitForElementVisible(button);
        // Use JS click instead of regular click
        basePageObject.getWaitUtils().clickWithJS(button);

    }

    // 4. Check your username is displayed on right corner of the page.

    public void verifyLoggedInUser() {
        // 1. Get the text from the website
        String actualText = basePageObject
                .getWaitUtils()
                .waitForElementVisible(loginPageElements.usernameDisplay)
                .getText();

        // 2. Build the expected string: "WELCOME, TEST TESTLASTNAME!"
        String expectedName = (GlobalVariables.name + " " + GlobalVariables.lastname).toUpperCase();

        // 3. Assert that the welcome message contains our name
        Assert.assertTrue(actualText.contains(expectedName),
                "Expected name [" + expectedName + "] not found in welcome message: " + actualText);
    }

    // 5. Click on Account and Log Out.

}
