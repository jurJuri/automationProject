package page;

import elements.StylingElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
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

    // task 4 functions

    // StylingPage.java
    public void hoverSaleMenu() {
        Actions actions = new Actions(BaseInformation.getDriver());
        WebElement sale = basePageObject.getWaitUtils().waitForElementVisible(stylingElements.saleMenu);
        actions.moveToElement(sale).perform();
    }

    public void clickViewAllSale() {
        basePageObject.getWaitUtils().waitForElementClickable(stylingElements.viewAllSaleLink).click();
    }

    public void verifySaleProductStyles() {
        // Expected RGBA values based on your hex codes
        String expectedGray = "rgba(160, 160, 160, 1)";
        String expectedBlue = "rgba(51, 153, 204, 1)"; // From #3399cc

        Assert.assertFalse(stylingElements.allProductItems.isEmpty(), "No sale products found!");

        for (int i = 0; i < stylingElements.allProductItems.size(); i++) {
            WebElement product = stylingElements.allProductItems.get(i);

            // 1. Check if both prices exist
            WebElement oldPrice = product.findElement(By.cssSelector("p.old-price .price"));
            WebElement specialPrice = product.findElement(By.cssSelector("p.special-price .price"));

            Assert.assertTrue(oldPrice.isDisplayed() && specialPrice.isDisplayed(),
                    "Product " + i + " is missing one of the prices.");

            // 2. Verify Old Price: Gray (#a0a0a0) + Strikethrough
            String actualOldColor = oldPrice.getCssValue("color");
            String actualOldDecoration = oldPrice.getCssValue("text-decoration");

            // We use .contains because some browsers return "line-through solid rgb..."
            Assert.assertEquals(actualOldColor, expectedGray, "Old price color is wrong at index " + i);
            Assert.assertTrue(actualOldDecoration.contains("line-through"), "Old price not strikethrough at index " + i);

            // 3. Verify Special Price: Blue + No Strikethrough
            String actualSpecialColor = specialPrice.getCssValue("color");
            String actualSpecialDecoration = specialPrice.getCssValue("text-decoration");

            Assert.assertEquals(actualSpecialColor, expectedBlue, "Special price color is wrong at index " + i);
            Assert.assertFalse(actualSpecialDecoration.contains("line-through"), "Special price should not be strikethrough at index " + i);
        }
    }


    // task 5
    // StylingPage.java

    public void navigateToAllMen() {
        Actions actions = new Actions(BaseInformation.getDriver());
        actions.moveToElement(stylingElements.menMenu).perform();
        basePageObject.getWaitUtils().waitForElementClickable(stylingElements.viewAllMenLink).click();
    }

    public void filterByBlack() {
        basePageObject.getWaitUtils().waitForElementClickable(stylingElements.blackColorSidebarFilter).click();
    }

    public void verifyBlackColorBorder() {
        // Blue color provided: #3399cc -> rgba(51, 153, 204, 1)
        String expectedBlue = "rgb(51, 153, 204)";

        for (WebElement product : stylingElements.allProductItems) {
            // Find the 'Black' swatch within this specific product
            // Based on HTML, selected swatches get the 'selected' class on the <li> parent
            WebElement blackSwatch = product.findElement(By.cssSelector("li.option-black.selected a.swatch-link"));

            String actualBorderColor = blackSwatch.getCssValue("border-color");
            Assert.assertEquals(actualBorderColor, expectedBlue, "The selected black swatch border is not blue!");
        }
    }

    public void filterByPriceRange() {
        basePageObject.getWaitUtils().waitForElementClickable(stylingElements.priceFilterFirstOption).click();
    }

    public void verifyProductCountAndPrice(int expectedCount, double min, double max) {
        // Verify Count
        Assert.assertEquals(stylingElements.allProductItems.size(), expectedCount, "Product count does not match!");

        // Verify individual prices
        for (WebElement priceElement : stylingElements.allProductPrices) {
            // Clean the price string (remove $ and commas)
            String priceText = priceElement.getText().replace("$", "").replace(",", "").trim();
            double actualPrice = Double.parseDouble(priceText);

            Assert.assertTrue(actualPrice >= min && actualPrice <= max,
                    "Price " + actualPrice + " is out of range [" + min + "-" + max + "]");
        }
    }
}
