package com.saucedemo.tests;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Edge case test for adding all products and removing alternates
 */
public class CartEdgeCaseTest extends BaseTest {

    @Test
    @DisplayName("Add all products to cart and remove every second product")
    public void testAddAllProductsAndRemoveAlternates() {
        // 1. Login to the application
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToLoginPage();
        ProductsPage productsPage = loginPage.login("standard_user", "secret_sauce");

        // 2. Get all product names for verification later
        List<String> allProductNames = productsPage.getAllProductNames();
        System.out.println("All products: " + allProductNames);

        // 3. Add all products to the cart
        List<String> addedProducts = productsPage.addAllProductsToCart();
        System.out.println("Added products: " + addedProducts);

        // 4. Verify that all products are added to the cart by checking the cart count
        int cartCount = productsPage.getCartCount();
        assertEquals(allProductNames.size(), cartCount, "Cart count should match the number of products");

        // 5. Remove every second product from the cart
        List<String> removedProducts = productsPage.removeEveryNthProduct(2);
        System.out.println("Removed products: " + removedProducts);

        // 6. Calculate expected remaining products
        List<String> expectedRemainingProducts = new ArrayList<>(allProductNames);
        expectedRemainingProducts.removeAll(removedProducts);
        System.out.println("Expected remaining products: " + expectedRemainingProducts);

        // 7. Go to the cart page
        CartPage cartPage = productsPage.goToCart();

        // 8. Get actual products in the cart
        List<String> actualProductsInCart = cartPage.getCartProductNames();
        System.out.println("Actual products in cart: " + actualProductsInCart);

        // 9. Verify that only the correct items remain in the cart
        assertEquals(expectedRemainingProducts.size(), actualProductsInCart.size(),
                "Number of items in cart should match expected");

        // Verify each expected product is in the cart
        for (String expectedProduct : expectedRemainingProducts) {
            assertTrue(actualProductsInCart.contains(expectedProduct),
                    "Cart should contain product: " + expectedProduct);
        }

        // Verify each product in the cart is expected
        for (String actualProduct : actualProductsInCart) {
            assertTrue(expectedRemainingProducts.contains(actualProduct),
                    "Product in cart should be expected: " + actualProduct);
        }
    }
}
