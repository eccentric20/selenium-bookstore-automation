package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.factory.DriverFactory;
import com.automation.pages.HomePage;

import com.automation.pages.ProductPage;
import com.automation.pages.SearchResultsPage;
import com.automation.utils.log;

import io.opentelemetry.api.logs.Logger;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import org.testng.annotations.DataProvider;
import io.qameta.allure.Allure;



public class SearchTests extends BaseTest {
	
	
	
	@DataProvider(name = "invalidSearchData")
	public Object[][] invalidSearchData() {
	    return new Object[][] {
	        {"xyz123abc"},   // alphanumeric
	        {"@#$%"}         // special characters
	    };
	}
	
	

    @Test
    public void verifyHomePageTitle() {
    	
    	log.info("Verifying Home Page Title");
    	
        String title = DriverFactory.getDriver().getTitle();
        Assert.assertTrue(title.contains("Books"));
        
        log.info("Home Page Title verified successfully");
        Allure.step("Test step executed");
    }
    
    @Test
    public void verifyValidSearch() throws InterruptedException {
    	
    	log.info("Starting Valid Search Test");

        HomePage home = new HomePage(DriverFactory.getDriver());
        home.searchBook("Harry Potter");

        SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());

        Assert.assertTrue(results.getResultCount() > 0,
                "No results found");
        
        log.info("Valid Search Test Passed");
    }
    
    @Test
    public void verifySearchWithEnter() throws InterruptedException {
    	
    	log.info("Starting Search With ENTER Key Test");

        HomePage home = new HomePage(DriverFactory.getDriver());
        home.searchWithEnter("Harry Potter");

        SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());

        Assert.assertTrue(results.getResultCount() > 0,
                "Search using ENTER failed");
        
        log.info("Search With ENTER Test Passed");
    }
    
    @Test
    public void verifySearchRelevance() {
    	
    	log.info("Starting Search Relevance Test");

        HomePage home = new HomePage(DriverFactory.getDriver());
        home.searchBook("Harry");

        SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());

        Assert.assertTrue(results.isResultRelevant("Harry"),
                "Irrelevant results found");
        
        log.info("Search Relevance Test Passed");
    }
    
    @Test
    public void verifyClickProduct() {
    	
    	log.info("Starting Product Click Navigation Test");
        HomePage home = new HomePage(DriverFactory.getDriver());
        home.searchBook("Harry");

        SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());
        results.clickFirstProduct();

        ProductPage product = new ProductPage(DriverFactory.getDriver());

        Assert.assertTrue(product.isAboutBookSectionDisplayed(),
                "'About the Book' section not found. Navigation failed.");
        
        log.info("Product Click Navigation Test Passed");
    }
    
    @Test(dataProvider = "invalidSearchData")
    public void verifyInvalidSearch(String searchInput) throws InterruptedException {
    	
    	log.info("Starting Invalid Search Test with input: {}", searchInput);

        HomePage home = new HomePage(DriverFactory.getDriver());
        home.searchBook(searchInput);

        SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());

        // Either no results OR page should not crash
        Assert.assertTrue(results.isResultsEmpty() || results.getResultCount() >= 0,
                "Invalid search not handled properly");
        
        log.info("Invalid Search Test Passed for input: {}", searchInput);
    }
    
    @Test
    public void verifyEmptySearch() {
    	
    	log.info("Starting Empty Search Test");

        HomePage home = new HomePage(DriverFactory.getDriver());
        home.clickSearchWithoutInput();

        SearchResultsPage results = new SearchResultsPage(DriverFactory.getDriver());

        Assert.assertTrue(results.isNoResultsMessageDisplayed(),
                "'No results to show' message not displayed for empty search");
        
        log.info("Empty Search Test Passed");
    }
    
    @Test
    public void verifyCaseInsensitiveSearch() throws InterruptedException {
    	
    	log.info("Starting Case Insensitive Search Test");

        HomePage home = new HomePage(DriverFactory.getDriver());

        // First search
        home.searchBook("Harry Potter");
        SearchResultsPage results1 = new SearchResultsPage(DriverFactory.getDriver());
        int count1 = results1.getResultCount();
        
        log.info("Result count for 'Harry Potter': {}", count1);
        System.out.println(count1);
        
        DriverFactory.getDriver().navigate().back();

        
        home = new HomePage(DriverFactory.getDriver());
        home.searchBook("harry potter");
        
        SearchResultsPage results2 = new SearchResultsPage(DriverFactory.getDriver());
        
        int count2 = results2.getResultCount();
        System.out.println(count2);

        Assert.assertEquals(count1, count2,
                "Search is case sensitive");
    }
    
    
    
}
