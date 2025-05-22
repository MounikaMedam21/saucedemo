package sauceDemo.factory;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class InventoryCartPage {
    private final Page page;
    private final String backpackAddButton = "[data-test='add-to-cart-sauce-labs-backpack']";
    private final String boltShirtAddButton = "[data-test='add-to-cart-sauce-labs-bolt-t-shirt']";
    private final String cartIcon = ".shopping_cart_link";
    private final String cartItems = ".cart_item";
    private final String removeBoltShirtButton = "[data-test='remove-sauce-labs-bolt-t-shirt']";

    public InventoryCartPage(Page page) {
        this.page = page;
    }

    public void addBackpackToCart() {
        page.click(backpackAddButton);
    }

    public void addBoltShirtToCart() {
        page.click(boltShirtAddButton);
    }

    public void goToCart() {
        page.click(cartIcon);
    }

    public int getCartItemCount() {
        Locator items = page.locator(cartItems);
        return items.count();
    }

    public void removeBoltShirtFromCart() {
        page.click(removeBoltShirtButton);
    }
}
