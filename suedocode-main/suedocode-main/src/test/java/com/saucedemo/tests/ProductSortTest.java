package com.saucedemo.tests;

import com.microsoft.playwright.*;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductSortTest {
    private Playwright playwright;
    private Browser browser;
    private Page page;

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeMethod
    public void createPage() {
        page = browser.newPage();
        page.navigate(ConfigReader.getBaseUrl());

        // Login before each test
        LoginPage loginPage = new LoginPage(page);
        loginPage.login(
                ConfigReader.getUsername(),
                ConfigReader.getPassword()
        );
    }

    @Test
    public void testSortProductsByPriceLowToHigh() {
        // Initialize page object
        InventoryPage inventoryPage = new InventoryPage(page);

        // Sort by Price (low to high)
        inventoryPage.sortBy("lohi");

        // Get product prices
        List<Double> productPrices = inventoryPage.getProductPrices();

        // Validate sorting
        List<Double> sortedPrices = productPrices.stream()
                .sorted()
                .collect(Collectors.toList());

        Assert.assertEquals(productPrices, sortedPrices,
                "Products should be sorted in ascending order by price");

        // Verify the first product has the lowest price
        double lowestPrice = sortedPrices.get(0);
        Assert.assertEquals(productPrices.get(0), lowestPrice,
                "First product should have the lowest price");
    }

    @Test
    public void testSortProductsByPriceHighToLow() {
        // Initialize page object
        InventoryPage inventoryPage = new InventoryPage(page);

        // Sort by Price (high to low)
        inventoryPage.sortBy("hilo");

        // Get product prices
        List<Double> productPrices = inventoryPage.getProductPrices();

        // Validate sorting
        List<Double> sortedPrices = productPrices.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        Assert.assertEquals(productPrices, sortedPrices,
                "Products should be sorted in descending order by price");

        // Verify the first product has the highest price
        double highestPrice = sortedPrices.get(0);
        Assert.assertEquals(productPrices.get(0), highestPrice,
                "First product should have the highest price");
    }

    @AfterMethod
    public void closePage() {
        page.close();
    }

    @AfterClass
    public void tearDown() {
        browser.close();
        playwright.close();
    }
}