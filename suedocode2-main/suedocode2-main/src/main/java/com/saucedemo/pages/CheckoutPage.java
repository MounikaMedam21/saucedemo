package com.saucedemo.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

/**
 * Page Object for the Checkout Information page
 */
public class CheckoutPage extends BasePage {
    // Selectors
    private final String firstNameInputSelector = "#first-name";
    private final String lastNameInputSelector = "#last-name";
    private final String postalCodeInputSelector = "#postal-code";
    private final String continueButtonSelector = "#continue";
    private final String cancelButtonSelector = "#cancel";
    private final String errorMessageSelector = ".error-message-container";

    public CheckoutPage(Page page) {
        super(page);
    }

    /**
     * Fills the first name field
     * @param firstName First name
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage fillFirstName(String firstName) {
        page.fill(firstNameInputSelector, firstName);
        return this;
    }

    /**
     * Fills the last name field
     * @param lastName Last name
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage fillLastName(String lastName) {
        page.fill(lastNameInputSelector, lastName);
        return this;
    }

    /**
     * Fills the postal code field
     * @param postalCode Postal code
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage fillPostalCode(String postalCode) {
        page.fill(postalCodeInputSelector, postalCode);
        return this;
    }

    /**
     * Fills all checkout information fields
     * @param firstName First name
     * @param lastName Last name
     * @param postalCode Postal code
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        fillFirstName(firstName);
        fillLastName(lastName);
        fillPostalCode(postalCode);
        return this;
    }

    /**
     * Clicks the Continue button
     * @return CheckoutOverviewPage if successful, or remains on CheckoutPage if there are errors
     */
    public Object clickContinue() {
        page.click(continueButtonSelector);

        // Check if we're still on the checkout page (error occurred)
        if (isErrorDisplayed()) {
            return this;
        } else {
            // If no error, we should be on the checkout overview page
            return new CheckoutOverviewPage(page);
        }
    }

    /**
     * Attempts to continue with the current form state and expects an error
     * @return Error message text
     */
    public String attemptContinueExpectingError() {
        page.click(continueButtonSelector);
        return getErrorMessage();
    }

    /**
     * Clicks the Cancel button
     * @return CartPage - returns to the cart page
     */
    public CartPage clickCancel() {
        page.click(cancelButtonSelector);
        return new CartPage(page);
    }

    /**
     * Checks if an error message is displayed
     * @return True if an error message is displayed, false otherwise
     */
    public boolean isErrorDisplayed() {
        Locator errorElement = page.locator(errorMessageSelector);
        return errorElement.isVisible();
    }

    /**
     * Gets the error message text
     * @return Error message text, or empty string if no error
     */
    public String getErrorMessage() {
        Locator errorElement = page.locator(errorMessageSelector);
        if (errorElement.isVisible()) {
            return errorElement.textContent();
        }
        return "";
    }

    /**
     * Clears all form fields
     * @return CheckoutPage instance for method chaining
     */
    public CheckoutPage clearAllFields() {
        page.fill(firstNameInputSelector, "");
        page.fill(lastNameInputSelector, "");
        page.fill(postalCodeInputSelector, "");
        return this;
    }
}