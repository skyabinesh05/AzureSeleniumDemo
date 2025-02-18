package stepDefinitions.TestcaseStepDefinitions;

import com.applitools.eyes.BatchInfo;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import coeqa.cucumberio.ScenarioContext;
import coeqa.cucumberio.TestContext;
import coeqa.datareader.ConfigReader;
import coeqa.datareader.GetdatafromExcel;
import coeqa.pageobjects.CommonMethods;
import coeqa.testbase.TestBase;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import utils.ExtentReport;

import java.io.FileWriter;
import java.io.IOException;

public class Common_StepDfn {

    public static final Logger Log = Logger.getLogger(Common_StepDfn.class.getName());
    static String commonTestName;

    static String sheetName;
    static String workBookName;
    static TestBase testBase;
    TestContext testContext;
    BatchInfo Smoke_BAE_EndState;
    String appName = "New Sales";

    CommonMethods commonMethods;
    GetdatafromExcel setdataobj;
    Scenario scenario;
    ScenarioContext sContext;
    ExtentReport extentReport;
    static String strbatchInfo;
    String UrlFromExcel;
    String appliToolLink;
    ConfigReader configReader = new ConfigReader();

    String featureName = "";
    static String channelName;
    String executionURL;
    BatchInfo Batch_EndState;
    static boolean alreadyExecuted;
    static String uniqueID ="";
    static String envName;
    public Common_StepDfn() {

    }

    public Common_StepDfn(TestContext context) {
        this.testContext = context;

        commonMethods=testContext.getPageObjectManager().getCommonMethods();
        sContext = testContext.getScenarioContext();
        testBase = testContext.getPageObjectManager().getTestBase();
        extentReport = testContext.getExtentReport();
        setdataobj = new GetdatafromExcel();
        envName = ConfigReader.getEnvName().toUpperCase();
        if(!alreadyExecuted) {
            uniqueID=commonMethods.getTimestamp();
            alreadyExecuted = true;
        }
        strbatchInfo ="SMB_"+envName+configReader.getRuntimePropValue("SUITE_NAME")+"_"+uniqueID;
        Batch_EndState = new BatchInfo(strbatchInfo);
        Batch_EndState.setId("SMB"+envName+strbatchInfo +uniqueID );
    }

    @Before
    public void before(Scenario scenario) throws IOException {
        DOMConfigurator.configure(System.getProperty("user.dir") + "//configs/log4j.xml");
        String scenarioName = scenario.getName().replaceAll(" ", "_");

        Log.info("Scenario.getUri() ->" + scenario.getUri());
        featureName = scenario.getId().split(".feature")[0];
        featureName = featureName.substring(featureName.lastIndexOf("/") + 1);
        this.scenario = scenario;
        sheetName = configReader.getRuntimePropValue("ENVIRONMENT");
        workBookName = featureName;
        ExtentReport.createExtentTest(scenario.getName());

        Log.info("scenarioName from hooks :" + scenarioName);
        Log.info("featureName from hooks :" + featureName);
    }

    public String getTestData(String testdata) {

        return GetdatafromExcel.fetchdata(workBookName, sheetName, commonTestName, testdata);
    }

//coe
    @Given("User Launch the Url {string} for {string}")
    public void user_Launch_the_Url_for(String url, String testname) throws IOException {
      System.out.println("=====================");
        commonTestName = testname.trim();
        int index = testname.indexOf("_");
        channelName  = testname.substring(0, index).trim();
        System.out.println("firstString = "+channelName);
        Log.info("sheetName:" + sheetName);
        Log.info("WBName:" + workBookName);
        ExtentReport.createExtentTest(commonTestName);

        executionURL = configReader.getURLForTestExecution(channelName);
        if (executionURL.isEmpty() || featureName.contains("SMB_QSP")) {
            UrlFromExcel = getTestData(url);
            System.out.println("GETTING URL DATA FROM EXCEL " + UrlFromExcel);
            testBase.getUrl(UrlFromExcel);
        } else {
            System.out.println("GETTING URL DATA FROM ENVIRONMENT XML " + executionURL);
            testBase.getUrl(executionURL);

        }
    }



    @After
    public void afterSteps() throws IOException {
        Log.info("after method");
        String browserName = configReader.getBrowerName().toUpperCase();
        String postJsonData = "[{\"browserName\": \"" + browserName + "\", \"appliToolsURL\": \"" + appliToolLink + "\"}]";
        FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + "\\executionDetails.json");
        fileWriter.write(postJsonData);
        fileWriter.close();

        extentReport.closeExtent();
        //testcontext.getWebDriverManager().deleteCookie();
        testContext.getWebDriverManager().closeDriver();
    }

    public static String getTestName() {
        return commonTestName;
    }
    public String getBatchInfo() { return strbatchInfo; }

    public String getChannelName() {
        return channelName;
    }
    public String getWorkBookName() {return workBookName;}
    public String getSheetName() {
        return sheetName;
    }




}
