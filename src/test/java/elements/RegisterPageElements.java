package elements;

import utilities.BaseInformation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPageElements {
    //1. Navigate to: https://ecommerce.tealiumdemo.com/
    // 2. Click Account and then Register button.
    // 3. Check title of the open page.
    // 4. Fill in form fields.
    // 5. Click Register button.
    // 6. Check successful message is displayed on the screen.
    // 7. Click on Account and Log Out.

    public RegisterPageElements(){

        PageFactory.initElements(BaseInformation.getDriver(), this);
    }

    @FindBy(xpath = "//div[contains(@class,'account-cart-wrapper')]//a[contains(@href,'/customer/account')]\n") public WebElement AccountButton;
    @FindBy(xpath = "//a[contains(@href,'/customer/account/create')]") public WebElement AccountRegisterButton;
    @FindBy(xpath = "//title") public WebElement Title;
    @FindBy(xpath = "//input[contains(@id, \"firstname\")]") public WebElement firstNameRegister;
    @FindBy(xpath = "//input[contains(@id, \"lastname\")]") public WebElement lastNameRegister;
    @FindBy(xpath = "//input[contains(@id, \"email\")]") public WebElement emailRegister;
    @FindBy(xpath = "//input[contains(@id, \"password\")]") public WebElement passwordRegister;
    @FindBy(xpath = "//input[contains(@id, \"confirmation\")]") public WebElement confirmPasswordRegister;
    @FindBy(xpath = "//button[contains(@title, \"Register\")]") public WebElement RegisterButton;
    @FindBy(xpath = "//li[contains(@class, \"success-msg\")]") public WebElement successMessageRegister;
    @FindBy(xpath = "//a[contains(@href, \"/customer/account/logout/\")]") public WebElement logOutButton;

}
