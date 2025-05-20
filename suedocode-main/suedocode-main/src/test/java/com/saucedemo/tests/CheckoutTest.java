package com.saucedemo.tests;

import com.microsoft.playwright.*;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.*;

public class CheckoutTest {
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
    public void testSuccessfulPurchaseFlow() {
        // Initialize page objects
        InventoryPage inventoryPage = new InventoryPage(page);
        CheckoutPage checkoutPage = new CheckoutPage(page);

        // Add first product to cart
        inventoryPage.addFirstProductToCart();

        // Proceed to checkout
        inventoryPage.proceedToCheckout();

        // Fill checkout information
        checkoutPage.fillCheckoutInformation(
                "John",
                "Doe",
                "12345"
        );

        // Complete purchase
        checkoutPage.completePurchase();

        // Verify checkout completion
        Assert.assertTrue(checkoutPage.isCheckoutComplete(),
                "Checkout should be completed");

        // Verify confirmation header
        String confirmationHeader = checkoutPage.getConfirmationHeader();
        Assert.assertEquals(confirmationHeader, "Thank you for your order!",
                "Confirmation header should match expected text");

        // Verify confirmation message
        String confirmationMessage = checkoutPage.getConfirmationMessage();
        Assert.assertTrue(
                confirmationMessage.contains("Your order has been dispatched"),
                "Confirmation message should indicate successful order dispatch"
        );
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