package page;

import elements.ShoppingItemsElement;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import utilities.BaseInformation;
import utilities.BasePageObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingItemsPage {
    ShoppingItemsElement elements = new ShoppingItemsElement();
    BasePageObject basePageObject = new BasePageObject(BaseInformation.getBaseInformation());

    public ShoppingItemsPage() {
        PageFactory.initElements(BaseInformation.getDriver(), elements);
    }

    public void verifyPriceSorting() {
        List<Double> actualPrices = new ArrayList<>();
        for (WebElement priceElement : elements.productPrices) {
            String priceText = priceElement.getText().replace("$", "").replace(",", "").trim();
            actualPrices.add(Double.parseDouble(priceText));
        }

        List<Double> sortedPrices = new ArrayList<>(actualPrices);
        Collections.sort(sortedPrices); // Sorts in ascending order

        Assert.assertEquals(actualPrices, sortedPrices, "Products are NOT sorted by price!");
    }


    public String getWishlistMenuText() {
        return elements.wishlistMenuLink.getText();
    }

    public int getWishlistTableItemCount() {
        return elements.wishlistTableRows.size();
    }

    public void waitForWishlistPageToLoad() {
        // 1. Wait for the URL to change
        basePageObject.getWaitUtils().waitForUrlToContain("wishlist");

        // 2. Wait for the 'My Wishlist' h1 tag to appear
        // This is much more reliable than an assertion on the URL
        WebElement wishlistHeader = BaseInformation.getDriver().findElement(By.xpath("//h1[contains(text(),'My Wishlist')]"));
        basePageObject.getWaitUtils().waitForElementVisible(wishlistHeader);
    }

    public void navigateToWomenCategory() {
        Actions actions = new Actions(BaseInformation.getDriver());

        // 1. Wait for the main menu to be ready
        basePageObject.getWaitUtils().waitForElementVisible(elements.womenMenu);

        // 2. Perform the hover with a slight movement to "wake up" the JS listeners
        actions.moveToElement(elements.womenMenu).pause(java.time.Duration.ofMillis(500)).perform();

        try {
            // 3. Try the normal Selenium click (waits up to 3 seconds for visibility)
            // We use a shorter timeout here so it fails fast and goes to the catch block
            basePageObject.getWaitUtils().waitForElementClickable(elements.viewAllWomen).click();
        } catch (Exception e) {
            // 4. FALLBACK: If hover failed to open the menu, force the click via Javascript
            System.out.println("Hover failed to trigger menu, using JS Click fallback.");
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) BaseInformation.getDriver();
            js.executeScript("arguments[0].click();", elements.viewAllWomen);
        }
    }

    public void sortByPrice() {
        WebElement dropdown = elements.sortByDropdown;
        Select sortSelect = new Select(dropdown);

        // Get the currently selected option
        String currentSelection = sortSelect.getFirstSelectedOption().getText();

        if (currentSelection.equalsIgnoreCase("Price")) {
            // TRAP DETECTED: Price is already selected, so selectByVisibleText will do nothing.
            // We manually find the target URL and force the browser to navigate.
            WebElement priceOption = dropdown.findElement(org.openqa.selenium.By.xpath(".//option[contains(text(), 'Price')]"));
            String targetUrl = priceOption.getAttribute("value");

            System.out.println("Price already selected. Forcing navigation to: " + targetUrl);
            BaseInformation.getDriver().get(targetUrl);
        } else {
            // Normal path: trigger the change via Selenium
            sortSelect.selectByVisibleText("Price");
        }

        // Now this wait will pass because we've guaranteed a navigation started
        basePageObject.getWaitUtils().waitForUrlToContain("order=price");
    }

    public void addFirstItemToWishlist() {
        WebElement firstItem = elements.addToWishlistLinks.get(0);
        jsClick(firstItem);
    }

    public void addSecondItemToWishlist() {
        // Wait until the 2nd item is actually present in the list
        basePageObject.getWaitUtils().waitForElementVisible(elements.addToWishlistLinks.get(1));
        jsClick(elements.addToWishlistLinks.get(1));
    }

    // Helper method to ensure the click always fires
    private void jsClick(WebElement element) {
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) BaseInformation.getDriver();
        js.executeScript("arguments[0].click();", element);
    }

    public void clickMyWishlistFromMenu() {
        // This selector finds any link inside the account header that leads to the wishlist
        String wishlistSelector = "#header-account a[href*='/wishlist/']";

        // Increase the wait slightly to allow for the sliding animation
        org.openqa.selenium.support.ui.WebDriverWait wait =
                new org.openqa.selenium.support.ui.WebDriverWait(BaseInformation.getDriver(), java.time.Duration.ofSeconds(10));

        WebElement myWishlistLink = wait.until(
                org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(By.cssSelector(wishlistSelector))
        );

        // Click it
        myWishlistLink.click();
    }

    public void openAccountMenu() {
        // 1. Find the Account trigger using the 'skip-account' class (standard for this site)
        WebElement accountTrigger = BaseInformation.getDriver().findElement(By.cssSelector("a.skip-account"));

        // 2. Scroll to it to ensure it's in the viewport
        ((org.openqa.selenium.JavascriptExecutor) BaseInformation.getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", accountTrigger);

        // 3. Force the click via Javascript to bypass any overlapping success messages
        ((org.openqa.selenium.JavascriptExecutor) BaseInformation.getDriver())
                .executeScript("arguments[0].click();", accountTrigger);

        // 4. WAIT for the menu container to actually become visible
        // This is the step that prevents the TimeoutException later
        basePageObject.getWaitUtils().waitForElementVisible(By.id("header-account"));
    }

}