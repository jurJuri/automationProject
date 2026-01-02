package tests;

import elements.StylingElements;
import elements.ShoppingItemsElement;
import globals.GlobalVariables;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.openqa.selenium.By;
import page.LoginPage;
import page.RegisterPage;
import page.ShoppingItemsPage;
import page.StylingPage;
import utilities.BaseInformation;
import utilities.BasePageObject;

public class TestMethods {
    BasePageObject basePageObject = new BasePageObject(BaseInformation.getBaseInformation());

    public void firstTestMethod() {
        BaseInformation.getDriver().get(GlobalVariables.URL);

        RegisterPage registerPage = new RegisterPage();
        registerPage.clickAccountButton();
        registerPage.clickRegisterButton();
        System.out.println("title: " + BaseInformation.getDriver().getTitle());
        registerPage.setFirstName(GlobalVariables.name);
        registerPage.setLastName(GlobalVariables.lastname);
        registerPage.setEmail();
        registerPage.setPassword();
        registerPage.setConfirmPassword();
        registerPage.clickRegisterButtonForm();
        Assert.assertTrue(registerPage.checkRegisterPageOpen(), "Success message was not displayed!");
        registerPage.clickAccountButton();
        registerPage.clickLogoutButton();
    }

    public void LoginTestMethod() {
        BaseInformation.getDriver().get(GlobalVariables.URL);
        LoginPage loginPage = new LoginPage();
        RegisterPage registerPage = new RegisterPage();

        registerPage.clickAccountButton();
        loginPage.clickLoginButtonAccount();
        loginPage.setEmail();
        loginPage.setPassword();
        loginPage.clickLoginButtonRegister();
        loginPage.verifyLoggedInUser();

    }

    public void secondTestMethod() {
        BaseInformation.getDriver().get(GlobalVariables.URL);
        RegisterPage registerPage = new RegisterPage();
        LoginTestMethod();
        registerPage.clickAccountButton();
        registerPage.clickLogoutButton();

    }

    public void thirdTestMethod() {
        // Precondition: Login (Assuming you have this in your LoginTestMethod)
        LoginTestMethod();

        StylingPage stylingPage = new StylingPage();
        stylingPage.hoverOverWomanMenu();
        stylingPage.clickViewAllWomen();

        // 2. Capture color BEFORE hover
        String colorBefore = stylingPage.getProductLinkColor();
        System.out.println("Color before hover: " + colorBefore);

        // 3. Perform the Hover
        stylingPage.hoverOverProductLink();

        // 4. Capture color AFTER hover
        String colorAfter = stylingPage.getProductLinkColor();
        System.out.println("Color after hover: " + colorAfter);

        // 5. Assert the change
        Assert.assertNotEquals(colorBefore, colorAfter,
                "The text color did not change on hover! Both were: " + colorAfter);
    }

    public void fourthTestMethod() {
        // 1. Handle the blocking popup first!
        StylingPage stylingPage = new StylingPage();

        // 2. Login
        LoginTestMethod();
        stylingPage.handlePrivacyPopup();

        // 3. Hover and Navigate
        stylingPage.hoverSaleMenu(); // Use the method inside StylingPage
        stylingPage.clickViewAllSale();

        // 4. Verify Styles (Colors and Strikethrough)
        stylingPage.verifySaleProductStyles();
    }

    public void fifthTestMethod() {
        StylingPage stylingPage = new StylingPage();
//        stylingPage.handlePrivacyPopup();

        LoginTestMethod();

        // 1. Navigate
        stylingPage.navigateToAllMen();

        // 2. Filter Black
        stylingPage.filterByBlack();

        // 3. Verify Border (Using our refreshed logic)
        stylingPage.verifyBlackColorBorder();

        // 4. Filter Price (Teacher's requirement vs Reality)
        stylingPage.filterByPriceRange();

        // 5. Verify 3 products and the REAL price range ($70+)
        // Good job catching that the $0-$99 range doesn't exist!
        stylingPage.verifyProductCountAndPrice(3, 70.00, 9999.00);

        System.out.println("Test 5 Passed with adjusted price parameters.");
    }

