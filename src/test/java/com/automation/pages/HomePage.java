package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	WebDriver driver;
	
	// Locators using PageFactory
    @FindBy(id = "keyword")
    WebElement searchBox;
    
 // Search button
    @FindBy(id = "find-books")
    WebElement searchButton;
    
    @FindBy(id = "shopping-cart-box-ontop")
    WebElement cartIcon;
    
    @FindBy(css = ".btn-car.btn-login")
    WebElement loginButton;
    
    
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    

    
    public void searchBook(String bookName) {
        searchBox.clear();
        searchBox.sendKeys(bookName);
        searchButton.click();
    }
    
 // Search using ENTER key
    public void searchWithEnter(String bookName) {
        searchBox.clear();
        searchBox.sendKeys(bookName);
        searchBox.sendKeys(Keys.ENTER);
    }
    
    public void clickSearchWithoutInput() {
        searchBox.clear();
        searchButton.click();
    }
    
    public void goToCart() {
        cartIcon.click();
    }
    
    public void goToLogin() {
        loginButton.click();
    }

}
