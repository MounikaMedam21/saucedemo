package com.saucedemo.pages;

import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;
    private final String usernameSelector = "#user-name";
    private final String passwordSelector = "#password";
    private final String loginButtonSelector = "#login-button";
    private final String errorMessageSelector = "[data-test='error']";

    public LoginPage(Page page) {
        this.page = page;
    }

    public void login(String username, String password) {
        page.fill(usernameSelector, username);
        page.fill(passwordSelector, password);
        page.click(loginButtonSelector);
    }

    public boolean isErrorMessageDisplayed() {
        return page.isVisible(errorMessageSelector);
    }

    public String getErrorMessage() {
        return page.textContent(errorMessageSelector);
    }

    public boolean isLoginPage() {
        return page.url().endsWith("saucedemo.com/") || page.url().endsWith("saucedemo.com/index.html");
    }

    public void clearLoginFields() {
        page.fill(usernameSelector, "");
        page.fill(passwordSelector, "");
    }
}