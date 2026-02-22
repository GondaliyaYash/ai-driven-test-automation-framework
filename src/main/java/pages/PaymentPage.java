package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * PaymentPage — Page Object for /payment
 */
public class PaymentPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "input[data-qa='name-on-card']")
    public WebElement nameOnCardInput;

    @FindBy(css = "input[data-qa='card-number']")
    public WebElement cardNumberInput;

    @FindBy(css = "input[data-qa='cvc']")
    public WebElement cvcInput;

    @FindBy(css = "input[data-qa='expiry-month']")
    public WebElement expiryMonthInput;

    @FindBy(css = "input[data-qa='expiry-year']")
    public WebElement expiryYearInput;

    @FindBy(css = "button[data-qa='pay-button']")
    public WebElement payAndConfirmButton;

    // ---- Success / Confirmation ----
    @FindBy(css = "h2[data-qa='order-placed'], .order-placed")
    public WebElement orderPlacedHeading;

    @FindBy(css = "#success_message .alert-success, .alert-success b")
    public WebElement successAlert;

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void enterPaymentDetails(String name, String cardNo, String cvc,
                                    String expMonth, String expYear) {
        wait.until(ExpectedConditions.visibilityOf(nameOnCardInput));
        nameOnCardInput.clear();
        nameOnCardInput.sendKeys(name);

        cardNumberInput.clear();
        cardNumberInput.sendKeys(cardNo);

        cvcInput.clear();
        cvcInput.sendKeys(cvc);

        expiryMonthInput.clear();
        expiryMonthInput.sendKeys(expMonth);

        expiryYearInput.clear();
        expiryYearInput.sendKeys(expYear);
    }

    public void clickPayAndConfirm() {
        // Dismiss any ad iframes blocking the button
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(
                    "document.querySelectorAll('iframe[id^=\"aswift\"]').forEach(f => f.style.display='none');"
                );
            Thread.sleep(300);
        } catch (Exception ignored) {}
        // Use JS click to bypass ad interception
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", payAndConfirmButton);
            Thread.sleep(300);
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", payAndConfirmButton);
        } catch (Exception e) {
            wait.until(ExpectedConditions.elementToBeClickable(payAndConfirmButton));
            payAndConfirmButton.click();
        }
    }

    public boolean isOrderPlaced() {
        try {
            wait.until(ExpectedConditions.visibilityOf(orderPlacedHeading));
            return orderPlacedHeading.isDisplayed();
        } catch (Exception e) {
            try {
                wait.until(ExpectedConditions.visibilityOf(successAlert));
                return successAlert.isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }

    public String getOrderConfirmationText() {
        try {
            wait.until(ExpectedConditions.visibilityOf(orderPlacedHeading));
            return orderPlacedHeading.getText();
        } catch (Exception e) {
            return "";
        }
    }
}
