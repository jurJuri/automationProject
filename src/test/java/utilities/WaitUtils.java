package utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WaitUtils {
    private BaseInformation baseInformation;
    private Duration defaultDuration;
    private WebDriverWait wait;

    private WebDriver getDriver() {
        return BaseInformation.getDriver();
    }
    public WaitUtils(BaseInformation baseInformation, Duration defaultDuration) {
        this.baseInformation = baseInformation;
        this.defaultDuration = defaultDuration;
        this.wait = new WebDriverWait(BaseInformation.getDriver(), Duration.ofSeconds(10));
    }

    public static void waitFor(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e1) {
            System.out.println("ERROR in waitForMethod");
        }
    }


    public WebElement waitForElementVisibleWithCustomTime(long mills, By locator) {
        WebDriverWait wait = new WebDriverWait(getDriver(),  Duration.ofMillis(mills));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementVisibleWithCustomTime(long mills, WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(),  Duration.ofMillis(mills));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForElementclicableWithCustomTime(long mills, WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(),  Duration.ofMillis(mills));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitForElementClickable(WebElement element) {
        return waitForElementclicableWithCustomTime(defaultDuration.toMillis(), element);
    }

    public WebElement waitForElementVisible(WebElement element) {
        return waitForElementVisibleWithCustomTime(defaultDuration.toMillis(), element);
    }

    public WebElement waitForElementVisible(By locator) {
        return waitForElementVisibleWithCustomTime(defaultDuration.toMillis(), locator);
    }

    public void waitForAttributePresentWithCustomWaitTime(long mills, WebElement element,
                                                          String nameOfAttribute) {
        int milsWaitStep = 500;
        long numberOfLoops = mills / milsWaitStep;
        for (int i = 0; i < numberOfLoops; i++) {
            try {
                element.getAttribute(nameOfAttribute);
            } catch (Exception ex) {
                waitFor(milsWaitStep);
            }
        }
    }

    public void waitForAttributePresent(WebElement element, String nameOfAttribute) {
        waitForAttributePresentWithCustomWaitTime(defaultDuration.toMillis(), element, nameOfAttribute);
    }

    public List<WebElement> waitForAllElementsVisible(List<WebElement> elements) {
        WebDriverWait wait = new WebDriverWait(getDriver(), defaultDuration);
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
        return elements;
    }


    public void waitForElementAbsent(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), defaultDuration);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }



    public WebElement waitForElementPresent(long mills, WebElement element) {
        int milsWaitStep = 500;
        long numberOfLoops = mills / milsWaitStep;
        for (int i = 0; i < numberOfLoops; i++) {
            try {
                element.getLocation();
                return element;
            } catch (Exception ex) {
                waitFor(milsWaitStep);
            }
        }
        throw new AssertionError("Target element absent");
    }

    public boolean waitForUrlContains(String fraction) {
        // This waits until the URL changes to include the filter (e.g., "price=70-")
        return wait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains(fraction));
    }
    public WebElement waitForElementPresent(WebElement element) {
        waitForElementPresent(30000, element);
        waitFor(1000);
        return element;
    }
    public void clickWithJS(WebElement element) {
        ((JavascriptExecutor) BaseInformation.getDriver()).executeScript("arguments[0].click();", element);
    }

    public void waitForUrlToContain(String fraction) {
        // This tells Selenium to pause until the URL changes
        // to include the text you provided (like "order=price")
        wait.until(ExpectedConditions.urlContains(fraction));
    }

}
