package stepDefinitions.TestcaseStepDefinitions;

import coeqa.cucumberio.ScenarioContext;
import coeqa.cucumberio.TestContext;
import coeqa.datareader.ConfigReader;
import coeqa.pageobjects.ArcadiaHomePage;
import coeqa.pageobjects.CommonMethods;
import coeqa.testbase.TestBase;
import com.applitools.eyes.BatchInfo;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;
import utils.C28CKI_DB;
import utils.ExtentReport;
import utils.PostgresDBI;

import java.io.IOException;
import java.sql.*;

public class TV_21647_Legacy_Downgrade_di_pacchetto_immediato_Kenan_StepDef {
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

    C28CKI_DB c28cki_db;

    public TV_21647_Legacy_Downgrade_di_pacchetto_immediato_Kenan_StepDef() {

    }

    public TV_21647_Legacy_Downgrade_di_pacchetto_immediato_Kenan_StepDef(TestContext context) {
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




    //    Pavithra - Downgrade

    @Then("I click on Crea Interazione tab and click on CREA button")
    public void i_click_on_Crea_Interazione_tab_and_click_on_CREA_button() {
        Arcadia_HomePage.creaInterazioneManuale();
    }


    @When("I enter Contract {string} and click CERCA")
    public void iEnterContractAndClickCERCA(String Number) {
        Arcadia_HomePage.SearchContract(getTestData(Number));
    }


    @Then("I click on the contract Code")
    public void i_click_on_the_contract_Code() {
        Arcadia_HomePage. clickContract();
    }


    @When("I click Cambio Consistenza button from Consistenza view tab")
    public void i_click_Cambio_Consistenza_button_from_Consistenza_view_tab() {
        Arcadia_HomePage.clickCambioConsistenza();
    }


    @Then("I click on a {string} to be downgraded under Gestione Consistenza")
    public void iClickOnAToBeDowngradedUnderGestioneConsistenza(String Package) {
        Arcadia_HomePage.selectPackage(getTestData(Package));
    }

    @Then("I validate selected package is displayed in the Pop up and click Si option")
    public void i_validate_selected_package_is_displayed_in_the_Pop_up_and_click_Si_option() {
        Arcadia_HomePage.clickSi();
    }

    @Then("I verify selected package is removed in Paccheto and click Avanti")
    public void i_verify_selected_package_is_removed_in_Paccheto_and_click_Avanti() {
        Arcadia_HomePage.clickPackageDowngradedAvanti();
    }

    @Then("I verify Modifica Dati Cliente is displayed and click Prosegui button")
    public void i_verify_Modifica_Dati_Cliente_is_displayed_and_click_Prosegui_button() {
        Arcadia_HomePage.clickProseguiInModificaDatiCliente();
    }


    @Then("I verify Riepilogo Ordine is displayed with summary of the updated {string} Order")
    public void iVerifyRiepilogoOrdineIsDisplayedWithSummaryOfTheUpdatedOrder(String Pack) {
        Arcadia_HomePage.clickProseguiInRiepilogoOrdine(getTestData(Pack));
    }

    @Then("I verify Esito Operazione and click conforma")
    public void i_verify_Esito_Operazione_and_click_Conforma() {
        Arcadia_HomePage.clickConforma();
    }

    @Then("I verify success message in Invio Ordine page and click Fine")
    public void i_verify_success_message_in_Invio_Ordine_page_and_click_Fine() {
        Arcadia_HomePage.successMessageInEsito();
    }


    @Then("verify postgres Status for contract {string}")
    public void verifyPostgresStatusForContractForDowngrade(String Number) {
       Arcadia_HomePage.verifyPostgresResults(getTestData(Number));
    }


    @Then("I should verify package is downgraded in arcadia home page")
    public void iShouldVerifyPackageIsDowngradedInArcadiaHomePage() {
        Assert.assertTrue(Arcadia_HomePage.verifyPackageDowngraded());
    }

    @Then("verify kenan status for contract {string} for downgrade")
    public void verifyKenanStatusForContractForDowngrade(String Number) {
        Arcadia_HomePage.verifyKenanResults(getTestData(Number));
    }

}
