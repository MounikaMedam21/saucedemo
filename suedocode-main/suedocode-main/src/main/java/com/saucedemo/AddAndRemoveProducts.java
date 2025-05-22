package sauceDemo.factory;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
public class AddAndRemoveProducts {


	public class InventoryCartPage {
	    private final Page page;

	    // Locators for inventory actions
	    private final String backpackAddButton = "[data-test='add-to-cart-sauce-labs-backpack']";
	    private final String boltShirtAddButton = "[data-test='add-to-cart-sauce-labs-bolt-t-shirt']";
	    private final String cartIcon = ".shopping_cart_link";

	    // Locators for cart actions
	    private final String cartItems = ".cart_item";
	    private final String removeBoltShirtButton = "[data-test='remove-sauce-labs-bolt-t-shirt']";

	    public InventoryCartPage(Page page) {
	        this.page = page;
	    }

	    // Inventory actions
	    public void addBackpackToCart() {
	        page.click(backpackAddButton);
	    }

	    public void addBoltShirtToCart() {
	        page.click(boltShirtAddButton);
	    }

	    public void goToCart() {
	        page.click(cartIcon);
	    }

	    // Cart actions
	    public int getCartItemCount() {
	        Locator items = page.locator(cartItems);
	        return items.count();
	    }

	    public void removeBoltShirtFromCart() {
	        page.click(removeBoltShirtButton);
	    }
	}


}
