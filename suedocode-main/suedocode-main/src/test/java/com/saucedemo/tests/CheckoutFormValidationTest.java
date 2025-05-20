package com.saucedemo.tests;

import com.saucedemo.pages.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Checkout Form Field Validation Tests
 * - Leave all fields blank and try to continue
 * - Validate error messages for missing First Name, Last Name, and Postal Code
 * - Fill only one field at a time and validate step-wise error behavior
 */
public class CheckoutFormValidationTest extends BaseTest {

    @Test
    @DisplayName("Checkout Form Validation - All Fields Blank")
    public void testAllFieldsBlank() {
        // 1. Login to the application
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToLoginPage();
        ProductsPage productsPage = loginPage.login("standard_user", "secret_sauce");

        // 2. Add a product to cart
        productsPage.addProductToCartByIndex(0);

        // 3. Go to cart and proceed to checkout
        CartPage cartPage = productsPage.goToCart();
        CheckoutPage checkoutPage = cartPage.checkout();

        // 4. Try to continue with all fields blank
        String errorMessage = checkoutPage.attemptContinueExpectingError();

        // 5. Validate error message for first name
        assertTrue(errorMessage.contains("Error") && errorMessage.contains("First Name"),
                "Error message should mention First Name is required");
    }

    @Test
    @DisplayName("Checkout Form Validation - Step by Step Field Validation")
    public void testStepwiseFieldValidation() {
        // 1. Login to the application
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToLoginPage();
        ProductsPage productsPage = loginPage.login("standard_user", "secret_sauce");

        // 2. Add a product to cart
        productsPage.addProductToCartByIndex(0);

        // 3. Go to cart and proceed to checkout
        CartPage cartPage = productsPage.goToCart();
        CheckoutPage checkoutPage = cartPage.checkout();

        // 4. Fill only first name and try to continue
        checkoutPage.fillFirstName("John")
                .clearAllFields()
                .fillFirstName("John");

        String errorMessage = checkoutPage.attemptContinueExpectingError();
        assertTrue(errorMessage.contains("Error") && errorMessage.contains("Last Name"),
                "Error message should mention Last Name is required");

        // 5. Fill first and last name, but no postal code
        checkoutPage.clearAllFields()
                .fillFirstName("John")
                .fillLastName("Doe");

        errorMessage = checkoutPage.attemptContinueExpectingError();
        assertTrue(errorMessage.contains("Error") && errorMessage.contains("Postal Code"),
                "Error message should mention Postal Code is required");

        // 6. Fill only last name and try to continue
        checkoutPage.clearAllFields()
                .fillLastName("Doe");

        errorMessage = checkoutPage.attemptContinueExpectingError();
        assertTrue(errorMessage.contains("Error") && errorMessage.contains("First Name"),
                "Error message should mention First Name is required");

        // 7. Fill only postal code and try to continue
        checkoutPage.clearAllFields()
                .fillPostalCode("12345");

        errorMessage = checkoutPage.attemptContinueExpectingError();
        assertTrue(errorMessage.contains("Error") && errorMessage.contains("First Name"),
                "Error message should mention First Name is required");

        // 8. Fill first name and postal code, but no last name
        checkoutPage.clearAllFields()
                .fillFirstName("John")
                .fillPostalCode("12345");

        errorMessage = checkoutPage.attemptContinueExpectingError();
        assertTrue(errorMessage.contains("Error") && errorMessage.contains("Last Name"),
                "Error message should mention Last Name is required");

        // 9. Fill last name and postal code, but no first name
        checkoutPage.clearAllFields()
                .fillLastName("Doe")
                .fillPostalCode("12345");

        errorMessage = checkoutPage.attemptContinueExpectingError();
        assertTrue(errorMessage.contains("Error") && errorMessage.contains("First Name"),
                "Error message should mention First Name is required");

        // 10. Finally, fill all fields correctly and verify no error
        checkoutPage.clearAllFields()
                .fillFirstName("John")
                .fillLastName("Doe")
                .fillPostalCode("12345");

        Object nextPage = checkoutPage.clickContinue();
        assertTrue(nextPage instanceof CheckoutOverviewPage,
                "Should proceed to CheckoutOverviewPage when all fields are filled correctly");
    }
}