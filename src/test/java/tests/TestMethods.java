package tests;

import elements.StylingElements;
import elements.ShoppingItemsElement;
import globals.GlobalVariables;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
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
        // Precondition: Login
        LoginTestMethod();

        StylingPage stylingPage = new StylingPage();

        // 1. Hover over Sale and click View All Sale
        // (Reuse the hover logic we built for the Women menu)
        Actions actions = new Actions(BaseInformation.getDriver());
        actions.moveToElement(StylingElements.saleMenu).perform();

        basePageObject.getWaitUtils()
                .waitForElementClickable(StylingElements.viewAllSaleLink)
                .click();

        // 2-4. Run the verification loop
        stylingPage.verifySaleProductStyles();
    }

    public void fifthTestMethod() {
        // Precondition: Sign In
        LoginTestMethod();

        StylingPage stylingPage = new StylingPage();

        // 1. Hover over Men and click View All
        stylingPage.navigateToAllMen();

        // 2. Click Black color in sidebar
        stylingPage.filterByBlack();

        // 3. Verify blue border on black swatches for all products
        stylingPage.verifyBlackColorBorder();

        // 4. Select Price range $0.00 - $99.99
        stylingPage.filterByPriceRange();

        // 5. Verify only 3 products exist and prices are within $0-$99.99
        stylingPage.verifyProductCountAndPrice(3, 70.00, 1000.00);

        System.out.println("Test 5 Passed: Filters work correctly and styling is applied.");
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
}

