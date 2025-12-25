package elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.BaseInformation;

public class LoginElements {
    public LoginElements(){
        PageFactory.initElements(BaseInformation.getDriver(), this);

    }
    //1. Navigate to: https://ecommerce.tealiumdemo.com/
    // 2. Click on Account then Sign in. / perdorim ate nga register
    // 3. Login with the credentials created from Test 1.
    // Using a partial match for the href is safer than the full URL
    @FindBy(css = "a[href*='/customer/account/login/']")
    public WebElement LoginButtonAccount;

    // # is shorthand for ID
    @FindBy(css = "#email")
    public WebElement InputEmail;

    @FindBy(css = "#pass")
    public WebElement InputPassword;

    @FindBy(css = "#send2")
    public WebElement LoginButton;

    // . is shorthand for class
    @FindBy(css = "p.welcome-msg")
    public WebElement usernameDisplay;
    // 5. Click on Account and Log Out.
    //marrim nga register
}
