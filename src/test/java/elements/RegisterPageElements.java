package elements;

import utilities.BaseInformation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPageElements {

    public RegisterPageElements(){
        PageFactory.initElements(BaseInformation.getDriver(), this);
    }

    // 2. Click Account [cite: 14]
    @FindBy(css = ".account-cart-wrapper a[href*='/customer/account']")
    public WebElement AccountButton;

    // 2. Click Register button [cite: 14]
    @FindBy(css = "a[href*='/customer/account/create']")
    public WebElement AccountRegisterButton;

    // 4. Fill in form fields
    @FindBy(css = "input#firstname")
    public WebElement firstNameRegister;

    @FindBy(css = "input#lastname")
    public WebElement lastNameRegister;

    @FindBy(css = "input#email_address")
    public WebElement emailRegister;

    @FindBy(css = "input#password")
    public WebElement passwordRegister;

    @FindBy(css = "input#confirmation")
    public WebElement confirmPasswordRegister;

    // 5. Click Register button [cite: 17]
    @FindBy(css = "button[title='Register']")
    public WebElement RegisterButton;

    // 6. Check successful message is displayed
    @FindBy(css = "li.success-msg")
    public WebElement successMessageRegister;

    // 7. Click on Account and Log Out
    @FindBy(css = "a[href*='/customer/account/logout/']")
    public WebElement logOutButton;

    @FindBy(css = "a.level0.has-children[href='https://ecommerce.tealiumdemo.com/women.html']")
    public WebElement WomanButton;


    @FindBy(css = ".page-title h1")
    public WebElement registerText;




}