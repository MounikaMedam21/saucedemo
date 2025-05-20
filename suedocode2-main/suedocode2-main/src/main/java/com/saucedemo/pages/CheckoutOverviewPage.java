package com.saucedemo.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import java.util.ArrayList;
import java.util.List;

/**
 * Page Object for the Checkout Overview page
 */
public class CheckoutOverviewPage extends BasePage {
    // Selectors
    private final String cartItemSelector = ".cart_item";
    private final String cartItemNameSelector = ".inventory_item_name";
    private final String finishButtonSelector = "#finish";
    private final String cancelButtonSelector = "#cancel";
    private final String subtotalSelector = ".summary_subtotal_label";
    private final String taxSelector = ".summary_tax_label";
    private final String totalSelector = ".summary_total_label";

    public CheckoutOverviewPage(Page page) {
        super(page);
    }

    /**
     * Gets all product names in the checkout overview
     * @return List of product names
     */
    public List<String> getProductNames() {
        List<String> productNames = new ArrayList<>();
        Locator products = page.locator(cartItemNameSelector);

        for (int i = 0; i < products.count(); i++) {
            productNames.add(products.nth(i).textContent());
        }

        return productNames;
    }

    /**
     * Gets the subtotal amount
     * @return Subtotal amount as a string
     */
    public String getSubtotal() {
        return page.locator(subtotalSelector).textContent();
    }

    /**
     * Gets the tax amount
     * @return Tax amount as a string
     */
    public String getTax() {
        return page.locator(taxSelector).textContent();
    }

    /**
     * Gets the total amount
     * @return Total amount as a string
     */
    public String getTotal() {
        return page.locator(totalSelector).textContent();
    }

    /**
     * Clicks the Finish button to complete the order
     * @return CheckoutCompletePage - the checkout complete page
     */
    public CheckoutCompletePage clickFinish() {
        page.click(finishButtonSelector);
        return new CheckoutCompletePage(page);
    }

    /**
     * Clicks the Cancel button to cancel the order
     * @return ProductsPage - returns to the products page
     */
    public ProductsPage clickCancel() {
        page.click(cancelButtonSelector);
        return new ProductsPage(page);
    }
}