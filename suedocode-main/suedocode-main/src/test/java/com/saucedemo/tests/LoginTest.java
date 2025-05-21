package sauceDemo.tests;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.*;
import sauceDemo.factory.LoginPage;

public class LoginTest {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private LoginPage loginPage;

    @BeforeClass
    public void setUp() {
        // Initialize Playwright and launch browser
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeMethod
    public void createContextAndPage() {
        // Create a new browser context and page for each test
        context = browser.newContext();
        page = context.newPage();
        loginPage = new LoginPage(page);
        loginPage.navigateToLoginPage();
    }

    @Test
    public void testValidLoginAndInventoryVerification() {
        // Perform login
        loginPage.loginPage("standard_user", "secret_sauce");

        // Verify navigation to inventory page
        Assert.assertTrue(loginPage.isAtInventoryPage(), "User should be redirected to inventory page after login");

        // Verify product count
        int productCount = loginPage.getProductCount();
        Assert.assertEquals(productCount, 6, "There should be exactly 6 products on the inventory page");
    }

    @AfterMethod
    public void closeContext() {
        // Close the browser context after each test
        context.close();
    }

    @AfterClass
    public void tearDown() {
        // Close browser and Playwright
        browser.close();
        playwright.close();
    }
}
