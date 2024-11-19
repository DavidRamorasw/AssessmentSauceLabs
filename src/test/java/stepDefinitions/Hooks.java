package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import utilities.BaseTest;

import java.io.IOException;

public class Hooks extends BaseTest {

    @Before
    public void setUpScenario(Scenario scenario) throws IOException, InterruptedException {
        // Initialize directories and ExtentReports
        initializeDirectories();
        getExtent("testing-report.html");

        // Start the test for the scenario
        test = getExtent("testing-report.html").createTest(scenario.getName());

        // Initialize WebDriver
        initializeWebDriver();

        // Load locators if necessary
        loadLocators();


    }

    @BeforeStep
    public void beforeEachStep(Scenario scenario) throws IOException, InterruptedException {
        // Log the step before execution
        test.info("Executing step: " + scenario.getName());
        captureInitialScreenshot();


    }



    @After
    public void tearDownScenario(Scenario scenario) throws IOException, InterruptedException {
        String screenshotPath = getScreenshot(scenario.getName());
        captureInitialScreenshot();
        // Embed final screenshot and mark test result
        if (scenario.isFailed()) {
            embedScreenshotInReport("failed", screenshotPath);
        } else if (scenario.getStatus().name().equalsIgnoreCase("PASSED")) {
            embedScreenshotInReport("passed", screenshotPath);
        } else if (scenario.getStatus().name().equalsIgnoreCase("SKIPPED")) {
            embedScreenshotInReport("skipped", screenshotPath);
        }

        // Close WebDriver
        closeBrowser();

        // Flush ExtentReports
        flushReports();
    }
}