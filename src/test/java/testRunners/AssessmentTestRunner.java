package testRunners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import utilities.BaseTest;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/assessment.feature",
        glue = "stepDefinitions",
        dryRun = false,
        plugin = {
                "pretty",
                "html:target/cucumber-reports/test-ran-html-report.html",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "junit:target/cucumber-reports/CucumberTestReport.xml"
        },
        monochrome = true,
        tags="@Assessment"
)
public class AssessmentTestRunner extends BaseTest {

    @BeforeClass
    public static void setUpReport() {
        // Provide a unique report name for this runner
//        getExtent();
    }

    @AfterClass
    public static void tearDown() {
        flushReports(); // Call flush on the shared ExtentReports instance
    }
}