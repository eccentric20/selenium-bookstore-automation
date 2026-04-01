package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.factory.DriverFactory;
import com.automation.pages.HomePage;

import com.automation.pages.ProductPage;
import com.automation.pages.SearchResultsPage;
import com.automation.utils.log;

import io.opentelemetry.api.logs.Logger;

import com.automation.pages.CartPage;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.testng.annotations.DataProvider;

public class CartTests extends BaseTest {
	
	
	
	@Test
	public void verifyAddToCartSingleProduct() throws InterruptedException {
		
		log.info("Starting test: verifyAddToCartSingleProduct");
		
		HomePage home = new HomePage(DriverFactory.getDriver());
//		home.goToLogin();
//		
//		LoginPage login = new LoginPage(driver);
//        login.enterMobileNumber("8103765525");
//
//        login.clickGenerateOTP();
//        Thread.sleep(15000);
//        login.clickLoginBtn();

		log.info("Searching for book: Harry Potter");
		
	    home.searchBook("Harry Potter");
	    
	    Thread.sleep(1000);

	    SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());

	    String expectedTitle = results.getFirstProductTitle();
	    String expectedPrice = results.getFirstProductPrice();

	    results.clickFirstProduct();

	    ProductPage product = new ProductPage(DriverFactory.getDriver());

	    product.addToCart();
	    
	    product.closeLogin();

	    // Navigate to cart (adjust based on site)
	    home.goToCart();
	    
	    

	    CartPage cart = new CartPage(DriverFactory.getDriver());

	    String actualTitle = cart.getFirstCartItemTitle();
	    String actualPrice = cart.getFirstCartItemPrice();

	    // ✅ Validate title
	    Assert.assertTrue(
	            actualTitle.contains(expectedTitle),
	            "Product not added correctly"
	    );

	    // ✅ Validate price
	    Assert.assertEquals(
	            actualPrice.replaceAll("[^0-9]", ""),
	            expectedPrice.replaceAll("[^0-9]", ""),
	            "Price mismatch in cart"
	    );

