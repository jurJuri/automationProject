package page;


import elements.RegisterPageElements;
import utilities.BaseInformation;
import utilities.BasePageObject;
import org.openqa.selenium.support.PageFactory;
import globals.GlobalVariables;

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

    public void setEmail(String email){
        basePageObject
                .getWaitUtils()
                .waitForElementVisible(registerPageElements.emailRegister)
                .sendKeys(email+Math.random()+"@gmail.com");
    }

    public void setPassword(){
        registerPageElements.passwordRegister.sendKeys(GlobalVariables.password);

    }

    public void setConfirmPassword(){
        registerPageElements.confirmPasswordRegister.sendKeys(GlobalVariables.confirmPassword);
    }

    public void clickRegisterButtonForm(){
        basePageObject
                .getWaitUtils()
                .waitForElementClickable(registerPageElements.RegisterButton)
                .click();
    }

    public boolean checkRegister(){
        return registerPageElements.successMessageRegister.isDisplayed();
    }

}
