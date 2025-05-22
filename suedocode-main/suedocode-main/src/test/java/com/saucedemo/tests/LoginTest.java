package sauceDemo.tests;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.microsoft.playwright.*;

import sauceDemo.factory.LoginPage;
import sauceDemo.factory.InventoryPage;

public class LoginTest {
    private Playwright playwright;
    private Browser browser;
    private Page page;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @BeforeTest
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        loginPage = new LoginPage(page);
        inventoryPage = new InventoryPage(page); // Initialize inventoryPage
        loginPage.navigateToLoginPage();
    }

    @Test
    public void testValidLoginAndInventoryPage() {
        // Perform login
        loginPage.loginPage("standard_user", "secret_sauce");
        // Verify navigation to inventory page
        Assert.assertTrue(loginPage.isAtInventoryPage(), "User is not redirected to the inventory page.");
        // Verify product count
        int productCount = loginPage.getProductCount();
        Assert.assertEquals(productCount, 6, "There should be exactly 6 products on the inventory page");
        Assert.assertEquals(inventoryPage.getProductCount(), 6, "The number of products displayed is not 6.");
    }

    @AfterTest
    public void tearDown() {
        browser.close();
        playwright.close();
    }
}

