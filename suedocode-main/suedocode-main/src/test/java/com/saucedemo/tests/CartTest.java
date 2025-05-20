package com.saucedemo.tests;

import com.microsoft.playwright.*;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.CartPage;
import com.saucedemo.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class CartTest {
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
    public void testAddAndRemoveItemsFromCart() {
        // Initialize page objects
        InventoryPage inventoryPage = new InventoryPage(page);
        CartPage cartPage = new CartPage(page);

        // Add two specific items to cart
        String backpackItem = "Sauce Labs Backpack";
        String tShirtItem = "Sauce Labs Bolt T-Shirt";

        inventoryPage.addProductToCart(backpackItem);
        inventoryPage.addProductToCart(tShirtItem);

        // Navigate to cart
        inventoryPage.navigateToCart();

        // Verify items in cart
        List<String> cartItems = cartPage.getCartItemNames();
        Assert.assertEquals(cartItems.size(), 2, "Cart should contain 2 items");
        Assert.assertTrue(cartItems.contains(backpackItem), "Backpack should be in cart");
        Assert.assertTrue(cartItems.contains(tShirtItem), "T-Shirt should be in cart");

        // Remove one item
        cartPage.removeItem(tShirtItem);

        // Verify only one item remains
        List<String> remainingCartItems = cartPage.getCartItemNames();
        Assert.assertEquals(remainingCartItems.size(), 1, "Cart should contain 1 item after removal");
        Assert.assertTrue(remainingCartItems.contains(backpackItem), "Only Backpack should remain in cart");
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