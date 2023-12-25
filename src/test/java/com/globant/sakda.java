package com.globant;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class sakda {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.bing.com/");
    }

    @Test
    public void testBingSearch() {
        String query = "Selenium WebDriver";

        // Search for the query
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(query);
        searchBox.submit();

        // Wait for search results to load (you might need to implement a proper wait here)
        // For simplicity, we'll use a sleep here, but you should replace it with an explicit wait
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Get search results
        List<WebElement> searchResults = driver.findElements(By.cssSelector(".b_algo"));

        // Assert that there are at least 1 result
        Assert.assertTrue(searchResults.size() > 0, "Expected at least 1 search result");

        // Print titles of the search results
        for (WebElement result : searchResults) {
            WebElement titleElement = result.findElement(By.tagName("h2"));
            System.out.println(titleElement.getText());
        }
    }

}


