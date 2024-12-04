package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CommonOperations {
    private WebDriver driver;
    int sleepTime;

    public CommonOperations(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isPageLoading(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = "return document.readyState != 'complete';";
        return (Boolean) js.executeScript(script);
    }

    public void waitUntilPageLoaded(WebDriver driver) {
        while(this.isPageLoading(driver)) {
            try {
                Thread.sleep((long)this.sleepTime);
            } catch (InterruptedException var3) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void waitForSpecificTime(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void dropDownSelectByText(WebElement webElement, String visibleText) {
        Select select = new Select(webElement);
        select.selectByVisibleText(visibleText);
    }

    public static void dropDownSelectByIndex(WebElement webElement, int indexValue) {
        Select select = new Select(webElement);
        select.selectByIndex(indexValue);
    }

    public static void dropDownSelectByValue(WebElement webElement, String value) {
        Select select = new Select(webElement);
        select.selectByValue(value);
    }

    public void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,250)");
    }
}
