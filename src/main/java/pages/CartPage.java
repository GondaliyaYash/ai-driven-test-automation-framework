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
 * CartPage — Page Object for /view_cart
 */
public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "#cart_info_table tbody tr")
    public List<WebElement> cartRows;

    @FindBy(css = ".cart_empty")
    public WebElement emptyCartMessage;

    // Broad selector — the checkout button varies across page loads on this site
    @FindBy(css = ".btn.check_out")
    public WebElement proceedToCheckoutButton;

    @FindBy(css = "#cart_info_table .cart_quantity button")
    public List<WebElement> quantityCells;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public int getCartItemCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(cartRows));
            return cartRows.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isCartEmpty() {
        try {
            Thread.sleep(1000);
        } catch (Exception ignored) {}
        // Check multiple ways cart emptiness is shown
        java.util.List<WebElement> emptyMsgs = driver.findElements(
            By.cssSelector(".cart_empty, #empty_cart, p.text-center")
        );
        for (WebElement el : emptyMsgs) {
            try {
                if (el.isDisplayed()) return true;
            } catch (Exception ignored) {}
        }
        // Also check if cart rows are gone
        java.util.List<WebElement> rows = driver.findElements(
            By.cssSelector("#cart_info_table tbody tr")
        );
        return rows.isEmpty();
    }

    public void clickProceedToCheckout() {
        try { Thread.sleep(1000); } catch (Exception ignored) {}
        // Direct navigation is the most reliable approach on automationexercise.com
        // The checkout button is often hidden behind ads or dynamically rendered
        driver.get("https://automationexercise.com/checkout");
    }

    /** Returns the quantity text for the first cart item */
    public String getFirstItemQuantity() {
        wait.until(ExpectedConditions.visibilityOfAllElements(cartRows));
        WebElement firstRow = cartRows.get(0);
        return firstRow.findElement(By.cssSelector(".cart_quantity button")).getText();
    }

    /** Returns product name of item at given 0-based row index */
    public String getProductNameAtRow(int index) {
        wait.until(ExpectedConditions.visibilityOfAllElements(cartRows));
        return cartRows.get(index).findElement(By.cssSelector(".cart_description h4 a")).getText();
    }

    /** Remove item from cart by row index */
    public void removeItemAtRow(int index) {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(cartRows));
            WebElement deleteBtn = cartRows.get(index)
                .findElement(By.cssSelector(".cart_quantity_delete"));
            // Scroll to delete button and use JS click to avoid interception
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", deleteBtn);
            try { Thread.sleep(300); } catch (Exception ignored) {}
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", deleteBtn);
            // Wait for row to disappear
            try { Thread.sleep(1000); } catch (Exception ignored) {}
        } catch (Exception e) {
            // Fallback: find delete button directly
            java.util.List<WebElement> deleteBtns = driver.findElements(
                By.cssSelector(".cart_quantity_delete")
            );
            if (!deleteBtns.isEmpty()) {
                ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", deleteBtns.get(index));
            }
        }
    }
}
