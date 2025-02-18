package TestRunner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import utils.GenerateReport;

@CucumberOptions(
        features = "src/test/resources/FunctionalTests/CoreTV/TV_43361_Migrazione_Legacy_NPP_with_Pacchetto.feature"
        , glue = {"stepDefinitions.excelDataProvider"}  //byy default cucumber checks code inside src/test/java folder  /Featuredpagecontentvalidation.feature
        , tags = {"@ITRegressionTest"},
        plugin = {
                "pretty", "json:target/cucumber-reports/Cucumber.json",
                "junit:target/cucumber-reports/Cucumber.xml",
                "html:target/cucumber-reports"},
        /*"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"},*/
        monochrome = true
)

public class TestRunner extends AbstractTestNGCucumberTests {


    @DataProvider(parallel = false)
    @Override
    public Object[][] scenarios() {
        return super.scenarios();
    }

// @AfterSuite
// public void tearDown(){
//
//     GenerateReport.GenerateJVMReport();
// }

}