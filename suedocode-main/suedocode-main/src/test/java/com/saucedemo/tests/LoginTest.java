package com.saucedemo.tests;

import com.microsoft.playwright.*;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest {
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
    }

    @Test
    public void testValidLoginAndInventoryVerification() {
        // Initialize page objects
        LoginPage loginPage = new LoginPage(page);
        InventoryPage inventoryPage = new InventoryPage(page);

        // Perform login
        loginPage.login(
                ConfigReader.getUsername(),
                ConfigReader.getPassword()
        );

        // Verify inventory page
        Assert.assertTrue(inventoryPage.isInventoryPage(),
                "User should be redirected to inventory page after login");

        // Verify product count
        int productCount = inventoryPage.getProductCount();
        Assert.assertEquals(productCount, 6,
                "There should be exactly 6 products on the inventory page");
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