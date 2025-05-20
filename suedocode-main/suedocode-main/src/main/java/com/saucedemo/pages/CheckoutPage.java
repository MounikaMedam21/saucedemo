package com.saucedemo.pages;

import com.microsoft.playwright.Page;

public class CheckoutPage {
    private final Page page;

    // Selectors
    private final String firstNameSelector = "#first-name";
    private final String lastNameSelector = "#last-name";
    private final String postalCodeSelector = "#postal-code";
    private final String continueButtonSelector = "#continue";
    private final String finishButtonSelector = "#finish";
    private final String completeHeaderSelector = ".complete-header";
    private final String completeTextSelector = ".complete-text";

    public CheckoutPage(Page page) {
        this.page = page;
    }

    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        page.fill(firstNameSelector, firstName);
        page.fill(lastNameSelector, lastName);
        page.fill(postalCodeSelector, postalCode);
        page.click(continueButtonSelector);
    }

    public void completePurchase() {
        page.click(finishButtonSelector);
    }

    public String getConfirmationHeader() {
        return page.textContent(completeHeaderSelector);
    }

    public String getConfirmationMessage() {
        return page.textContent(completeTextSelector);
    }

    public boolean isCheckoutComplete() {
        return page.url().contains("/checkout-complete.html");
    }
}