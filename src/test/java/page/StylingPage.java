package page;

import elements.StylingElements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import utilities.BaseInformation;
import utilities.BasePageObject;

public class StylingPage {
    BasePageObject basePageObject = new BasePageObject(BaseInformation.getBaseInformation());
    StylingElements stylingElements =  new StylingElements();

    public StylingPage(){
        PageFactory.initElements(BaseInformation.getDriver(), this);
    }

    public void hoverOverWomanMenu() {
        Actions actions = new Actions(BaseInformation.getDriver());
        WebElement woman = basePageObject.getWaitUtils().waitForElementVisible(stylingElements.WomanButton);
        actions.moveToElement(woman).perform();
    }

    public void clickViewAllWomen() {
        basePageObject.getWaitUtils().waitForElementClickable(stylingElements.viewAllWomenLink).click();
    }

    public void hoverOverFirstProduct() {
        Actions actions = new Actions(BaseInformation.getDriver());
        WebElement product = basePageObject.getWaitUtils().waitForElementVisible(stylingElements.firstProductItem);
        actions.moveToElement(product).perform();
    }

    // Helper to get a specific style (like box-shadow or border)
    public String getProductCSS(String property) {
        return stylingElements.firstProductItem.getCssValue(property);
    }

    public void hoverOverProductLink() {
        Actions actions = new Actions(BaseInformation.getDriver());
        WebElement link = basePageObject.getWaitUtils().waitForElementVisible(stylingElements.firstProductLink);
        actions.moveToElement(link).perform();
    }

    public String getProductLinkColor() {
        // We look for "color" specifically
        return stylingElements.firstProductLink.getCssValue("color");
    }
}
