package sauceDemo.factory;
import com.microsoft.playwright.Page;

public class InventoryPage {
	    private final Page page;
	    private final String inventoryItem = ".inventory_item";

	    public InventoryPage(Page page) {
	        this.page = page;
	    }
	 public boolean isAtInventoryPage() {
	        return page.url().equals("https://www.saucedemo.com/inventory.html");
	    }

	    public int getProductCount() {
	        return page.locator(inventoryItem).count();
	    }
	}




