package com.saucedemo.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.ElementHandle;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {
    private final Page page;
    private final String productItemSelector = ".inventory_item";
    private final String cartButtonSelector = ".btn_inventory";
    private final String productPriceSelector = ".inventory_item_price";
    private final String sortDropdownSelector = ".product_sort_container";
    private final String addToCartButtonSelector = ".btn_inventory";
    private final String cartBadgeSelector = ".shopping_cart_badge";

    public InventoryPage(Page page) {
        this.page = page;
    }

    public int getProductCount() {
        List<ElementHandle> products = page.querySelectorAll(productItemSelector);
        return products.size();
    }

    public boolean isInventoryPage() {
        return page.url().contains("/inventory.html");
    }

    public void addProductToCart(String productName) {
        page.click(String.format("//div[contains(text(), '%s')]/ancestor::div[@class='inventory_item']//button", productName));
    }

    public void navigateToCart() {
        page.click(".shopping_cart_link");
    }

    // New sorting methods
    public void sortBy(String option) {
        page.selectOption(sortDropdownSelector, option);
    }

    public List<Double> getProductPrices() {
        return page.querySelectorAll(productItemSelector).stream()
                .map(item -> {
                    String priceText = item.querySelector(productPriceSelector).innerText().replace("$", "");
                    return Double.parseDouble(priceText);
                })
                .collect(Collectors.toList());
    }

    public List<String> getProductNames() {
        return page.querySelectorAll(productItemSelector).stream()
                .map(item -> item.querySelector(".inventory_item_name").innerText())
                .collect(Collectors.toList());
    }

    public void addFirstProductToCart() {
        // Select the first product's add to cart button
        page.click(productItemSelector + " .btn_inventory");
    }

    public void proceedToCheckout() {
        // Click on shopping cart
        page.click(".shopping_cart_link");

        // Click checkout button
        page.click("#checkout");
    }

    public void addMultipleProductsToCart(List<String> productNames) {
        productNames.forEach(this::addProductToCart);
    }

    public int getCartBadgeCount() {
        // Check if cart badge exists before getting text
        List<ElementHandle> badges = page.querySelectorAll(cartBadgeSelector);
        return badges.isEmpty() ? 0 : Integer.parseInt(badges.get(0).innerText());
    }

    public List<String> getAvailableProductNames() {
        return page.querySelectorAll(productItemSelector).stream()
                .map(item -> item.querySelector(".inventory_item_name").innerText())
                .collect(Collectors.toList());
    }
}