package com.saucedemo.pages;

import com.microsoft.playwright.Page;

/**
 * Page Object for the Checkout Complete page
 */
public class CheckoutCompletePage extends BasePage {
    // Selectors
    private final String thankYouHeaderSelector = ".complete-header";
    private final String completeTextSelector = ".complete-text";
    private final String backHomeButtonSelector = "#back-to-products";

    public CheckoutCompletePage(Page page) {
        super(page);
    }

    /**
     * Gets the thank you header text
     * @return Thank you header text
     */
    public String getThankYouHeaderText() {
        return page.locator(thankYouHeaderSelector).textContent();
    }

    /**
     * Gets the complete text message
     * @return Complete text message
     */
    public String getCompleteText() {
        return page.locator(completeTextSelector).textContent();
    }

    /**
     * Clicks the Back Home button
     * @return ProductsPage - returns to the products page
     */
    public ProductsPage clickBackHome() {
        page.click(backHomeButtonSelector);
        return new ProductsPage(page);
    }

    /**
     * Checks if the order completion message is displayed
     * @return True if the order completion message is displayed, false otherwise
     */
    public boolean isOrderCompleted() {
        return page.locator(thankYouHeaderSelector).isVisible();
    }
}