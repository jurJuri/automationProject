package elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.BaseInformation;

public class StylingElements {
    public StylingElements(){
        PageFactory.initElements(BaseInformation.getDriver(), this);
    }

    // task 3 elements
    @FindBy(css = "li.view-all a[href*='women.html']")
    public WebElement viewAllWomenLink;
    @FindBy(css = "a.level0.has-children[href='https://ecommerce.tealiumdemo.com/women.html']")
    public WebElement WomanButton;
    @FindBy(css = ".products-grid .item.last")
    public WebElement firstProductItem;
    @FindBy(css = ".products-grid .item.last h2.product-name a")
    public WebElement firstProductLink;
}
