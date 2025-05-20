package com.saucedemo.pages;

import com.microsoft.playwright.Page;

/**
 * Page Object for the Login page
 */
public class LoginPage extends BasePage {
    // Selectors
    private final String usernameInputSelector = "#user-name";
    private final String passwordInputSelector = "#password";
    private final String loginButtonSelector = "#login-button";

    public LoginPage(Page page) {
        super(page);
    }

    /**
     * Navigates to the login page
     */
    public void navigateToLoginPage() {
        navigate("https://www.saucedemo.com/");
    }

    /**
     * Logs in with the specified credentials
     * @param username Username
     * @param password Password
     * @return ProductsPage - the next page after login
     */
    public ProductsPage login(String username, String password) {
        page.fill(usernameInputSelector, username);
        page.fill(passwordInputSelector, password);
        page.click(loginButtonSelector);
        return new ProductsPage(page);
    }
}