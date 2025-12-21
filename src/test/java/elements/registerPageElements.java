package elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
public class registerPageElements {
    @FindBy(id = "firstname") public WebElement firstNameField;
    @FindBy(id = "lastname") public WebElement lastNameField;
    @FindBy(id = "email_address") public WebElement emailField;
    @FindBy(id = "password") public WebElement passwordField;
    @FindBy(id = "confirmation") public WebElement confirmPasswordField;
    @FindBy(xpath = "//button[@title='Register']") public WebElement registerButton;
}
