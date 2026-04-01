package com.automation.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;

public class WaitUtils {
	WebDriver driver;
    WebDriverWait wait;
    
 // Constructor
    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
 // Wait for element visible
    public WebElement waitForVisibility(WebElement productTitle) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated((By) productTitle));
    }
    
 // Wait for element clickable
    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
 // Wait for all element visible
    public List<WebElement> waitForAllVisibility(List<WebElement> productTitles) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By) productTitles));
    }
    
    public WebElement waitForElement(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    public void waitForListToLoad(List<WebElement> elements) {
        wait.until(driver -> elements.size() > 0);
    }

    public void until(ExpectedCondition<?> condition) {
        wait.until(condition);
    }

	
}
