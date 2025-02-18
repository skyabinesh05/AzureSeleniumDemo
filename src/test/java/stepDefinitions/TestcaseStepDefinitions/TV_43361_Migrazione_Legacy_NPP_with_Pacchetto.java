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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TV_43361_Migrazione_Legacy_NPP_with_Pacchetto {
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


    public TV_43361_Migrazione_Legacy_NPP_with_Pacchetto(TestContext context) {
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

    @Then("I Click on LISTINO button for Upgrading Pachato")
    public void iClickOnLISTINOButtonForUpgradingPachato() {
        Arcadia_HomePage.Listino_btn();
    }

    @Then("I Verify and Click Avanti on Promo di Upgrade Tecnologico for upgrading pachato")
    public void iVerifyAndClickAvantiOnPromoDiUpgradeTecnologicoForUpgradingPachato() {
        Arcadia_HomePage.Avanti_On_PromoPage();
    }

    @Then("I Verfiy and click on Avanti on Esito Controlli Preliminari for upgrading pachato")
    public void iVerfiyAndClickOnAvantiOnEsitoControlliPreliminariForUpgradingPachato() {
        Arcadia_HomePage.Avivso_popup();
        Arcadia_HomePage.Estione_Primli();
    }

    @Then("I Verify and upgrading the package")
    public void iVerifyAndUpgradingThePackage() {
        Arcadia_HomePage.Upgrading_Promos();
//        Arcadia_HomePage.selectPackage();
    }


    @Then("I Verify and click on Avanti on Carrello Migrazione TV for upgrading pachato")
    public void iVerifyAndClickOnAvantiOnCarrelloMigrazioneTVForUpgradingPachato() {
        Arcadia_HomePage.Avanti_on_Carrello_Migrazione_TV();
    }

    @Then("I Verify and click on Avanti on Modifica Dati Account for upgrading pachato")
    public void iVerifyAndClickOnAvantiOnModificaDatiAccountForUpgradingPachato() {
        Arcadia_HomePage.Avanti_on_Modifica_Dati_Account();
    }

    @Then("I Verify and click on Avanti on Riepilogo Ordine for upgrading pachato")
    public void iVerifyAndClickOnAvantiOnRiepilogoOrdineForUpgradingPachato() {
        Arcadia_HomePage.Avanti_on_RiepilogoOrdine();
    }


    @Then("I Click on Register vocal option for upgrading pachato")
    public void iClickOnRegisterVocalOptionForUpgradingPachato() {
        Arcadia_HomePage.Firma_Digitale();
    }


    @Then("Query to check the postgres Status for {string}")
    public void queryToCheckThePostgresStatusFor(String CodiceContratto) {
        try (Connection connection = postgresDBI.getConnection()) {
            Statement statement = connection.createStatement();
            String query = "SELECT * from omcustom.T_ORDER a where a.order_num = '"+CodiceContratto+"' order by a.creation_date desc";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                // Process the result set
                System.out.println("order_id\t--\t" + resultSet.getString("order_id"));
                System.out.println("order_ref\t--\t" + resultSet.getString("order_ref"));
                System.out.println("order_num\t--\t" + resultSet.getString("order_num"));
                System.out.println("status\t--\t" + resultSet.getString("status"));
                System.out.println("sub_status\t--\t" + resultSet.getString("sub_status"));
                System.out.println("creation_date\t--\t" + resultSet.getString("creation_date"));
                System.out.println("processing_date\t--\t" + resultSet.getString("processing_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
