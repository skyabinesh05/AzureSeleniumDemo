package stepDefinitions.TestcaseStepDefinitions;

import coeqa.cucumberio.ScenarioContext;
import coeqa.cucumberio.TestContext;
import coeqa.datareader.ConfigReader;
import coeqa.pageobjects.ArcadiaHomePage;
import coeqa.pageobjects.CommonMethods;
import coeqa.pageobjects.NewSalesPage_Offerta;
import coeqa.testbase.TestBase;
import com.applitools.eyes.BatchInfo;
import cucumber.api.java.en.Then;
import utils.ExtentReport;
import utils.PostgresDBI;

public class TV_20297_Migrazione_Legacy_NPP_StepDef {

    Common_StepDfn commonStepDfn = new Common_StepDfn();
    static String commonTestName;
    static TestBase testBase;
    TestContext testContext;
    BatchInfo Batch_EndState;
    NewSalesPage_Offerta NewSales_Page_Off;
    ScenarioContext sContext;
    ExtentReport extentReport;
    String strbatchInfo;
    ConfigReader configReader = new ConfigReader();
    CommonMethods commonmethods;
    PostgresDBI postgresDBI;
    ArcadiaHomePage Arcadia_HomePage;


    public TV_20297_Migrazione_Legacy_NPP_StepDef(TestContext context) {
        this.testContext = context;

        commonmethods=testContext.getPageObjectManager().getCommonMethods();
        //coe
        NewSales_Page_Off = testContext.getPageObjectManager().getNewSalesPage();
        Arcadia_HomePage = testContext.getPageObjectManager().getArcadia_HomePage();
        sContext = testContext.getScenarioContext();
        testBase = testContext.getPageObjectManager().getTestBase();
        extentReport = testContext.getExtentReport();
        strbatchInfo = commonStepDfn.getBatchInfo();
        Batch_EndState = new BatchInfo(strbatchInfo);
        Batch_EndState.setId(strbatchInfo + testBase.getDate());
        commonTestName = Common_StepDfn.getTestName()+ configReader.getEnv();
    }

    //Added by Abi
    public String getTestData(String testdata) {

        String value = commonStepDfn.getTestData(testdata);
        if (value == null) {
            System.out.println("Test data for key '" + testdata + "' is null. Please check the data source.");
            return ""; // Return an empty string or handle it as needed
        }
        return value;
    }

    @Then("I Click on LISTINO button")
    public void i_Click_on_LISTINO_button() {
        Arcadia_HomePage.Listino_btn();
    }

    @Then("I Verify and Click Avanti on Promo di Upgrade Tecnologico")
    public void i_Verify_and_Click_Avanti_on_Promo_di_Upgrade_Tecnologico() {
        Arcadia_HomePage.Avanti_On_PromoPage();

    }

    @Then("I Verfiy and click on Avanti on Esito Controlli Preliminari")
    public void i_Verfiy_and_click_on_Avanti_on_Esito_Controlli_Preliminari() {
        Arcadia_HomePage.Avivso_popup();
    }

    @Then("I Verify and click on Avanti on Carrello Migrazione TV")
    public void i_Verify_and_click_on_Avanti_on_Carrello_Migrazione_TV() {
        Arcadia_HomePage.Estione_Primli();
        Arcadia_HomePage.Avanti_on_Carrello_Migrazione_TV();
    }

    @Then("I Verify and click on Avanti on Modifica Dati Account")
    public void i_Verify_and_click_on_Avanti_on_Modifica_Dati_Account() {
        Arcadia_HomePage.Avanti_on_Modifica_Dati_Account();
    }

    @Then("I Verify and click on Avanti on Riepilogo Ordine")
    public void i_Verify_and_click_on_Avanti_on_Riepilogo_Ordine() {
        Arcadia_HomePage.Avanti_on_RiepilogoOrdine();
    }

    @Then("I Click on Register vocal option")
    public void i_Click_on_Register_vocal_option() {
        Arcadia_HomePage.Firma_Digitale();
    }


}
