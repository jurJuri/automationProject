package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.time.Duration;

public class BasePageObject {
    private ElementLocatorFactory rootFactory;
    private final Duration defaultDuration = Duration.ofSeconds(10);
    private BaseInformation baseInformation;
    private WaitUtils waitUtils;
    private WebElementUtils webElementUtils;

    private final WebDriver driver = BaseInformation.getDriver();


    public BasePageObject(BaseInformation baseInformation) {
        this.baseInformation = baseInformation;
        rootFactory = new DefaultElementLocatorFactory(driver);
        PageFactory.initElements(rootFactory, this);
    }

    public BasePageObject(BaseInformation baseInformation, By locator) {
        this.baseInformation = baseInformation;
        try{
            getWaitUtils().waitForElementVisible(locator);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
        rootFactory = new DefaultElementLocatorFactory(
                driver.findElement(locator));
        PageFactory.initElements(rootFactory, this);
    }

    public BasePageObject(BaseInformation baseInformation, WebElement rootElement) {
        this.baseInformation = baseInformation;
        rootFactory = new DefaultElementLocatorFactory(rootElement);
        PageFactory.initElements(rootFactory, this);
    }

    public void getUrl(String url){
        driver.get(url);
    }

    public WaitUtils getWaitUtils() {
        if (waitUtils == null) {
            waitUtils = new WaitUtils(getBaseInformation(), defaultDuration);
        }
        return waitUtils;
    }

    public WebElementUtils getWebElementUtils() {
        if (webElementUtils == null) {
            webElementUtils = new WebElementUtils(getBaseInformation(), defaultDuration);
        }
        return webElementUtils;
    }

    public BaseInformation getBaseInformation() {
        return baseInformation;
    }

    public void scrollAndClick(WebElement element) {
        // 1. Scroll the element into view (align to center/top to avoid footer coverage)
        ((org.openqa.selenium.JavascriptExecutor) BaseInformation.getDriver())
                .executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);

        // 2. Wait a split second for scrolling to finish
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        // 3. Try standard click first
        try {
            getWaitUtils().waitForElementClickable(element).click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            // 4. Fallback: Force JS Click if still covered
            System.out.println("Standard click intercepted. Using JS Click fallback.");
            ((org.openqa.selenium.JavascriptExecutor) BaseInformation.getDriver())
                    .executeScript("arguments[0].click();", element);
        }
    }

}
