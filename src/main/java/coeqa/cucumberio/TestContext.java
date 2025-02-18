package coeqa.cucumberio;

import coeqa.managers.DriverManager;
import coeqa.managers.PageObjectManager;
import org.apache.log4j.xml.DOMConfigurator;
import utils.ExtentReport;

public class TestContext {
    static {
        DOMConfigurator.configure(System.getProperty("user.dir") + "//configs/log4j.xml");
    }

    DriverManager webDriverManager;
    PageObjectManager pageobjectmanager;
    ScenarioContext scenarioConText;
    ExtentReport extentReport;

    public TestContext() {

    //    RunTimePropFile.createRTP();
        webDriverManager = new DriverManager();
        pageobjectmanager = new PageObjectManager(webDriverManager.getWebDriver());
        scenarioConText = new ScenarioContext();
        extentReport = new ExtentReport();
    }


    public DriverManager getWebDriverManager() {
        return webDriverManager;
    }

    public PageObjectManager getPageObjectManager() {
        return pageobjectmanager;
    }

    public ScenarioContext getScenarioContext() {
        return scenarioConText;
    }

    public ExtentReport getExtentReport() {
        return extentReport;
    }


}
