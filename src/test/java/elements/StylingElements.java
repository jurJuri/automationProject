package elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.BaseInformation;

import java.util.List;

public class StylingElements {
    public StylingElements(){
        PageFactory.initElements(BaseInformation.getDriver(), this);
    }
    // pop up
    // Privacy Popup Elements
    @FindBy(id = "privacy_pref_optout")
    public WebElement optOutRadioButton;

    // Usually there is a 'Confirm' or 'Submit' button in these prompts.
    // If there isn't one, we will use a JS script to hide it.
    @FindBy(css = ".privacy_prompt_content button, .privacy_prompt_content input[type='submit']")
    public WebElement privacySubmitButton;

    // task 3 elements
    @FindBy(css = "li.view-all a[href*='women.html']")
    public WebElement viewAllWomenLink;
    @FindBy(css = "a.level0.has-children[href='https://ecommerce.tealiumdemo.com/women.html']")
    public WebElement WomanButton;
    @FindBy(css = ".products-grid .item.last")
    public WebElement firstProductItem;
    @FindBy(css = ".products-grid .item.last h2.product-name a")
    public WebElement firstProductLink;

    // task 4 elements
    // StylingElements.java
//    @FindBy(css = "a.level0.has-children[href*='sale.html']")
//    public WebElement saleMenu;

//    @FindBy(css = "li.view-all a[href*='sale.html']")
//    public WebElement viewAllSaleLink;
    // Selectors for the price elements found in your provided HTML
    @FindBy(css = "p.old-price span.price")
    public List<WebElement> allOldPrices;
    @FindBy(css = "p.special-price span.price")
    public List<WebElement> allSpecialPrices;
//    @FindBy(css = "ul.products-grid li.item")
//    public List<WebElement> allProductItems;

    // StylingElements.java
    @FindBy(css = "li.nav-5 > a")
    public static WebElement saleMenu;

    @FindBy(css = "li.view-all a[href*='sale.html']")
    public static WebElement viewAllSaleLink;

// This targets the individual product cards in the grid
//    @FindBy(css = "ul.products-grid li.item")
//    public List<WebElement> allProductItems;

    // task 5
    // Point 1: Men Menu
    @FindBy(css = "li.nav-2 > a")
    public WebElement menMenu;

    @FindBy(css = "li.nav-2 li.view-all a")
    public WebElement viewAllMenLink;

    // Point 2: Black Color Filter in Shopping Options sidebar
    @FindBy(css = "a.swatch-link[href*='color=20']")
    public WebElement blackColorSidebarFilter;

    // Point 3: Product Grid and Selected Swatches
//    @FindBy(css = "ul.products-grid li.item")
//    public List<WebElement> allProductItems;

    // Point 4: Price Filter ($0.00 - $99.99)
    @FindBy(css = "a[href*='price=70-']")
    public WebElement priceFilterFirstOption;

    // Price elements for verification
//    @FindBy(css = ".price-box .price")
//    public List<WebElement> allProductPrices;

    @FindBy(css = ".col-main .products-grid > li.item")
    public List<WebElement> allProductItems;

    // Also update the price selector to be specific to the main grid
    @FindBy(css = ".col-main .products-grid .price-box .regular-price .price, .col-main .products-grid .price-box .special-price .price")
    public List<WebElement> allProductPrices;
    // This finds all the rows in the wishlist table so we can count them
}
