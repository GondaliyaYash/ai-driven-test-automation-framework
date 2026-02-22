package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

/**
 * ProductsPage — Page Object for /products
 */
public class ProductsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ---- Product Listing ----
    @FindBy(css = ".productinfo")
    public List<WebElement> productCards;

    @FindBy(css = ".features_items .col-sm-4")
    public List<WebElement> productItems;

    // ---- Search ----
    @FindBy(id = "search_product")
    public WebElement searchInput;

    @FindBy(id = "submit_search")
    public WebElement searchButton;

    // ---- Search Results (use broader selector for post-search page) ----
    @FindBy(css = ".productinfo p, #search-section .productinfo p, .features_items .productinfo p")
    public List<WebElement> searchResults;

    // ---- Category sidebar ----
    @FindBy(css = ".left-sidebar")
    public WebElement sidebar;

    // ---- Product Detail modal ----
    @FindBy(css = ".modal-content")
    public WebElement productModal;

    @FindBy(css = ".modal-footer a[href='/view_cart']")
    public WebElement viewCartFromModal;

    @FindBy(css = "button.close-modal, button[data-dismiss='modal']")
    public WebElement continueShoppingButton;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /** Click "Add to cart" for the product at the given 0-based index */
    public void addProductToCartByIndex(int index) {
        wait.until(ExpectedConditions.visibilityOfAllElements(productItems));
        WebElement item = productItems.get(index);
        WebElement addBtn = item.findElement(By.cssSelector(".productinfo a.add-to-cart, .add-to-cart"));
        wait.until(ExpectedConditions.elementToBeClickable(addBtn));
        addBtn.click();
    }

    /** Click "View Product" link for the product at the given 0-based index */
    public void viewProductByIndex(int index) {
        wait.until(ExpectedConditions.visibilityOfAllElements(productItems));
        // Dismiss any ad overlays first
        dismissAds();
        WebElement item = productItems.get(index);
        // Scroll item into view
        ((org.openqa.selenium.JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView({block:'center'});", item);
        try { Thread.sleep(300); } catch (Exception ignored) {}
        
        // Try multiple selectors for the View Product link
        WebElement viewBtn = null;
        String[] selectors = {
            "a[href*='/product_details/']",
            ".choose a",
            "a.btn"
        };
        for (String sel : selectors) {
            try {
                java.util.List<org.openqa.selenium.WebElement> btns = 
                    item.findElements(org.openqa.selenium.By.cssSelector(sel));
                if (!btns.isEmpty()) {
                    viewBtn = btns.get(0);
                    break;
                }
            } catch (Exception ignored) {}
        }
        
        if (viewBtn == null) {
            throw new RuntimeException("Could not find View Product link for index " + index);
        }
        
        // Use JS click to avoid element being intercepted by ads
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", viewBtn);
        } catch (Exception e) {
            viewBtn.click();
        }
    }
    
    /** Dismiss ad iframes/overlays that might intercept clicks */
    private void dismissAds() {
        try {
            // Close any ad overlay iframe
            java.util.List<org.openqa.selenium.WebElement> iframes = 
                driver.findElements(org.openqa.selenium.By.tagName("iframe"));
            for (org.openqa.selenium.WebElement iframe : iframes) {
                try {
                    String src = iframe.getAttribute("src");
                    if (src != null && (src.contains("ad") || src.contains("google") || src.contains("doubleclick"))) {
                        ((org.openqa.selenium.JavascriptExecutor) driver)
                            .executeScript("arguments[0].style.display='none';", iframe);
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception ignored) {}
    }

    public void searchProduct(String productName) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys(productName);
        searchButton.click();
    }

    public boolean isModalDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(productModal));
            return productModal.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickViewCartFromModal() {
        wait.until(ExpectedConditions.elementToBeClickable(viewCartFromModal));
        viewCartFromModal.click();
    }

    public void clickContinueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton));
        continueShoppingButton.click();
    }

    public int getSearchResultCount() {
        try {
            Thread.sleep(1000); // wait for search results to load
        } catch (Exception ignored) {}
        // Re-find elements fresh after search
        java.util.List<WebElement> results = driver.findElements(
            By.cssSelector(".productinfo p, #search-section .productinfo p, .features_items .productinfo p")
        );
        return results.size();
    }
}
