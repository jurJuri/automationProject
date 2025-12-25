package page;


import elements.RegisterPageElements;
import org.openqa.selenium.WebElement;
import utilities.BaseInformation;
import utilities.BasePageObject;
import org.openqa.selenium.support.PageFactory;
import globals.GlobalVariables;
import utilities.DataStore;

public class RegisterPage {
    BasePageObject basePageObject = new BasePageObject(BaseInformation.getBaseInformation());
    RegisterPageElements registerPageElements =  new RegisterPageElements();

    public RegisterPage(){
        PageFactory.initElements(BaseInformation.getDriver(), this);
    }

    public void getUrl(String url){
        BaseInformation.getDriver().get(url);
    }
    // 1. Navigate to: https://ecommerce.tealiumdemo.com/
    // 2. Click Account and then Register button.
    // 3. Check title of the open page.
    // 4. Fill in form fields.
    // 5. Click Register button.
    // 6. Check successful message is displayed on the screen.
    // 7. Click on Account and Log Out.

    public void clickAccountButton(){
        basePageObject
                .getWaitUtils()
                .waitForElementClickable(registerPageElements.AccountButton)
                .click();
    }
    public void clickWomanButton(){
        basePageObject
                .getWaitUtils()
                .waitForElementClickable(registerPageElements.WomanButton)
                .click();
    }


    public void clickRegisterButton(){
        basePageObject
                .getWaitUtils()
                .waitForElementClickable(registerPageElements.AccountRegisterButton)
                .click();
    }


    public void setFirstName(String firstName){
        basePageObject
                .getWaitUtils()
                .waitForElementVisible(registerPageElements.firstNameRegister)
                .sendKeys(firstName);
    }

    public void setLastName(String lastName){
        basePageObject
                .getWebElementUtils()
                .sendKeysToElementWithWait(registerPageElements.lastNameRegister,lastName,2);
    }

    public void setEmail(){
        // 1. Create the final email string first so we can save it exactly as it is
        String email = "user" + System.currentTimeMillis() + "@gmail.com";

        // 2. Locate the element and send the keys
        basePageObject
                .getWaitUtils()
                .waitForElementVisible(registerPageElements.emailRegister)
                .sendKeys(email);

        // 3. Save THIS EXACT string to your properties file using the DataStore utility
        DataStore.saveEmail(email);

        // Optional: Print to console so you can double-check it during the test
        System.out.println("Registered with email: " + email);
    }

    public void setPassword(){
        registerPageElements.passwordRegister.sendKeys(GlobalVariables.password);

    }

    public void setConfirmPassword(){
        registerPageElements.confirmPasswordRegister.sendKeys(GlobalVariables.confirmPassword);
    }

    public void clickRegisterButtonForm(){
        WebElement button = registerPageElements.RegisterButton;
        basePageObject.getWaitUtils().waitForElementVisible(button);
        // Use JS click instead of regular click
        basePageObject.getWaitUtils().clickWithJS(button);

    }

    public boolean checkRegister(){
        return registerPageElements.successMessageRegister.isDisplayed();
    }

    public boolean checkRegisterPageOpen(){
        return registerPageElements.registerText.isDisplayed();
    }

    public void clickLogoutButton(){
        basePageObject
                .getWaitUtils()
                .waitForElementClickable(registerPageElements.logOutButton)
                .click();
    }


}
