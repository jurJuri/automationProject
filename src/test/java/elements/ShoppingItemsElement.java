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

    @FindBy(css = ".success-msg")
    public WebElement successMessage;

    @FindBy(css = "#wishlist-table tbody tr[id^='item_']")
    public static List<WebElement> wishlistItems;
}