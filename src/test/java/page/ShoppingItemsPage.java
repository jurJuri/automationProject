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

    // Test 6 ----------------------
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

    //Test 7 ----------------------------------------


    public void clickProductImageInWishlist(int index) {
        basePageObject.getWaitUtils().waitForElementVisible(elements.wishlistProductImages.get(index));
        // Use JS Click to ensure we hit the image even if things overlap
        jsClick(elements.wishlistProductImages.get(index));
    }

    public void selectFirstColorAndSize() {
        // Generic selector: Finds the first available option for BOTH Color and Size
        List<WebElement> options = BaseInformation.getDriver().findElements(By.cssSelector("#product-options-wrapper dd .configurable-swatch-list li:first-child a"));

        for (WebElement option : options) {
            basePageObject.getWaitUtils().waitForElementClickable(option).click();
        }
    }

//    public void clickAddToCart() {
//        basePageObject.getWaitUtils().waitForElementClickable(elements.addToCartButton).click();
//    }

    public void clickAddToCart() {
        // Use the robust helper instead of simple .click()
        // This fixes the "orange notice container" blocking the button on small screens
        WebElement btn = elements.addToCartButton;

        // 1. Scroll into view (Center)
        ((org.openqa.selenium.JavascriptExecutor) BaseInformation.getDriver())
                .executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", btn);

        // 2. Click (with JS Fallback)
        try {
            basePageObject.getWaitUtils().waitForElementClickable(btn).click();
        } catch (Exception e) {
            ((org.openqa.selenium.JavascriptExecutor) BaseInformation.getDriver())
                    .executeScript("arguments[0].click();", btn);
        }
    }


    public void verifyAddToCartSuccess() {
        basePageObject.getWaitUtils().waitForElementVisible(elements.successMessage);
        Assert.assertTrue(elements.successMessage.getText().contains("added to your shopping cart"),
                "Product was not added successfully!");
    }

    public void clickMyCartFromAccountMenu() {
        // Find the link using CSS
        WebElement cartLink = BaseInformation.getDriver().findElement(By.cssSelector("#header-account a[href*='checkout/cart']"));
        basePageObject.getWaitUtils().waitForElementVisible(cartLink);
        cartLink.click();
    }

    public void waitForCartPageToLoad() {
        basePageObject.getWaitUtils().waitForUrlToContain("checkout/cart");
        basePageObject.getWaitUtils().waitForElementVisible(elements.pageTitle);

        // Give the table 1 second to "settle" its layout
        try { Thread.sleep(1000); } catch (Exception e) {}
    }
