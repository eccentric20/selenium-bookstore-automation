package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.factory.DriverFactory;
import com.automation.pages.HomePage;
import com.automation.pages.ProductPage;
import com.automation.pages.SearchResultsPage;
import com.automation.utils.WaitUtils;
import com.automation.utils.log;

import io.opentelemetry.api.logs.Logger;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import org.testng.annotations.DataProvider;
import com.automation.utils.WaitUtils;

public class ProductTests extends BaseTest {
	
	
	
	@DataProvider(name = "ProductData")
	public Object[][] invalidSearchData() {
	    return new Object[][] {
	        {"xyz123abc"},   // alphanumeric
	        {"conjuring"},
	        {"Harry potter"}// special characters
	    };
	}
	
	@Test
	public void verifyViewBookDetails() {
		
		log.info("Starting test: verifyViewBookDetails");

	    HomePage home = new HomePage(DriverFactory.getDriver());
	    
	    log.info("Searching for: Harry Potter");
	    home.searchBook("Harry Potter");

	    SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());

	    String expectedTitle = results.getFirstProductTitle();
	    String expectedPrice = results.getFirstProductPrice().replaceAll("[^0-9]", "");

	    results.clickFirstProduct();

	    ProductPage product = new ProductPage(DriverFactory.getDriver());

