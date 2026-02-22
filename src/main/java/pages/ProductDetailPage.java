package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * ProductDetailPage — Page Object for /product_details/{id}
 */
public class ProductDetailPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = ".product-information h2")
    public WebElement productName;

    @FindBy(css = ".product-information span span")
    public WebElement productPrice;

    @FindBy(css = ".product-information p:nth-of-type(1)")
    public WebElement productCategory;

    @FindBy(css = ".product-information p:nth-of-type(2)")
    public WebElement productAvailability;

    @FindBy(css = ".product-information p:nth-of-type(3)")
    public WebElement productCondition;

    @FindBy(css = ".product-information p:nth-of-type(4)")
    public WebElement productBrand;

    @FindBy(id = "quantity")
    public WebElement quantityInput;

    @FindBy(css = "button.cart")
    public WebElement addToCartButton;

    @FindBy(css = "#cartModal .modal-header h4")
    public WebElement cartModalHeader;

    @FindBy(css = "#cartModal a[href='/view_cart']")
    public WebElement viewCartButton;

    @FindBy(css = "#cartModal button[data-dismiss='modal']")
    public WebElement continueShoppingButton;

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void setQuantity(int qty) {
        wait.until(ExpectedConditions.visibilityOf(quantityInput));
        quantityInput.clear();
        quantityInput.sendKeys(String.valueOf(qty));
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        addToCartButton.click();
    }

    public boolean isCartModalDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(cartModalHeader));
            return cartModalHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickViewCart() {
        wait.until(ExpectedConditions.elementToBeClickable(viewCartButton));
        viewCartButton.click();
    }

    public void clickContinueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton));
        continueShoppingButton.click();
    }

    public String getProductName() {
        wait.until(ExpectedConditions.visibilityOf(productName));
        return productName.getText();
    }

    public String getProductPrice() {
        wait.until(ExpectedConditions.visibilityOf(productPrice));
        return productPrice.getText();
    }
}
