package sauceDemo.factory;

import com.microsoft.playwright.Page;

public class LoginPage {
	
	Page page;
	
	//Locators for login page
	private String usernameInputField = "#user-name";
	private String passwordInputField = "#password";
	private String loginButton = "#login-button";
	// Locator for inventory items
    private final String inventoryItem = ".inventory_item";
	
	//Page Constructor
	public LoginPage(Page page) {
		this.page = page;
	}
	
	//Navigate to the login page
	public void navigateToLoginPage() {
        page.navigate("https://www.saucedemo.com/");
    }
		
	public void loginPage(String Username, String Password) {
		page.fill(usernameInputField, Username);
		page.fill(passwordInputField, Password);
		page.click(loginButton);
		
	}
	
	// Verify if current URL is the inventory page
    public boolean isAtInventoryPage() {
        return page.url().equals("https://www.saucedemo.com/inventory.html");
    }

    // Count the number of products displayed
    public int getProductCount() {
        return page.locator(inventoryItem).count();
    }

}



    


