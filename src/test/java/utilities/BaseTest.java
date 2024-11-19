package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter; // Replaced ExtentSparkReporter with ExtentHtmlReporter
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public static WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentTest test;
    public static FileReader configFile;
    public static FileReader locatorsFile;
    public static Properties prop = new Properties();
    public static Properties locators = new Properties();
    public static String testUrl;


    public static String reportDirectoryPath;
    public static String screenshotDirectoryPath;

    // Initialize ExtentReports and set the report path
    public static ExtentReports getExtent(String reportName) {
        if (extent == null) {
            try {
                createDirectory(reportDirectoryPath);

                String reportPath = reportDirectoryPath + "/" + reportName;
                ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath);
                htmlReporter.config().setDocumentTitle("Automation Test Report");
                htmlReporter.config().setReportName("Current Sprint (PI 23.4)");
                htmlReporter.config().setTheme(Theme.STANDARD);

                extent = new ExtentReports();
                extent.attachReporter(htmlReporter);
                extent.setSystemInfo("Host Name", "Localhost");
                extent.setSystemInfo("Product", "HOLLARD");

                System.out.println("ExtentReports initialized with report: " + reportPath);
            } catch (Exception e) {
                System.err.println("Failed to initialize ExtentReports: " + e.getMessage());
            }
        }
        return extent;
    }

    // Initialize the directories required for reports and screenshots
    public static void initializeDirectories() {
        if (reportDirectoryPath == null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date());
            reportDirectoryPath = System.getProperty("user.dir") + "/Automation Test Execution Reports/HOLLARD_" + timestamp;
            screenshotDirectoryPath = reportDirectoryPath + "/screenshots";

            createDirectory(reportDirectoryPath);
            createDirectory(screenshotDirectoryPath);
        }
    }

    // Method to create a directory if it doesn't exist
    private static void createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs();
            System.out.println(isCreated ? "Directory created: " + directoryPath
                    : "Failed to create directory: " + directoryPath);
        } else {
            System.out.println("Directory already exists: " + directoryPath);
        }
    }

    // Initialize WebDriver based on config.properties
    public static void initializeWebDriver() throws IOException {
        configFile = new FileReader(System.getProperty("user.dir") + "/src/test/resources/configfiles/config.properties");
        prop.load(configFile);
        configFile.close();

        String env = prop.getProperty("env");
        if (env != null) {
            FileReader envConfigFile = new FileReader(System.getProperty("user.dir") + "/src/test/resources/configfiles/config-" + env + ".properties");
            prop.load(envConfigFile);
            envConfigFile.close();
        }

        String browser = prop.getProperty("browser");
        String projectRoot = System.getProperty("user.dir");

        try {
            if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            } else if (browser.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            } else if (browser.equalsIgnoreCase("edge")) {
                System.setProperty("webdriver.edge.driver", projectRoot + "/drivers/msedgedriver.exe");
                driver = new EdgeDriver();
            } else if (browser.equalsIgnoreCase("safari")) {
                driver = new SafariDriver();
            }

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            System.out.println("Browser initialized successfully: " + browser);

        } catch (Exception e) {
            System.err.println("Failed to initialize WebDriver: " + e.getMessage());
            throw e;
        }

        testUrl = System.getProperty("testUrl");
        if (testUrl == null || testUrl.isEmpty()) {
            testUrl = prop.getProperty("testUrl");
        }
        if (testUrl != null && !testUrl.isEmpty()) {
            try {
                driver.get(testUrl);
                test.info("Navigated to URL: " + testUrl);
                System.out.println("Navigated to URL: " + testUrl);
            } catch (Exception e) {
                System.err.println("Failed to load URL: " + testUrl);
                test.fail("Failed to load URL: " + testUrl);
            }
        } else {
            System.err.println("Test URL is not defined in the configuration file.");
            test.fail("Test URL not defined in the configuration file.");
        }
    }

    public static String getScreenshot(String screenshotName) throws IOException {
        if (driver == null) {
            throw new IllegalStateException("Driver is not initialized.");
        }

        String dateName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String destination = screenshotDirectoryPath + "/" + screenshotName + dateName + ".png";

        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File finalDestination = new File(destination);
            FileUtils.copyFile(source, finalDestination);

            System.out.println("Screenshot saved at: " + finalDestination.getAbsolutePath());
            return "screenshots/" + screenshotName + dateName + ".png";
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

    // Capture initial screenshot after browser launch
    public static void captureInitialScreenshot() throws IOException, InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        String screenshotPath = getScreenshot("Browser_Launched");
        if (screenshotPath != null) {
            test.info("", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else {
            test.fail("Failed to capture initial screenshot.");
        }
    }

    // Embed screenshot in the Extent Report
    public static void embedScreenshotInReport(String status, String screenshotPath) throws IOException {
        if (screenshotPath != null) {
            switch (status) {
                case "failed":
                    test.fail("Test Case Failed",
                            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                    break;
                case "passed":
                    test.pass("Test Case Passed",
                            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                    break;
                case "skipped":
                    test.skip("Test Case Skipped",
                            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                    break;
                default:
                    test.info("Unknown test status.");
                    break;
            }
        } else {
            test.fail("No screenshot captured to embed.");
        }
    }

    // Flush ExtentReports after test suite completes
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
            System.out.println("ExtentReports flushed successfully.");
        }
    }

    // Load locators from locators.properties
    public static void loadLocators() throws IOException {
        locatorsFile = new FileReader(System.getProperty("user.dir") + "/src/test/resources/locators/locators.properties");
        locators.load(locatorsFile);
        locatorsFile.close();
        System.out.println("Loaded locators: " + locators);
    }


    // Get locator from locators file
    public static String getLocator(String key) {
        String locator = locators.getProperty(key);
        if (locator == null || locator.isEmpty()) {
            throw new IllegalArgumentException("Locator for key '" + key + "' not found in locators file.");
        }
        System.out.println("Locator retrieved: Key=" + key + ", Value=" + locator);
        return locator;
    }


    // Generate random string for unique email
    public static String generateRandomEmailAddress() {
        return RandomStringUtils.randomAlphabetic(5) + "@test.com";
    }

    // Close browser after test completion
    public static void closeBrowser() {
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("Browser closed successfully.");
            } catch (Exception e) {
                System.err.println("Failed to close browser: " + e.getMessage());
            }
        } else {
            System.out.println("Driver was null, no browser to close.");
        }
    }
}
