package com.saucedemo.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import java.util.ArrayList;
import java.util.List;

/**
 * Page Object for the Cart page
 */
public class CartPage extends BasePage {
    // Selectors
    private final String cartItemSelector = ".cart_item";
    private final String cartItemNameSelector = ".inventory_item_name";
    private final String cartQuantitySelector = ".cart_quantity";
    private final String removeButtonSelector = "button.cart_button";
    private final String continueShoppingButtonSelector = "#continue-shopping";
    private final String checkoutButtonSelector = "#checkout";

    public CartPage(Page page) {
        super(page);
    }

    /**
     * Gets all product names in the cart
     * @return List of product names in the cart
     */
    public List<String> getCartProductNames() {
        List<String> productNames = new ArrayList<>();
        Locator products = page.locator(cartItemNameSelector);

        for (int i = 0; i < products.count(); i++) {
            productNames.add(products.nth(i).textContent());
        }

        return productNames;
    }

    /**
     * Gets the quantity of each item in the cart
     * @return List of quantities
     */
    public List<Integer> getCartItemQuantities() {
        List<Integer> quantities = new ArrayList<>();
        Locator quantityElements = page.locator(cartQuantitySelector);

        for (int i = 0; i < quantityElements.count(); i++) {
            quantities.add(Integer.parseInt(quantityElements.nth(i).textContent()));
        }

        return quantities;
    }

    /**
     * Removes an item from the cart by index
     * @param index Index of the item to remove
     */
    public void removeItemByIndex(int index) {
        Locator removeButtons = page.locator(removeButtonSelector);
        if (index < removeButtons.count()) {
            removeButtons.nth(index).click();
        }
    }

    /**
     * Gets the total number of items in the cart
     * @return Number of items in the cart
     */
    public int getCartItemCount() {
        return page.locator(cartItemSelector).count();
    }

    /**
     * Navigates back to the products page
     * @return ProductsPage - the products page
     */
    public ProductsPage continueShopping() {
        page.click(continueShoppingButtonSelector);
        return new ProductsPage(page);
    }

    /**
     * Proceeds to checkout
     * @return CheckoutPage - the checkout page
     */
    public CheckoutPage checkout() {
        page.click(checkoutButtonSelector);
        return new CheckoutPage(page);
    }
}
