package com.saucedemo.tests;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Back Navigation Check test
 * Add an item to cart -> go to cart -> click "Continue Shopping"
 * Verify redirection back to inventory page
 * Verify redirection back to inventory page
 * Ensure cart still reflects the added item
 */
public class BackNavigationTest extends BaseTest {

    @Test
    @DisplayName("Back Navigation Check - Continue Shopping functionality")
    public void testBackNavigationCheck() {
        // 1. Login to the application
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToLoginPage();
        ProductsPage productsPage = loginPage.login("standard_user", "secret_sauce");

        // 2. Get the name of the first product to add to cart
        String productToAdd = productsPage.getProductNameByIndex(0);
        System.out.println("Product to add: " + productToAdd);

        // 3. Add the product to cart
        productsPage.addProductToCartByIndex(0);

        // 4. Verify the cart count is updated
        int cartCountBeforeNavigation = productsPage.getCartCount();
        assertEquals(1, cartCountBeforeNavigation, "Cart count should be 1 after adding an item");

        // 5. Go to the cart page
        CartPage cartPage = productsPage.goToCart();

        // 6. Verify the product is in the cart
        String productInCart = cartPage.getCartProductNames().get(0);
        assertEquals(productToAdd, productInCart, "The product in cart should match the added product");

        // 7. Click "Continue Shopping" button
        ProductsPage productsPageAfterNavigation = cartPage.continueShopping();

        // 8. Verify we're back on the inventory page by checking the URL or a unique element
        String currentUrl = page.url();
        assertTrue(currentUrl.contains("inventory.html"),
                "URL should contain 'inventory.html' after clicking Continue Shopping");

        // Alternative verification: Check if a products page specific element is visible
        assertTrue(productsPageAfterNavigation.isProductsPageLoaded(),
                "Products page should be loaded after clicking Continue Shopping");

        // 9. Ensure cart still reflects the added item
        int cartCountAfterNavigation = productsPageAfterNavigation.getCartCount();
        assertEquals(1, cartCountAfterNavigation, "Cart count should still be 1 after navigation");
        assertEquals(cartCountBeforeNavigation, cartCountAfterNavigation,
                "Cart count should remain the same after navigation");
    }
}