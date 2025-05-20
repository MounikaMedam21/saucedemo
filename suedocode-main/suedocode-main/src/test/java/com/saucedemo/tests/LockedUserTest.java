package com.saucedemo.tests;

import com.microsoft.playwright.*;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.*;

public class LockedUserTest {
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
    public void testLockedOutUser() {
        // Initialize page objects
        LoginPage loginPage = new LoginPage(page);
        InventoryPage inventoryPage = new InventoryPage(page);

        // Get credentials from config
        String lockedUsername = "locked_out_user"; // Hardcoded for clarity
        String lockedPassword = "secret_sauce";     // Hardcoded for clarity
        String expectedErrorMessage = "Epic sadface: Sorry, this user has been locked out."; // Expected error message

        // Attempt login with locked_out_user
        loginPage.login(lockedUsername, lockedPassword);

        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for locked out user");

        // Verify correct error message is shown
        String actualErrorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage,
                "Error message should indicate user is locked out");

        // Ensure user is still on login page
        Assert.assertTrue(loginPage.isLoginPage(),
                "User should remain on login page when using locked_out_user");

        // Verify user is not navigated to inventory page
        Assert.assertFalse(page.url().contains("/inventory.html"),
                "User should not be redirected to inventory page");
    }

    @Test
    public void testContrastValidUserWithLockedUser() {
        // Initialize page objects
        LoginPage loginPage = new LoginPage(page);

        // First attempt with locked user
        loginPage.login("locked_out_user", "secret_sauce");

        // Verify error message and still on login page
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for locked out user");
        Assert.assertTrue(loginPage.isLoginPage(),
                "User should remain on login page when using locked_out_user");

        // Clear fields and try with standard user
        loginPage.clearLoginFields();
        loginPage.login("standard_user", "secret_sauce");

        // Verify successful login
        Assert.assertTrue(page.url().contains("/inventory.html"),
                "Standard user should be redirected to inventory page");
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
