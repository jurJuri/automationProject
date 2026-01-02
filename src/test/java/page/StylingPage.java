package page;

import elements.StylingElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilities.BaseInformation;
import utilities.BasePageObject;

import java.util.List;

public class StylingPage {
    BasePageObject basePageObject = new BasePageObject(BaseInformation.getBaseInformation());
    StylingElements stylingElements =  new StylingElements();

    //pop up
    public void handlePrivacyPopup() {
        try {
            // Wait a few seconds to see if it appears
            Thread.sleep(2000);
            if (stylingElements.optOutRadioButton.isDisplayed()) {
                // 1. Click Opt-Out
                basePageObject.getWaitUtils().waitForElementClickable(stylingElements.optOutRadioButton).click();

                // 2. Click Submit/Save if it exists
                try {
                    stylingElements.privacySubmitButton.click();
                } catch (Exception e) {
                    // If no button, try clicking the radio label or just use JS to hide the div
                    ((org.openqa.selenium.JavascriptExecutor) BaseInformation.getDriver())
                            .executeScript("document.getElementsByClassName('privacy_prompt')[0].style.display='none';");
                }
                System.out.println("Privacy popup dismissed.");
            }
        } catch (Exception e) {
            System.out.println("Privacy popup not found, continuing...");
        }
    }

    // task 3 -----------------
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
        // #3399cc is rgb(51, 153, 204)
        String expectedBlue = "51, 153, 204";

        // 1. Wait for the 'Currently Shopping by' indicator to ensure AJAX finished
        basePageObject.getWaitUtils().waitForElementVisible(By.cssSelector(".currently .value"));

        // 2. Get the list of products
        List<WebElement> products = BaseInformation.getDriver().findElements(By.cssSelector("ul.products-grid li.item"));

        for (int i = 0; i < products.size(); i++) {
            boolean colorMatched = false;
            String actualColor = "";

            // Retry loop (3 attempts) to give CSS time to 'settle'
            for (int retry = 0; retry < 3; retry++) {
                try {
                    // Refresh product reference to avoid StaleElement
                    WebElement product = BaseInformation.getDriver().findElements(By.cssSelector("ul.products-grid li.item")).get(i);
                    WebElement blackSwatch = product.findElement(By.cssSelector("li.option-black.selected a.swatch-link"));

                    actualColor = blackSwatch.getCssValue("border-top-color");

                    // Debug print: helps you see what is actually happening!
                    System.out.println("Product " + (i + 1) + " color found: " + actualColor);

                    if (actualColor.contains(expectedBlue)) {
                        colorMatched = true;
                        break;
                    }
                    // If it's not blue yet, wait 500ms and try again
                    Thread.sleep(500);
                } catch (Exception e) {
                    // Handle stale element by retrying the same index
                }
            }

            Assert.assertTrue(colorMatched,
                    "Product " + (i + 1) + " swatch border was [" + actualColor + "] but expected blue [" + expectedBlue + "]");
        }
    }

    public void filterByPriceRange() {
        basePageObject.getWaitUtils().waitForElementClickable(stylingElements.priceFilterFirstOption).click();
    }

    public void verifyProductCountAndPrice(int expectedCount, double min, double max) {
        // 1. Wait for URL to update (uses the method we just added)
        basePageObject.getWaitUtils().waitForUrlContains("price=");

        // 2. Refresh the list of prices to avoid StaleElementReferenceException
        List<WebElement> priceElements = BaseInformation.getDriver().findElements(By.cssSelector(".col-main .products-grid > li.item"));

        // 3. Verify count
        Assert.assertEquals(priceElements.size(), expectedCount, "Product count mismatch!");

        // 4. Verify ranges
        for (WebElement priceElement : priceElements) {
            String priceText = priceElement.getText().replaceAll("[^0-9.]", "").trim();
            if (!priceText.isEmpty()) {
                double actualPrice = Double.parseDouble(priceText);
                Assert.assertTrue(actualPrice >= min && actualPrice <= max,
                        "Price " + actualPrice + " is out of range!");
            }
        }
    }
}
