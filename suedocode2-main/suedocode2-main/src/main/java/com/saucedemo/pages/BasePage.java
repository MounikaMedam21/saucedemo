package com.saucedemo.pages;

import com.microsoft.playwright.Page;

//Base page with common methods for all pages

public class BasePage {
    protected Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    /**
     * Navigates to the specified URL
     * @param url URL to navigate to
     */
    public void navigate(String url) {
        page.navigate(url);
    }

    /**
     * Waits for the specified timeout
     * @param ms milliseconds to wait
     */
    public void waitForTimeout(int ms) {
        page.waitForTimeout(ms);
    }
}