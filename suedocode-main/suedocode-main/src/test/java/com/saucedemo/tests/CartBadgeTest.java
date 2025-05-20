package com.saucedemo.tests;

import com.microsoft.playwright.*;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.CartPage;
import com.saucedemo.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Arrays;
import java.util.List;

public class CartBadgeTest {
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
    public void testCartBadgeCountAddMultipleItems() {
        // Initialize page objects
        InventoryPage inventoryPage = new InventoryPage(page);

        // Get available products
        List<String> availableProducts = inventoryPage.getAvailableProductNames();

        // Select first two products
        List<String> selectedProducts = availableProducts.subList(0, 2);

        // Add products to cart
        inventoryPage.addMultipleProductsToCart(selectedProducts);

        // Verify cart badge count on inventory page
        int inventoryPageBadgeCount = inventoryPage.getCartBadgeCount();
        Assert.assertEquals(inventoryPageBadgeCount, 2,
                "Cart badge should show 2 items on inventory page");

        // Navigate to cart
        page.click(".shopping_cart_link");

        // Initialize cart page
        CartPage cartPage = new CartPage(page);

        // Verify cart badge count on cart page
        int cartPageBadgeCount = cartPage.getCartBadgeCount();
        Assert.assertEquals(cartPageBadgeCount, 2,
                "Cart badge should show 2 items on cart page");

        // Verify cart contains correct items
        List<String> cartItems = cartPage.getCartItemNames();
        Assert.assertEquals(cartItems, selectedProducts,
                "Cart should contain the selected products");
    }

    @Test
    public void testCartBadgeCountRemoveItem() {
        // Initialize page objects
        InventoryPage inventoryPage = new InventoryPage(page);

        // Get available products
        List<String> availableProducts = inventoryPage.getAvailableProductNames();

        // Select first two products
        List<String> selectedProducts = availableProducts.subList(0, 2);

        // Add products to cart
        inventoryPage.addMultipleProductsToCart(selectedProducts);

        // Navigate to cart
        page.click(".shopping_cart_link");

        // Initialize cart page
        CartPage cartPage = new CartPage(page);

        // Remove one item
        cartPage.removeItem(selectedProducts.get(0));

        // Verify cart badge count updates to 1
        int cartPageBadgeCount = cartPage.getCartBadgeCount();
        Assert.assertEquals(cartPageBadgeCount, 1,
                "Cart badge should show 1 item after removing an item");

        // Verify remaining cart item
        List<String> remainingCartItems = cartPage.getCartItemNames();
        Assert.assertEquals(remainingCartItems,
                selectedProducts.subList(1, 2),
                "Cart should contain only the remaining product");
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