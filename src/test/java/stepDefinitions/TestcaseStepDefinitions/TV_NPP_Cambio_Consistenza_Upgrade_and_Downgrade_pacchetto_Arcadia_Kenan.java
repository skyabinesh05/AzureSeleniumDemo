package stepDefinitions.TestcaseStepDefinitions;

import coeqa.cucumberio.ScenarioContext;
import coeqa.cucumberio.TestContext;
import coeqa.datareader.ConfigReader;
import coeqa.pageobjects.ArcadiaHomePage;
import coeqa.pageobjects.CommonMethods;
import coeqa.testbase.TestBase;
import com.applitools.eyes.BatchInfo;
import cucumber.api.java.en.Then;
import utils.ExtentReport;

public class TV_NPP_Cambio_Consistenza_Upgrade_and_Downgrade_pacchetto_Arcadia_Kenan {
    static String commonTestName;
    static TestBase testBase;
    TestContext testContext;
    BatchInfo Batch_EndState;
    ArcadiaHomePage Arcadia_HomePage;
    ScenarioContext sContext;
    ExtentReport extentReport;
    String strbatchInfo;
    ConfigReader configReader = new ConfigReader();
    Common_StepDfn commonStepDfn = new Common_StepDfn();
    CommonMethods commonmethods;


    public TV_NPP_Cambio_Consistenza_Upgrade_and_Downgrade_pacchetto_Arcadia_Kenan() {

    }

    public TV_NPP_Cambio_Consistenza_Upgrade_and_Downgrade_pacchetto_Arcadia_Kenan(TestContext context) {
        this.testContext = context;

        commonmethods=testContext.getPageObjectManager().getCommonMethods();
        //coe
        Arcadia_HomePage= testContext.getPageObjectManager().getArcadia_HomePage();
        sContext = testContext.getScenarioContext();
        testBase = testContext.getPageObjectManager().getTestBase();
        extentReport = testContext.getExtentReport();
        strbatchInfo = commonStepDfn.getBatchInfo();
        Batch_EndState = new BatchInfo(strbatchInfo);
        Batch_EndState.setId(strbatchInfo + testBase.getDate());
        commonTestName = Common_StepDfn.getTestName()+ configReader.getEnv();
    }

    public String getTestData(String testdata) {
        return commonStepDfn.getTestData(testdata);
    }

    @Then("I select the Cambio Consistenza option from Scelta Attivita")
    public void i_select_the_Cambio_Consistenza_option_from_Scelta_Attivita() {
        Arcadia_HomePage.click_on_CambioOption();
    }

    @Then("I Verify and upgrading the package for NPP or Legacy")
    public void iVerifyAndUpgradingThePackageForNPPOrLegacy() {
        Arcadia_HomePage.Upgrading_Package_NPP_Legacy();
    }

    @Then("I Verify and click on Avanti on Carrello Migrazione TV for upgrading NPP")
    public void iVerifyAndClickOnAvantiOnCarrelloMigrazioneTVForUpgradingNPP() {
        Arcadia_HomePage.Avanti_on_Upgrading_CarrellaMigrazione();
    }

    @Then("I Verify and click on Avanti on Modifica Dati Account for upgrading NPP")
    public void iVerifyAndClickOnAvantiOnModificaDatiAccountForUpgradingNPP() {
        Arcadia_HomePage.Avanti_on_Modifica_Page_Upgrade();
    }

    @Then("I Verify and click on Avanti on Riepilogo Ordine for upgrading NPP")
    public void iVerifyAndClickOnAvantiOnRiepilogoOrdineForUpgradingNPP() {
        Arcadia_HomePage.Avanti_on_Riepligo_upgrade();
    }

    @Then("I Click on Cambio Consistenza for Upgrading Package")
    public void iClickOnCambioConsistenzaForUpgradingPackage() {
        Arcadia_HomePage.clickCambioConsistenza_btn();
    }
}
