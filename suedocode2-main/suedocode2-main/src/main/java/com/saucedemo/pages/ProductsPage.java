package com.saucedemo.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import java.util.ArrayList;
import java.util.List;

/**
 * Page Object for the Products page
 */
public class ProductsPage extends BasePage {
    // Selectors
    private final String inventoryItemSelector = ".inventory_item";
    private final String itemNameSelector = ".inventory_item_name";
    private final String addToCartButtonSelector = "button[id^='add-to-cart']";
    private final String removeButtonSelector = "button[id^='remove']";
    private final String cartIconSelector = ".shopping_cart_link";
    private final String cartBadgeSelector = ".shopping_cart_badge";
    private final String productsSortContainerSelector = ".product_sort_container";

    public ProductsPage(Page page) {
        super(page);
    }

    /**
     * Gets all product names on the page
     * @return List of product names
     */
    public List<String> getAllProductNames() {
        List<String> productNames = new ArrayList<>();
        Locator products = page.locator(itemNameSelector);

        for (int i = 0; i < products.count(); i++) {
            productNames.add(products.nth(i).textContent());
        }

        return productNames;
    }

    /**
     * Gets a product name by index
     * @param index Index of the product
     * @return Product name
     */
    public String getProductNameByIndex(int index) {
        Locator products = page.locator(itemNameSelector);
        if (index < products.count()) {
            return products.nth(index).textContent();
        }
        return null;
    }

    /**
     * Adds a specific product to the cart by index
     * @param index Index of the product to add
     * @return Product name that was added
     */
    public String addProductToCartByIndex(int index) {
        Locator addButtons = page.locator(addToCartButtonSelector);
        Locator products = page.locator(itemNameSelector);

        if (index < addButtons.count() && index < products.count()) {
            String productName = products.nth(index).textContent();
            addButtons.nth(index).click();
            return productName;
        }
        return null;
    }

    /**
     * Adds all products to the cart
     * @return List of product names added to the cart
     */
    public List<String> addAllProductsToCart() {
        List<String> addedProducts = new ArrayList<>();
        Locator addButtons = page.locator(addToCartButtonSelector);
        Locator products = page.locator(itemNameSelector);

        int count = addButtons.count();
        for (int i = 0; i < count; i++) {
            String productName = products.nth(i).textContent();
            addedProducts.add(productName);
            addButtons.first().click();
        }

        return addedProducts;
    }

    /**
     * Removes products at the specified indices from the cart
     * @param indices Indices of products to remove
     * @return List of product names removed from the cart
     */
    public List<String> removeProductsByIndices(List<Integer> indices) {
        List<String> removedProducts = new ArrayList<>();
        Locator removeButtons = page.locator(removeButtonSelector);
        Locator products = page.locator(itemNameSelector);

        // First, collect the product names to be removed
        for (int index : indices) {
            if (index < products.count()) {
                String productName = products.nth(index).textContent();
                removedProducts.add(productName);
            }
        }

        // Then remove them (in reverse order to avoid index shifting)
        for (int i = indices.size() - 1; i >= 0; i--) {
            int index = indices.get(i);
            if (index < removeButtons.count()) {
                removeButtons.nth(index).click();
                // Wait a bit for the UI to update
                waitForTimeout(100);
            }
        }

        return removedProducts;
    }

    /**
     * Removes every nth product from the cart
     * @param n Remove every nth product (e.g., 2 for every second product)
     * @return List of product names removed from the cart
     */
    public List<String> removeEveryNthProduct(int n) {
        List<Integer> indicesToRemove = new ArrayList<>();
        Locator removeButtons = page.locator(removeButtonSelector);

        int count = removeButtons.count();
        for (int i = n - 1; i < count; i += n) {
            indicesToRemove.add(i);
        }

        return removeProductsByIndices(indicesToRemove);
    }

    /**
     * Gets the cart count from the cart badge
     * @return Cart count
     */
    public int getCartCount() {
        Locator cartBadge = page.locator(cartBadgeSelector);
        if (cartBadge.isVisible()) {
            return Integer.parseInt(cartBadge.textContent());
        }
        return 0;
    }

    /**
     * Navigates to the shopping cart
     * @return CartPage - the cart page
     */
    public CartPage goToCart() {
        page.click(cartIconSelector);
        return new CartPage(page);
    }

    /**
     * Checks if the products page is loaded
     * @return True if the products page is loaded, false otherwise
     */
    public boolean isProductsPageLoaded() {
        // Check if products sort container is visible - this is unique to the products page
        return page.locator(productsSortContainerSelector).isVisible();
    }
}
