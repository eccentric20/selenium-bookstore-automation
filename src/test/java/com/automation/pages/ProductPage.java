package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.utils.WaitUtils;

public class ProductPage {
	WebDriver driver;
	WaitUtils wait;

    // Locator for "About the book" section
    @FindBy(xpath = "//a[normalize-space()='About the Book']")
    WebElement aboutBookSection;
    
    @FindBy(css = "h1[class='product-name']")  // adjust
    WebElement productTitle;

    @FindBy(css = "span[class='price']")
    WebElement productPrice;

    @FindBy(id = "bbiprocurrimg")
    WebElement productImage;
    
    @FindBy(id = "cartadd") 
    WebElement addToCartBtn;
    
    @FindBy(css = "button[aria-label='Close']") 
    WebElement closeBtn;
    
    
    
    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
        
    }
    
    public String getProductTitle() {
    	return wait.waitForElement(productTitle).getText();
    }
    

    public String getProductPrice() {
        return productPrice.getText();
    }

    public boolean isImageDisplayed() {
        return productImage.isDisplayed();
    }
    
    public void addToCart() {
        wait.waitForElement(addToCartBtn).click();
    }
    
    public boolean isAboutBookSectionDisplayed() {
        try {
            return aboutBookSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void waitForProductToLoad() {
        wait.waitForElement(productTitle);
    }
    
    public void closeLogin() {
    	wait.waitForElement(closeBtn);
    	closeBtn.click();
    }

}
