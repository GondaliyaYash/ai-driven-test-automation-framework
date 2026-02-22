package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * SignupPage — Page Object for /signup (account registration form)
 */
public class SignupPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ---- Account Information ----
    @FindBy(id = "id_gender1")
    public WebElement mrRadio;

    @FindBy(id = "id_gender2")
    public WebElement mrsRadio;

    @FindBy(id = "name")
    public WebElement nameInput;

    @FindBy(id = "email")
    public WebElement emailInput;

    @FindBy(id = "password")
    public WebElement passwordInput;

    @FindBy(id = "days")
    public WebElement dobDaySelect;

    @FindBy(id = "months")
    public WebElement dobMonthSelect;

    @FindBy(id = "years")
    public WebElement dobYearSelect;

    @FindBy(id = "newsletter")
    public WebElement newsletterCheckbox;

    @FindBy(id = "optin")
    public WebElement specialOffersCheckbox;

    // ---- Address Information ----
    @FindBy(id = "first_name")
    public WebElement firstNameInput;

    @FindBy(id = "last_name")
    public WebElement lastNameInput;

    @FindBy(id = "company")
    public WebElement companyInput;

    @FindBy(id = "address1")
    public WebElement address1Input;

    @FindBy(id = "address2")
    public WebElement address2Input;

    @FindBy(id = "country")
    public WebElement countrySelect;

    @FindBy(id = "state")
    public WebElement stateInput;

    @FindBy(id = "city")
    public WebElement cityInput;

    @FindBy(id = "zipcode")
    public WebElement zipcodeInput;

    @FindBy(id = "mobile_number")
    public WebElement mobileInput;

    // ---- Submit ----
    @FindBy(css = "button[data-qa='create-account']")
    public WebElement createAccountButton;

    // ---- Success ----
    @FindBy(css = "h2[data-qa='account-created']")
    public WebElement accountCreatedHeading;

    @FindBy(css = "a[data-qa='continue-button']")
    public WebElement continueButton;

    public SignupPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void fillAccountDetails(String name, String password,
                                   String day, String month, String year) {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        mrRadio.click();
        nameInput.clear();
        nameInput.sendKeys(name);
        passwordInput.sendKeys(password);
        new Select(dobDaySelect).selectByVisibleText(day);
        new Select(dobMonthSelect).selectByVisibleText(month);
        new Select(dobYearSelect).selectByVisibleText(year);
    }

    public void fillAddressDetails(String firstName, String lastName, String address,
                                   String country, String state, String city,
                                   String zip, String mobile) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        address1Input.sendKeys(address);
        new Select(countrySelect).selectByVisibleText(country);
        stateInput.sendKeys(state);
        cityInput.sendKeys(city);
        zipcodeInput.sendKeys(zip);
        mobileInput.sendKeys(mobile);
    }

    public void clickCreateAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(createAccountButton));
        createAccountButton.click();
    }

    public boolean isAccountCreated() {
        try {
            wait.until(ExpectedConditions.visibilityOf(accountCreatedHeading));
            return accountCreatedHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        continueButton.click();
    }
}
