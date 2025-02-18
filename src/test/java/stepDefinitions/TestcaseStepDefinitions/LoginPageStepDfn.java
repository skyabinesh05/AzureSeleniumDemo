package stepDefinitions.TestcaseStepDefinitions;

import com.applitools.eyes.BatchInfo;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import coeqa.cucumberio.ScenarioContext;
import coeqa.cucumberio.TestContext;
import coeqa.datareader.ConfigReader;
import coeqa.datareader.GetdatafromExcel;
import coeqa.pageobjects.CommonMethods;
import coeqa.pageobjects.LoginPage;

import coeqa.testbase.TestBase;
import org.apache.log4j.Logger;
import utils.ExtentReport;

import java.awt.*;

public class LoginPageStepDfn {
    public static final Logger Log = Logger.getLogger(LoginPageStepDfn.class.getName());
    static String commonTestName;

    static String sheetName;
    static String workBookName;
    static TestBase testBase;
    TestContext testContext;
    BatchInfo Batch_EndState;
    String appName = "";

    LoginPage loginPage;
    GetdatafromExcel setdataobj;
    Scenario scenario;
    ScenarioContext sContext;
    ExtentReport extentReport;
    String strbatchInfo;
    String UrlFromExcel;
    static String appliToolLink="applitoollink";
    ConfigReader configReader = new ConfigReader();
    Common_StepDfn commonStepDfn = new Common_StepDfn();
    //ExcelActions excelActions = new ExcelActions();
    CommonMethods commonmethods;
    String urlcheck;
    String featureName = "";

    public LoginPageStepDfn() {

    }

    public LoginPageStepDfn(TestContext context) {
        this.testContext = context;

        commonmethods=testContext.getPageObjectManager().getCommonMethods();
        //coe
        loginPage=testContext.getPageObjectManager().getLoginPage();
        sContext = testContext.getScenarioContext();
        testBase = testContext.getPageObjectManager().getTestBase();
        extentReport = testContext.getExtentReport();
//		strbatchInfo = "Smoke_BAE_EndState";
        strbatchInfo = commonStepDfn.getBatchInfo();
        Batch_EndState = new BatchInfo(strbatchInfo);
        Batch_EndState.setId(strbatchInfo + testBase.getDate());
        commonTestName = Common_StepDfn.getTestName()+ configReader.getEnv();
    }
    public String getTestData(String testdata) {
        return commonStepDfn.getTestData(testdata);
    }


    @And("Enter valid username {string} and Password {string}")
    public void enterValidUsernameAndPassword(String username, String password) {
        loginPage.enterLoginCredential(getTestData(username),getTestData(password));
    }

    @Then("User click on the submit button")
    public void userClickOnTheSubmitButton() {
        loginPage.submitButton();
    }

    @Then("user should click on New CRM tab in SKY home screen")
    public void userShouldClickOnNewCRMTabInSKYHomeScreen() throws InterruptedException {
      //  Thread.sleep(10000);
        loginPage.newCRM();
        Thread.sleep(5000);
    }

    @Then("User focus land on the new page")
    public void userFocusLandOnTheNewPage() {
       loginPage.newTab();
    }

    @Then("User click on the {string} button")
    public void user_click_on_the_button(String ENVIRONMENT) {
//       loginPage.click_Pdd_btn(ENVIRONMENT);
        if (ENVIRONMENT.equalsIgnoreCase("ST") || ENVIRONMENT.equalsIgnoreCase("IT")) {
            loginPage.click_Pdd_btn(ENVIRONMENT);
        } else {
            throw new IllegalArgumentException("Invalid environment: " + ENVIRONMENT);
        }
    }

}
