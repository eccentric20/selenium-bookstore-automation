package com.automation.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import com.automation.utils.WaitUtils;

public class CartPage {
	
	WebDriver driver;
    WaitUtils wait;
    
    @FindBy(xpath = "//div[@class='product-info']//a[1]")
    List<WebElement> cartItems;

    @FindBy(xpath = "//body[1]/div[4]/form[1]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[2]/span[1]")
    List<WebElement> cartPrices;
    
    @FindBy(xpath = "//body[1]/div[4]/form[1]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[4]/span/span[2]")
    WebElement totalPrice;
    
    @FindBy(id = "summary-total")
    WebElement cartTotal;
    
    
    
    
    
    @FindBy(xpath = "//button[normalize-space()='+']")
    WebElement plusButton;

    @FindBy(xpath = "//button[normalize-space()='-']")
    WebElement minusButton;
    
    @FindBy(xpath = "(//input[@class='qty-input'])[1]")
    WebElement quantityInput;
    
    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }
    
    public int getQuantity() {
        wait.until(ExpectedConditions.visibilityOf(quantityInput));
        return Integer.parseInt(quantityInput.getAttribute("value"));
    }
    
    public int getProductTotal() {
        String total = totalPrice.getText().replaceAll("[^0-9]", "");
        return Integer.parseInt(total);
    }

    public int getCartSummaryTotal() {
        String total = cartTotal.getText().replaceAll("[^0-9]", "");
        return Integer.parseInt(total);
    }
    
    public int getCartItemCount() {
        wait.waitForListToLoad(cartItems);
        return cartItems.size();
    }

    public String getFirstCartItemTitle() {
        return cartItems.get(0).getText();
    }

    public String getFirstCartItemPrice() {
        return cartPrices.get(0).getText();
    }
    
    public void clickPlus() {
        plusButton.click();
        wait.until(ExpectedConditions.attributeToBeNotEmpty(quantityInput, "value"));
    }

    public void clickMinus() {
        minusButton.click();
        wait.until(ExpectedConditions.attributeToBeNotEmpty(quantityInput, "value"));
    }
}
