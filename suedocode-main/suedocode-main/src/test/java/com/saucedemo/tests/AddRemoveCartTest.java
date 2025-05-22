package sauceDemo.tests;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.microsoft.playwright.*;
import sauceDemo.factory.LoginPage;
import sauceDemo.factory.InventoryPage;
import sauceDemo.factory.InventoryCartPage;

public class AddRemoveCartTest {
    private Playwright playwright;
    private Browser browser;
    private Page page;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private InventoryCartPage inventoryCartPage;

    @BeforeTest
    public void setUp() {
        // Initialize Playwright and launch the browser in non-headless mode
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        // Instantiate page objects

        loginPage = new LoginPage(page);
        inventoryPage = new InventoryPage(page);
        inventoryCartPage = new InventoryCartPage(page);
        // Navigate to the login page
        loginPage.navigateToLoginPage();
    }

    @Test
    public void testAddAndRemoveProducts() {
        // Perform login with valid credentials

        loginPage.loginPage("standard_user", "secret_sauce");
        // Assert that the user is redirected to the inventory page
        Assert.assertTrue(inventoryPage.isAtInventoryPage(), "Not at inventory page");
        
        // Add products to the cart
        inventoryCartPage.addBackpackToCart();
        inventoryCartPage.addBoltShirtToCart();
        
        // Navigate to the cart and verify the number of items
        inventoryCartPage.goToCart();
        Assert.assertEquals(inventoryCartPage.getCartItemCount(), 2, "Cart should contain 2 items.");
        
        // Remove a product from the cart and verify the updated item count
        inventoryCartPage.removeBoltShirtFromCart();
        Assert.assertEquals(inventoryCartPage.getCartItemCount(), 1, "Cart should contain 1 item.");
        
        
    }

    @AfterTest
    public void tearDown() {
        // Close the browser and Playwright instance
        browser.close();
        playwright.close();
    }
}
