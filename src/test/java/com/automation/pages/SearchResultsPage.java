package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.utils.WaitUtils;

import java.time.Duration;
import java.util.List;

public class SearchResultsPage {
	WebDriver driver;
	WaitUtils wait;

    // Product titles
    @FindBy(xpath = "//div[contains(@class,'product-name')]//a")
	public
    List<WebElement> productTitles;
    
    @FindBy(xpath = "//h2[contains(text(),'No results to show')]")
    WebElement noResultsMessage;
    
    @FindBy(xpath = "//span[@class='price product-price']")
    List<WebElement> productPrices;
    
    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }
    
 // Count results
    public int getResultCount() throws InterruptedException {
    	

        Thread.sleep(1000);
        return productTitles.size();
    }
    
    public boolean isResultRelevant(String keyword) {
        for (WebElement product : productTitles) {
            if (!product.getText().toLowerCase().contains(keyword.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
    
    public String getFirstProductTitle() {
    	wait.waitForElement(productTitles.get(0));
        return productTitles.get(0).getText();
    }

    public String getFirstProductPrice() {
        return productPrices.get(0).getText();
    }
    
 // Click first product
    public void clickFirstProduct() {
        if (productTitles.size() > 0) {
            productTitles.get(0).click();
        }
    }
    
    public boolean isResultsEmpty() {
        return productTitles.size() == 0;
    }
    
    public boolean isNoResultsMessageDisplayed() {
        try {
            return noResultsMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getProductTitleByIndex(int index) {
        wait.waitForElement(productTitles.get(index));
        return productTitles.get(index).getText();
    }

    public void clickProductByIndex(int index) {
        wait.waitForElement(productTitles.get(index)).click();
    }
    
    public void waitForResultsToLoad() {
        wait.waitForListToLoad(productTitles);
    }

	
    
    
    
    
}