	    // ✅ Validate count
	    Assert.assertEquals(cart.getCartItemCount(), 1,
	            "Cart item count incorrect");
	}
	
	@Test
	public void verifyQuantityIncrement() throws InterruptedException {
		
		log.info("Starting test: verifyQuantityIncrement");
		
		HomePage home = new HomePage(DriverFactory.getDriver());
//		home.goToLogin();
//		
//		LoginPage login = new LoginPage(driver);
//        login.enterMobileNumber("8103765525");
//
//        login.clickGenerateOTP();
//        Thread.sleep(15000);
//        login.clickLoginBtn();

	    
		
	    home.searchBook("Harry Potter");
	    
	    Thread.sleep(1000);

	    SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());

	    String expectedTitle = results.getFirstProductTitle();
	    String expectedPrice = results.getFirstProductPrice();

	    results.clickFirstProduct();

	    ProductPage product = new ProductPage(DriverFactory.getDriver());

	    product.addToCart();
	    
	    product.closeLogin();

	    // Navigate to cart (adjust based on site)
	    home.goToCart();

	    CartPage cart = new CartPage(DriverFactory.getDriver());

	    int initialQty = cart.getQuantity();
	    
	    
	    String priceText = cart.getFirstCartItemPrice();
	    int unitPrice = Integer.parseInt(priceText.replaceAll("[^0-9]", ""));
	    
	    System.out.println(unitPrice);

	    cart.clickPlus();
	    log.info("Clicked + button");
	    
	    Thread.sleep(1000);

	    int updatedQty = cart.getQuantity();
	    int productTotal = cart.getProductTotal();
	    int summaryTotal = cart.getCartSummaryTotal();

	    // Validate quantity increment
	    Assert.assertEquals(updatedQty, initialQty + 1,
	            "Quantity did not increment correctly");

	    // Validate product total calculation
	    Assert.assertEquals(productTotal, unitPrice * updatedQty,
	            "Product total calculation incorrect after increment");

	    // Validate cart summary update
	    Assert.assertEquals(summaryTotal, productTotal,
	            "Cart summary not updated correctly after increment");
	}
	
	@Test
	public void verifyQuantityDecrement() throws InterruptedException {
		
		HomePage home = new HomePage(DriverFactory.getDriver());
//		home.goToLogin();
//		
//		LoginPage login = new LoginPage(driver);
//        login.enterMobileNumber("8103765525");
//
//        login.clickGenerateOTP();
//        Thread.sleep(15000);
//        login.clickLoginBtn();

	    
		
	    home.searchBook("Harry Potter");
	    
	    Thread.sleep(1000);

	    SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());

	    String expectedTitle = results.getFirstProductTitle();
	    String expectedPrice = results.getFirstProductPrice();

	    results.clickFirstProduct();

	    ProductPage product = new ProductPage(DriverFactory.getDriver());

	    product.addToCart();
	    
	    product.closeLogin();

	    // Navigate to cart (adjust based on site)
	    home.goToCart();

	    CartPage cart = new CartPage(DriverFactory.getDriver());

	    int initialQty = cart.getQuantity();
	    String priceText = cart.getFirstCartItemPrice();
	    int unitPrice = Integer.parseInt(priceText.replaceAll("[^0-9]", ""));

	    cart.clickMinus();
	    Thread.sleep(1000);

	    int updatedQty = cart.getQuantity();
	    int productTotal = cart.getProductTotal();

	    if (initialQty > 1) {

	        // Validate quantity decrement
	        Assert.assertEquals(updatedQty, initialQty - 1,
	                "Quantity did not decrement correctly");

	        // Validate total update
	        Assert.assertEquals(productTotal, unitPrice * updatedQty,
	                "Product total incorrect after decrement");

	    } else {

	        // Validate it does not go below 1
	        Assert.assertEquals(updatedQty, 1,
	                "Quantity should not go below 1");
	    }
	}
	
	@Test
	public void verifyProductTotalCalculation() throws InterruptedException {
		
		HomePage home = new HomePage(DriverFactory.getDriver());
//		home.goToLogin();
//		
//		LoginPage login = new LoginPage(driver);
//        login.enterMobileNumber("8103765525");
//
//        login.clickGenerateOTP();
//        Thread.sleep(15000);
//        login.clickLoginBtn();

	    
		
	    home.searchBook("Harry Potter");
	    
	    Thread.sleep(1000);

	    SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());

	    String expectedTitle = results.getFirstProductTitle();
	    String expectedPrice = results.getFirstProductPrice();

	    results.clickFirstProduct();

	    ProductPage product = new ProductPage(DriverFactory.getDriver());

	    product.addToCart();
	    
	    product.closeLogin();

	    // Navigate to cart (adjust based on site)
	    home.goToCart();
	    
	    

	    CartPage cart = new CartPage(DriverFactory.getDriver());
	    
	    cart.clickPlus();
	    
	    Thread.sleep(1000);

	    int quantity = cart.getQuantity();
	    String priceText = cart.getFirstCartItemPrice();
	    int unitPrice = Integer.parseInt(priceText.replaceAll("[^0-9]", ""));
	    int productTotal = cart.getProductTotal();

	    int expectedTotal = unitPrice * quantity;

	    Assert.assertEquals(
	            productTotal,
	            expectedTotal,
	            "Product total calculation mismatch (Unit Price × Quantity)"
	    );
	}
	
	@Test
	public void verifyCartSummaryMatchesProductTotal() throws InterruptedException {
		
		HomePage home = new HomePage(DriverFactory.getDriver());
//		home.goToLogin();
//		
//		LoginPage login = new LoginPage(driver);
//        login.enterMobileNumber("8103765525");
//
//        login.clickGenerateOTP();
//        Thread.sleep(15000);
//        login.clickLoginBtn();

	    
		
	    home.searchBook("Harry Potter");
	    
	    Thread.sleep(1000);

	    SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());

	    String expectedTitle = results.getFirstProductTitle();
	    String expectedPrice = results.getFirstProductPrice();

	    results.clickFirstProduct();

	    ProductPage product = new ProductPage(DriverFactory.getDriver());

	    product.addToCart();
	    
	    product.closeLogin();

	    // Navigate to cart (adjust based on site)
	    home.goToCart();
	    
	    

	    CartPage cart = new CartPage(DriverFactory.getDriver());
	    cart.clickPlus();
	    
	    Thread.sleep(1000);
	    
	    cart.clickPlus();
	    
	    Thread.sleep(1000);

	    int productTotal = cart.getProductTotal();
	    int summaryTotal = cart.getCartSummaryTotal();

	    Assert.assertEquals(
	            summaryTotal,
	            productTotal,
	            "Cart summary total does not match product total"
	    );
	}
}