//    public void clickMyCartFromAccountMenu() {
//        WebElement cartLink = BaseInformation.getDriver().findElement(By.cssSelector("#header-account a[href*='checkout/cart']"));
//        basePageObject.getWaitUtils().waitForElementVisible(cartLink);
//        jsClick(cartLink);
//    }

    /**
     * UPDATED: Handles the "Update button appears after typing" logic
     */
    public void updateCartQuantity(int itemIndex, String quantity) {
        int attempts = 0;
        while (attempts < 3) { // Try up to 3 times
            try {
                // 1. Re-find the input list FRESH inside the loop
                // This ensures we always have the latest version of the element
                List<WebElement> inputs = BaseInformation.getDriver().findElements(By.cssSelector("#shopping-cart-table input.qty"));

                if (inputs.size() <= itemIndex) {
                    throw new RuntimeException("Input not found at index " + itemIndex);
                }

                WebElement input = inputs.get(itemIndex);

                // 2. Wait for it to be clickable (ensures it's fully rendered)
                basePageObject.getWaitUtils().waitForElementClickable(input);

                // 3. Action: Clear and Type
                input.clear();
                input.sendKeys(quantity);

                // 4. Find the specific "Update" button next to this input
                WebElement specificUpdateBtn = input.findElement(By.xpath("./following-sibling::button[contains(@class, 'btn-update')]"));
                basePageObject.getWaitUtils().waitForElementVisible(specificUpdateBtn);

                // 5. Click Update
                specificUpdateBtn.click();

                // 6. Wait for the reload to finish
                // The page will refresh, so we wait for a few seconds to let it settle
                try { Thread.sleep(3000); } catch (Exception e) {}
                basePageObject.getWaitUtils().waitForElementVisible(elements.grandTotalPrice);

                // If we finished without error, break the loop!
                break;

            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                // TRAP DETECTED: The page refreshed while we were working.
                System.out.println("Cart page refreshed unexpectedly. Retrying attempt " + (attempts + 1) + "...");
                attempts++;

                // Wait a tiny bit before trying again
                try { Thread.sleep(1000); } catch (InterruptedException ie) {}
            }
        }
    }

    public void verifyGrandTotalMatch() {
        // 1. Get all Rows (Find fresh to avoid StaleElement)
        List<WebElement> prices = BaseInformation.getDriver().findElements(By.cssSelector("#shopping-cart-table .product-cart-price .price"));
        List<WebElement> qtys = BaseInformation.getDriver().findElements(By.cssSelector("#shopping-cart-table input.qty"));

        double calculatedSum = 0.0;

        // 2. Calculate Sum
        for (int i = 0; i < prices.size(); i++) {
            // Clean price text: "$100.00" -> "100.00"
            String priceText = prices.get(i).getText().replace("$", "").replace(",", "").trim();
            double price = Double.parseDouble(priceText);

            // Get Qty
            String qtyText = qtys.get(i).getAttribute("value");
            int qty = Integer.parseInt(qtyText);

            calculatedSum += (price * qty);
        }

        // 3. Get Grand Total
        String totalText = elements.grandTotalPrice.getText().replace("$", "").replace(",", "").trim();
        double grandTotal = Double.parseDouble(totalText);

        System.out.println("Calculated: " + calculatedSum + " | Grand Total: " + grandTotal);

        // 4. Verify
        Assert.assertEquals(calculatedSum, grandTotal, "Cart sum does not match Grand Total!");
    }

    // test 8
    // --- NEW METHODS FOR TEST 8 ---

    /**
     * Gets the current number of items in the cart table.
     * Returns 0 if the table doesn't exist (which happens when cart is empty).
     */
    public int getCartItemCount() {
        try {
            // We use findElements(By...) to ensure we get a fresh count from the browser
            // If we used elements.cartTableRows.size(), it might give a stale cached number.
            List<WebElement> rows = BaseInformation.getDriver().findElements(By.cssSelector("#shopping-cart-table tbody tr"));
            return rows.size();
        } catch (Exception e) {
            // If the table is missing, the cart is empty
            return 0;
        }
    }
    public void removeFirstItem() {
        int attempts = 0;
        while (attempts < 3) {
            try {
                List<WebElement> buttons = BaseInformation.getDriver().findElements(By.cssSelector(".btn-remove2"));
                if (buttons.isEmpty()) break;

                WebElement btn = buttons.get(0);

                // FIX: Scroll to center so header/footer don't cover it
                ((org.openqa.selenium.JavascriptExecutor) BaseInformation.getDriver())
                        .executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", btn);

                // FIX: Force Click
                ((org.openqa.selenium.JavascriptExecutor) BaseInformation.getDriver())
                        .executeScript("arguments[0].click();", btn);

                try { Thread.sleep(4000); } catch (Exception e) {}
                return;

            } catch (Exception e) {
                attempts++;
            }
        }
    }

//    public void removeFirstItem() {
//        int attempts = 0;
//        while (attempts < 3) {
//            try {
//                // 1. Fresh Lookup
//                List<WebElement> buttons = BaseInformation.getDriver().findElements(By.cssSelector(".btn-remove2"));
//
//                if (buttons.isEmpty()) {
//                    System.out.println("No delete buttons found.");
//                    break;
//                }
//
//                WebElement btn = buttons.get(0);
//
//                // 2. Scroll to the element (Helps if it's off-screen)
//                ((org.openqa.selenium.JavascriptExecutor) BaseInformation.getDriver())
//                        .executeScript("arguments[0].scrollIntoView(true);", btn);
//
//                // Small pause to let scrolling finish
//                try { Thread.sleep(500); } catch (Exception e) {}
//
//                // 3. FORCE CLICK via Javascript
//                // This fixes the "Timeout waiting for element to be clickable" error
//                ((org.openqa.selenium.JavascriptExecutor) BaseInformation.getDriver())
//                        .executeScript("arguments[0].click();", btn);
//
//                // 4. Wait for the page reload to complete
//                // The cart page reloads fully after deletion. We wait to ensure the next step doesn't read stale data.
//                try { Thread.sleep(4000); } catch (Exception e) {}
//
//                return; // Success, exit method
//
//            } catch (org.openqa.selenium.StaleElementReferenceException e) {
//                // Trap: If the element changed while we were scrolling/clicking
//                System.out.println("Stale element caught. Retrying attempt " + (attempts + 1) + "...");
//                attempts++;
//            } catch (Exception e) {
//                // Trap: Any other weird error
//                System.out.println("Error clicking remove button: " + e.getMessage());
//                attempts++;
//            }
//        }
//    }
    public void verifyCartIsEmpty() {
        // 1. Wait for the 'Shopping Cart is Empty' title
        basePageObject.getWaitUtils().waitForElementVisible(elements.emptyCartPageTitle);

        // 2. Verify Title Text
        Assert.assertEquals(elements.emptyCartPageTitle.getText(), "SHOPPING CART IS EMPTY", "Page title mismatch!");

        // 3. Verify Body Text
        basePageObject.getWaitUtils().waitForElementVisible(elements.emptyCartMessageBody);
        String bodyText = elements.emptyCartMessageBody.getText();
        Assert.assertTrue(bodyText.contains("You have no items in your shopping cart."),
                "Empty message text not found! Found: " + bodyText);
    }

    // Helper needed for Setup


}