package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

/**
 * HomePage — Page Object for https://automationexercise.com/
 */
public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ---- Navigation Links ----
    @FindBy(css = "a[href='/login']")
    public WebElement signupLoginLink;

    @FindBy(css = "a[href='/products']")
    public WebElement productsLink;

    @FindBy(css = "a[href='/view_cart']")
    public WebElement cartLink;

    // ---- Logo / Brand ----
    @FindBy(css = "img[alt='Website for automation practice']")
    public WebElement logo;

    // ---- Slider / Hero ----
    @FindBy(css = "#slider")
    public WebElement heroSlider;

    // ---- Feature Items ----
    @FindBy(css = ".features_items")
    public WebElement featuresSection;

    // ---- Subscription (footer) ----
    @FindBy(id = "susbscribe_email")
    public WebElement subscriptionEmailInput;

    @FindBy(id = "subscribe")
    public WebElement subscribeButton;

    @FindBy(css = ".alert-success")
    public WebElement subscriptionSuccessAlert;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void clickSignupLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(signupLoginLink));
        signupLoginLink.click();
    }

    public void clickProducts() {
        // Dismiss any ad iframes first
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(
                    "document.querySelectorAll('iframe[id^=\"aswift\"]').forEach(f => f.style.display='none');"
                );
        } catch (Exception ignored) {}
        // Use JS click to avoid ad interception
        try {
            wait.until(ExpectedConditions.elementToBeClickable(productsLink));
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", productsLink);
        } catch (Exception e) {
            // Fallback: direct navigation
            driver.get("https://automationexercise.com/products");
        }
    }

    public void clickCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink));
        cartLink.click();
    }

    public boolean isLogoDisplayed() {
        return logo.isDisplayed();
    }

    public void subscribeWithEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(subscriptionEmailInput));
        subscriptionEmailInput.sendKeys(email);
        subscribeButton.click();
    }

    public boolean isSubscriptionSuccessful() {
        try {
            wait.until(ExpectedConditions.visibilityOf(subscriptionSuccessAlert));
            return subscriptionSuccessAlert.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
