package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * LoginPage — Page Object for /login (Login + Signup combined page)
 */
public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ---- Login Section ----
    @FindBy(css = "input[data-qa='login-email']")
    public WebElement loginEmailInput;

    @FindBy(css = "input[data-qa='login-password']")
    public WebElement loginPasswordInput;

    @FindBy(css = "button[data-qa='login-button']")
    public WebElement loginButton;

    @FindBy(css = ".login-form p[style='color: red;']")
    public WebElement loginErrorMessage;

    // ---- Signup Section ----
    @FindBy(css = "input[data-qa='signup-name']")
    public WebElement signupNameInput;

    @FindBy(css = "input[data-qa='signup-email']")
    public WebElement signupEmailInput;

    @FindBy(css = "button[data-qa='signup-button']")
    public WebElement signupButton;

    @FindBy(css = ".signup-form p[style*='color: red'], .signup-form p[style*='color:red']")
    public WebElement signupErrorMessage;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void loginWith(String email, String password) {
        wait.until(ExpectedConditions.visibilityOf(loginEmailInput));
        loginEmailInput.clear();
        loginEmailInput.sendKeys(email);
        loginPasswordInput.clear();
        loginPasswordInput.sendKeys(password);
        loginButton.click();
    }

    public void fillSignupDetails(String name, String email) {
        wait.until(ExpectedConditions.visibilityOf(signupNameInput));
        signupNameInput.clear();
        signupNameInput.sendKeys(name);
        signupEmailInput.clear();
        signupEmailInput.sendKeys(email);
        signupButton.click();
    }

    public boolean isLoginErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(loginErrorMessage));
            return loginErrorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSignupErrorDisplayed() {
        try {
            // Try multiple ways to detect the duplicate email error
            java.util.List<org.openqa.selenium.WebElement> errors = driver.findElements(
                org.openqa.selenium.By.cssSelector(
                    ".signup-form p[style*='color'], .signup-form .alert, .signup-form p")
            );
            for (org.openqa.selenium.WebElement el : errors) {
                try {
                    String text = el.getText().toLowerCase();
                    String style = el.getAttribute("style");
                    if ((style != null && style.contains("red")) ||
                        text.contains("already") || text.contains("exist") || text.contains("registered")) {
                        return true;
                    }
                } catch (Exception ignored) {}
            }
            // Fallback: wait for the original element
            wait.until(ExpectedConditions.visibilityOf(signupErrorMessage));
            return signupErrorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
