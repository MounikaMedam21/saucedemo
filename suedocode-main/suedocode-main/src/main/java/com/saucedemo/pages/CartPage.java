package com.saucedemo.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.ElementHandle;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage {
    private final Page page;
    private final String cartItemSelector = ".cart_item";
    private final String itemNameSelector = ".inventory_item_name";
    private final String removeButtonSelector = ".btn_secondary";
    private final String cartBadgeSelector = ".shopping_cart_badge";

    public CartPage(Page page) {
        this.page = page;
    }

    public List<String> getCartItemNames() {
        return page.querySelectorAll(cartItemSelector).stream()
                .map(item -> item.querySelector(itemNameSelector).innerText())
                .collect(Collectors.toList());
    }

    public int getCartItemCount() {
        return page.querySelectorAll(cartItemSelector).size();
    }

    public void removeItem(String itemName) {
        // Find the remove button for the specific item
        page.click(String.format("//div[contains(text(), '%s')]/ancestor::div[@class='cart_item']//button[contains(@class, 'btn_secondary')]", itemName));
    }

    public int getCartBadgeCount() {
        // Check if cart badge exists before getting text
        List<ElementHandle> badges = page.querySelectorAll(cartBadgeSelector);
        return badges.isEmpty() ? 0 : Integer.parseInt(badges.get(0).innerText());
    }
}