package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

/**
 * BaseTest — Parent class for all test classes.
 * Handles:
 *  - WebDriver lifecycle (setup / teardown)
 *  - ExtentReports initialisation and finalisation
 *  - Screenshot capture on failure
 */
public class BaseTest {

    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static ExtentReports extent;
    protected static ExtentTest extentTest;

    public static final String BASE_URL       = "https://automationexercise.com";
    public static final String REPORTS_PATH   = "reports/ExtentReport.html";
    public static final String SCREENSHOT_DIR = "screenshots/";

    // ------------------------------------------------------------------ //
    //  Suite-level: initialise ExtentReports once for the entire run       //
    // ------------------------------------------------------------------ //
    @BeforeSuite
    public void setupExtentReports() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORTS_PATH);
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("AutomationExercise — UI Test Report");
        sparkReporter.config().setReportName("Task 2: AI-Assisted UI Test Execution");
        sparkReporter.config().setTimeStampFormat("dd-MMM-yyyy HH:mm:ss");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application",  "https://automationexercise.com");
        extent.setSystemInfo("Environment",  "QA");
        extent.setSystemInfo("Browser",      "Google Chrome");
        extent.setSystemInfo("Tester",       "AI-Generated via Claude");
        extent.setSystemInfo("Framework",    "Selenium WebDriver + TestNG");
    }

    // ------------------------------------------------------------------ //
    //  Method-level: launch browser before each test                       //
    // ------------------------------------------------------------------ //
    @BeforeMethod
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        // Block Google ads at the browser level
        options.addArguments("--disable-extensions");
        options.addArguments("--blink-settings=imagesEnabled=false");
        options.addArguments("--host-rules=MAP googleads.g.doubleclick.net 127.0.0.1, MAP pagead2.googlesyndication.com 127.0.0.1");
        // Uncomment below for headless execution:
        // options.addArguments("--headless=new");

        driver = new ChromeDriver(options);
        wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(BASE_URL);

        // Inject global ad dismissal script — runs on every page load
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "window._dismissAds = function() {" +
                "  document.querySelectorAll(" +
                "    'iframe[id^=\"aswift\"], div[id$=\"_host\"], " +
                "     #ad_position_box, .adsbygoogle, ins.adsbygoogle, " +
                "     #google_image_div, .google-auto-placed'" +
                "  ).forEach(el => el.style.display='none');" +
                "};" +
                "window._dismissAds();"
            );
        } catch (Exception ignored) {}
    }

    // ------------------------------------------------------------------ //
    //  Method-level: capture screenshot on failure, then quit browser      //
    // ------------------------------------------------------------------ //
    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = captureScreenshot(result.getName());
            if (extentTest != null && screenshotPath != null) {
                try {
                    extentTest.fail("Test FAILED — screenshot attached")
                              .addScreenCaptureFromPath(screenshotPath);
                } catch (Exception e) {
                    extentTest.fail("Test FAILED (screenshot error: " + e.getMessage() + ")");
                }
            }
            if (extentTest != null) {
                extentTest.log(Status.FAIL, result.getThrowable());
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            if (extentTest != null) extentTest.pass("Test PASSED");
        } else {
            if (extentTest != null) extentTest.skip("Test SKIPPED");
        }

        if (driver != null) {
            driver.quit();
        }
    }

    // ------------------------------------------------------------------ //
    //  Suite-level: flush / close the report                               //
    // ------------------------------------------------------------------ //
    @AfterSuite
    public void tearDownSuite() {
        if (extent != null) {
            extent.flush();
        }
    }

    // ------------------------------------------------------------------ //
    //  Helper: take screenshot and save to disk                            //
    // ------------------------------------------------------------------ //
    public String captureScreenshot(String testName) {
        try {
            new File(SCREENSHOT_DIR).mkdirs();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName  = SCREENSHOT_DIR + testName + "_" + timestamp + ".png";
            File   src       = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(fileName));
            return fileName;
        } catch (IOException e) {
            System.err.println("Screenshot failed: " + e.getMessage());
            return null;
        }
    }

    // ------------------------------------------------------------------ //
    //  Helper: scroll element into view                                     //
    // ------------------------------------------------------------------ //
    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // ------------------------------------------------------------------ //
    //  Helper: JS click (use when normal click is intercepted)              //
    // ------------------------------------------------------------------ //
    public void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