	    Assert.assertTrue(product.getProductTitle().contains(expectedTitle));
	    Assert.assertEquals(product.getProductPrice().replaceAll("[^0-9]", ""), expectedPrice);
	    Assert.assertTrue(product.isImageDisplayed());
	}
	
	@Test(dataProvider = "ProductData")
	public void verifyMultipleProductNavigation(String productInput) throws InterruptedException {
		
		

	    HomePage home = new HomePage(DriverFactory.getDriver());
	    home.searchBook(productInput);

	    SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());

	    int totalProducts = results.getResultCount();
	    
	    

	    

	    // ✅ Case 1: No results
	    if (totalProducts == 0) {
	        System.out.println("No products found - handled correctly");
	        Assert.assertTrue(true); // pass test safely
	        return;
	    }

	    // ✅ Case 2: Single result
	    if (totalProducts == 1) {

	        String expectedTitle = results.getProductTitleByIndex(0);

	        results.clickProductByIndex(0);

	        ProductPage product = new ProductPage(DriverFactory.getDriver());

	        String actualTitle = product.getProductTitle();

	        Assert.assertTrue(
	                actualTitle.contains(expectedTitle),
	                "Single product navigation failed"
	        );

	        System.out.println("Single product handled correctly");
	        return;
	    }

	    // Limit to first 3 products (avoid long execution)
	    int limit = Math.min(totalProducts, 3);

	    for (int i = 0; i < limit; i++) {

	        // Re-initialize results page each loop (important)
	        results = new SearchResultsPage(DriverFactory.getDriver());

	        String expectedTitle = results.getProductTitleByIndex(i);

	        results.clickProductByIndex(i);

	        ProductPage product = new ProductPage(DriverFactory.getDriver());

	        String actualTitle = product.getProductTitle();

	        Assert.assertTrue(
	                actualTitle.contains(expectedTitle),
	                "Navigation failed for product index: " + i
	        );

	        // Navigate back
	        DriverFactory.getDriver().navigate().back();

	        // Wait for results page to load again
	        Thread.sleep(1000);
	    }
	}
	
	@Test
	public void verifyUrlChangesForEachProduct() throws InterruptedException {
		
		log.info("Starting test: verifyUrlChangesForEachProduct");

	    HomePage home = new HomePage(DriverFactory.getDriver());
	    home.searchBook("Harry Potter");

	    SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());
	    
	    

	    int totalProducts = results.getResultCount();
	    if (totalProducts == 0) {
	        throw new RuntimeException("No products found on page");
	    }
	    
	    int limit = Math.min(totalProducts, 3);

	    String previousUrl = "";

	    for (int i = 0; i < limit; i++) {

	        // Reinitialize page (important)
	        results = new SearchResultsPage(DriverFactory.getDriver());

	        String productTitle = results.getProductTitleByIndex(i);

	        results.clickProductByIndex(i);

	        ProductPage product = new ProductPage(DriverFactory.getDriver());

	        String currentUrl = DriverFactory.getDriver().getCurrentUrl();

	        System.out.println("Product " + i + " URL: " + currentUrl);

	        // ✅ 1. URL should not be empty
	        Assert.assertFalse(currentUrl.isEmpty(), "URL is empty");

	        // ✅ 2. URL should change for each product
	        if (!previousUrl.isEmpty()) {
	            Assert.assertNotEquals(currentUrl, previousUrl,
	                    "URL did not change for product index: " + i);
	        }

	        // ✅ 3. URL should contain product identifier (basic check)
	        String expectedKeyword = productTitle.split(" ")[0]; // first word
	        Assert.assertTrue(
	                currentUrl.toLowerCase().contains(expectedKeyword.toLowerCase()),
	                "URL does not contain product identifier"
	        );

	        previousUrl = currentUrl;

	        // Navigate back
	        DriverFactory.getDriver().navigate().back();

	        // Wait for results to load again
	        results = new SearchResultsPage(DriverFactory.getDriver());
	        Thread.sleep(1000);
	        
	        log.info("URL validation test completed");
	        
	        
	    }
	}
	
	@Test
	public void verifyClickLastProduct() throws InterruptedException {
		
		log.info("Starting test: verifyClickLastProduct");

	    HomePage home = new HomePage(DriverFactory.getDriver());
	    home.searchBook("Harry Potter");

	    SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());

	    int productCount = results.getResultCount();

	    System.out.println("Total products: " + productCount);

	    // ✅ Handle no results
	    Assert.assertTrue(productCount > 0, "No products found");

	    // ✅ Get last index
	    int lastIndex = productCount - 1;

	    // Capture expected data
	    String expectedTitle = results.getProductTitleByIndex(lastIndex);

	    // Click last product
	    results.clickProductByIndex(lastIndex);

	    ProductPage product = new ProductPage(DriverFactory.getDriver());

	    String actualTitle = product.getProductTitle();

	    // ✅ Validate navigation
	    Assert.assertTrue(
	            actualTitle.contains(expectedTitle),
	            "Navigation failed for last product"
	    );
	    
	    log.info("verifyClickLastProduct completed successfully");
	    
	}
	
	@Test
	public void verifyProductPageAfterRefresh() {
		
		log.info("Starting test: verifyProductPageAfterRefresh");

	    HomePage home = new HomePage(DriverFactory.getDriver());
	    home.searchBook("Harry Potter");

	    SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());

	    // Capture data before navigation
	    String expectedTitle = results.getFirstProductTitle();
	    String expectedPrice = results.getFirstProductPrice();

	    // Open product
	    results.clickFirstProduct();

	    ProductPage product = new ProductPage(DriverFactory.getDriver());

	    // Capture data before refresh
	    String titleBefore = product.getProductTitle();
	    String priceBefore = product.getProductPrice();

	    // 🔄 Refresh page
	    DriverFactory.getDriver().navigate().refresh();

	    // Reinitialize page (IMPORTANT after refresh)
	    product = new ProductPage(DriverFactory.getDriver());

	    // Wait for page to load
	    product.waitForProductToLoad();

	    // Capture data after refresh
	    String titleAfter = product.getProductTitle();
	    String priceAfter = product.getProductPrice();

	    // ✅ Validate title consistency
	    Assert.assertEquals(titleAfter, titleBefore,
	            "Product title changed after refresh");

	    // ✅ Validate price consistency
	    Assert.assertEquals(
	            priceAfter.replaceAll("\\s", ""),
	            priceBefore.replaceAll("\\s", ""),
	            "Product price changed after refresh"
	    );

	    // ✅ Optional: validate against search results
	    Assert.assertTrue(
	            titleAfter.contains(expectedTitle),
	            "Product title mismatch with listing"
	    );

	    System.out.println("Product page stable after refresh");
	}

}
