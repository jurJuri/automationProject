package elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class ShoppingItemsElement {

    @FindBy(css = "li.nav-1 > a")
    public WebElement womenMenu;

    @FindBy(css = "li.nav-1 .view-all a")
    public WebElement viewAllWomen;

    @FindBy(css = ".sort-by select")
    public WebElement sortByDropdown;

    @FindBy(css = ".products-grid .price-box .regular-price .price, .products-grid .price-box .special-price .price")
    public List<WebElement> productPrices;

    @FindBy(css = ".products-grid .link-wishlist")
    public List<WebElement> addToWishlistLinks;

    @FindBy(css = ".skip-account")
    public WebElement accountLink;

    @FindBy(css = "#header-account a[title*='Wishlist']")
    public WebElement wishlistMenuLink;

    @FindBy(css = "#wishlist-table tbody tr")
    public List<WebElement> wishlistTableRows;

    @FindBy(css = ".page-title h1")
    public WebElement pageTitle; // Useful to confirm which page we are on

    @FindBy(css = "#wishlist-table tbody tr[id^='item_']")
    public static List<WebElement> wishlistItems;

    //task 7

    // 1. Wishlist Product Images (to click and go to product page)
    @FindBy(css = "#wishlist-table .product-image")
    public List<WebElement> wishlistProductImages;

    // 2. Product Options (Color & Size)
    // We target the lists of options. Usually first UL is Color, Second is Size.
    @FindBy(css = "#product-options-wrapper ul.configurable-swatch-list li:not(.not-available) a")
    public List<WebElement> availableSwatchOptions; // Generic list of all clickable swatches

    // 3. Add to Cart on Product Page
    @FindBy(css = ".add-to-cart-buttons .btn-cart")
    public WebElement addToCartButton;

    // 4. Success Messages
    @FindBy(css = ".success-msg")
    public WebElement successMessage;

    // 5. Cart Page Elements
    @FindBy(css = "#shopping-cart-table input.qty")
    public List<WebElement> cartQtyInputs;

    @FindBy(css = "button[title='Update Shopping Cart']")
    public WebElement updateCartButton;

    @FindBy(css = "#shopping-cart-table .product-cart-price .price")
    public List<WebElement> cartItemPrices;

    @FindBy(css = "#shopping-cart-totals-table strong .price")
    public WebElement grandTotalPrice;

    // test 8 ----------
    // 1. Cart Table Rows (To count how many items are left)
    // We use the ID to be specific.
    @FindBy(css = "#shopping-cart-table tbody tr")
    public List<WebElement> cartTableRows;

    // 2. Remove Buttons (The 'trash bin' or 'Remove Item' link)
    // The user provided HTML shows class="btn-remove btn-remove2"
    @FindBy(css = ".btn-remove2")
    public List<WebElement> removeButtons;

    // 3. Empty Cart Title ("Shopping Cart is Empty")
    @FindBy(css = ".page-title h1")
    public WebElement emptyCartPageTitle;

    // 4. Empty Cart Message Body ("You have no items...")
    // This is inside a div with class 'cart-empty'
    @FindBy(css = ".cart-empty")
    public WebElement emptyCartMessageBody;
}