    public void sixthTestMethod() {
        LoginTestMethod();
        ShoppingItemsPage shoppingPage = new ShoppingItemsPage();

        shoppingPage.navigateToWomenCategory();
        shoppingPage.sortByPrice();
        shoppingPage.addFirstItemToWishlist();
        shoppingPage.waitForWishlistPageToLoad();

        shoppingPage.navigateToWomenCategory();
        shoppingPage.sortByPrice();
        shoppingPage.addSecondItemToWishlist();
        shoppingPage.waitForWishlistPageToLoad();

        basePageObject.getWaitUtils().waitForUrlToContain("wishlist");

        shoppingPage.openAccountMenu();
        shoppingPage.clickMyWishlistFromMenu();

        org.openqa.selenium.support.ui.WebDriverWait wait =
                new org.openqa.selenium.support.ui.WebDriverWait(BaseInformation.getDriver(), java.time.Duration.ofSeconds(10));

        try {
            wait.until(d -> ShoppingItemsElement.wishlistItems.size() == 2);
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Actual items found: " + ShoppingItemsElement.wishlistItems.size());
        }

        int actualCount = ShoppingItemsElement.wishlistItems.size();
        org.testng.Assert.assertEquals(actualCount, 2, "Wishlist should have 2 items!");
    }

    public void seventhTestMethod() {
        LoginTestMethod();
        ShoppingItemsPage shoppingPage = new ShoppingItemsPage();

        // --- PART 1: POPULATE WISHLIST ---
        // (Assuming you have logic here from Test 6 to add 2 items to wishlist)
        // If not, simply call your add methods here.
        shoppingPage.navigateToWomenCategory();
        shoppingPage.sortByPrice();
        shoppingPage.addFirstItemToWishlist();
        shoppingPage.waitForWishlistPageToLoad();

        shoppingPage.navigateToWomenCategory();
        shoppingPage.sortByPrice();
        shoppingPage.addSecondItemToWishlist();
        shoppingPage.waitForWishlistPageToLoad();

        // --- PART 2: MOVE ITEMS TO CART ---
        for(int i = 0; i < 2; i++) {
            // Go to wishlist if we aren't there (for the 2nd item)
            if(i > 0) {
                shoppingPage.openAccountMenu();
                shoppingPage.clickMyWishlistFromMenu();
                shoppingPage.waitForWishlistPageToLoad();
            }

            // Click the product image
            shoppingPage.clickProductImageInWishlist(i);

            // Select options and add
            shoppingPage.selectFirstColorAndSize();
            shoppingPage.clickAddToCart();
            shoppingPage.verifyAddToCartSuccess();
        }

        // --- PART 3: CART UPDATES ---
        shoppingPage.openAccountMenu();
        shoppingPage.clickMyCartFromAccountMenu();

        // Wait for Cart Title
        shoppingPage.waitForCartPageToLoad();

        // Update Quantity (Logic: Type -> Wait for btn -> Click)
        shoppingPage.updateCartQuantity(0, "2");

        // --- PART 4: VERIFY ---
        shoppingPage.verifyGrandTotalMatch();
    }

    public void eighthTestMethod() {
        LoginTestMethod();
        ShoppingItemsPage shoppingPage = new ShoppingItemsPage();

        // --- STEP 0: PRECONDITION (Populate Cart with 2 items) ---
        // (We reuse the logic from Test 7 to ensure we have data to delete)

        // Add Item 1
        shoppingPage.navigateToWomenCategory();
        shoppingPage.sortByPrice();
        shoppingPage.addFirstItemToWishlist();
        shoppingPage.waitForWishlistPageToLoad();

        // Add Item 2
        shoppingPage.navigateToWomenCategory();
        shoppingPage.sortByPrice();
        shoppingPage.addSecondItemToWishlist();
        shoppingPage.waitForWishlistPageToLoad();

        // Move both to Cart
        for(int i = 0; i < 2; i++) {
            if(i > 0) {
                shoppingPage.openAccountMenu();
                shoppingPage.clickMyWishlistFromMenu();
                shoppingPage.waitForWishlistPageToLoad();
            }
            shoppingPage.clickProductImageInWishlist(i);
            shoppingPage.selectFirstColorAndSize();
            shoppingPage.clickAddToCart();
            shoppingPage.verifyAddToCartSuccess();
        }

        shoppingPage.openAccountMenu();
        shoppingPage.clickMyCartFromAccountMenu();

        // --- TEST 8 START ---

        int currentCount = shoppingPage.getCartItemCount();
        System.out.println("Starting deletion. Initial items: " + currentCount);

        while (currentCount > 0) {
            // 1. Delete (Now uses JS Click + Wait)
            shoppingPage.removeFirstItem();

            // 2. Get New Count
            int newCount = shoppingPage.getCartItemCount();
            System.out.println("Deleted item. Old: " + currentCount + " -> New: " + newCount);

            // 3. Verify
            org.testng.Assert.assertEquals(newCount, currentCount - 1, "Item count mismatch!");

            // 4. Update for next iteration
            currentCount = newCount;
        }

        // Verify Empty Message
        shoppingPage.verifyCartIsEmpty();
    }

}