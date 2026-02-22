package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * CheckoutPage — Page Object for /checkout
 */
public class CheckoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ---- Address Section ----
    @FindBy(css = "#address_delivery .address_firstname")
    public WebElement deliveryAddressName;

    @FindBy(css = "#address_delivery .address_address1")
    public WebElement deliveryAddressLine;

    @FindBy(css = "#address_delivery .address_city")
    public WebElement deliveryCity;

    // ---- Order Review ----
    @FindBy(css = "#cart_info tbody tr")
    public java.util.List<WebElement> orderItems;

    @FindBy(css = ".cart_total_price")
    public WebElement totalPrice;

    // ---- Comment & Place Order ----
    // NOTE: automationexercise.com uses name='message' not id='ordermessage'
    @FindBy(css = "textarea[name='message'], #ordermessage, textarea.form-control")
    public WebElement orderCommentTextarea;

    @FindBy(css = "a[href='/payment']")
    public WebElement placeOrderButton;

    // ---- Register/Login prompt (shown to guests) ----
    @FindBy(css = ".modal-body a[href='/login']")
    public WebElement registerLoginLink;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void addOrderComment(String comment) {
        // Scroll to bottom of page where the textarea is
        ((org.openqa.selenium.JavascriptExecutor) driver)
            .executeScript("window.scrollTo(0, document.body.scrollHeight);");
        try { Thread.sleep(500); } catch (Exception ignored) {}
        
        // Try multiple selectors in case site structure varies
        WebElement textarea = null;
        String[] selectors = {
            "textarea[name='message']",
            "#ordermessage",
            "textarea.form-control",
            ".checkout-information textarea"
        };
        for (String sel : selectors) {
            try {
                java.util.List<org.openqa.selenium.WebElement> found = 
                    driver.findElements(org.openqa.selenium.By.cssSelector(sel));
                if (!found.isEmpty()) {
                    textarea = found.get(0);
                    break;
                }
            } catch (Exception ignored) {}
        }
        
        if (textarea != null) {
            try {
                wait.until(ExpectedConditions.visibilityOf(textarea));
                textarea.clear();
                textarea.sendKeys(comment);
                // Also update the field reference for getAttribute calls
                orderCommentTextarea = textarea;
            } catch (Exception e) {
                // JS fallback
                ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].value = arguments[1];", textarea, comment);
            }
        }
        // If no textarea found, silently skip — comment is optional
    }

    public void clickPlaceOrder() {
        // Dismiss any full-screen ad overlays before clicking
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll(" +
                "'iframe[id^=\"aswift\"], div[id$=\"_host\"], #ad_position_box, " +
                ".adsbygoogle, ins.adsbygoogle'" +
                ").forEach(el => el.style.display='none');"
            );
            Thread.sleep(400);
        } catch (Exception ignored) {}
        // Use JS click to bypass any remaining overlay
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", placeOrderButton);
            Thread.sleep(300);
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", placeOrderButton);
        } catch (Exception e) {
            wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton));
            placeOrderButton.click();
        }
    }

    public String getDeliveryName() {
        wait.until(ExpectedConditions.visibilityOf(deliveryAddressName));
        return deliveryAddressName.getText();
    }

    public boolean isDeliveryAddressDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(deliveryAddressName));
            return deliveryAddressName.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickRegisterLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(registerLoginLink));
        registerLoginLink.click();
    }
}
