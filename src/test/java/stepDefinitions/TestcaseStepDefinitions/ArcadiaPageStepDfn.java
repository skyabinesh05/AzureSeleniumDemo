package stepDefinitions.TestcaseStepDefinitions;

import coeqa.cucumberio.ScenarioContext;
import coeqa.cucumberio.TestContext;
import coeqa.datareader.ConfigReader;
import coeqa.pageobjects.ArcadiaHomePage;
import coeqa.pageobjects.CommonMethods;
import coeqa.testbase.TestBase;
import com.applitools.eyes.BatchInfo;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import utils.ExtentReport;
import utils.PostgresDBI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArcadiaPageStepDfn {
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
    PostgresDBI postgresDBI;

    public ArcadiaPageStepDfn() {

    }

    public ArcadiaPageStepDfn(TestContext context) {
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

    @Then("User click on the Menu button and  select New Sale Option")
    public void user_click_on_the_Menu_button_and_select_New_Sale_Option() {
        Arcadia_HomePage.Click_Menu_btn_and_Select_NewSales();
    }

    @Then("User click on New CRM tab in Arcadia Home Screen")
    public void user_click_on_New_CRM_tab_in_Arcadia_Home_Screen() {
        Arcadia_HomePage.Click_NewCRM_tab();
    }

    @When("User click on Crea Interazione Manuale button")
    public void user_click_on_Crea_Interazione_Manuale_button() {
        Arcadia_HomePage.Click_Crea_Interazione_Manuale_btn();
    }

    @Then("User click on CREA button")
    public void user_click_on_CREA_button() throws InterruptedException {
        Arcadia_HomePage.Click_CREA_btn();
    }


    @Then("User enter Contract Number {string} in Codice Contratto Field")
    public void userEnterContractNumberInCodiceContrattoField(String Contract) throws InterruptedException {
        Arcadia_HomePage.Enter_Contract_Number(getTestData(Contract));
    }

    @Then("User click on CERCA button")
    public void user_click_on_CERCA_button() {
        Arcadia_HomePage.Click_CERCA_btn();
    }

    @Then("User click on Contract Number hyperlink")
    public void user_click_on_Contract_Number_hyperlink() {
        Arcadia_HomePage.Click_Contract_Hyperlink();
    }

    @Then("User click on Gestione BB and select Gestione light retention option")
    public void user_click_on_Gestione_BB_and_select_Gestione_light_retention_option() throws InterruptedException {
        Arcadia_HomePage.Click_GestioneBB_LR();
    }

    @Then("User click on sky wifi BB and respective options for Light retention in Omniscript")
    public void uuser_click_on_sky_wifi_BB_and_respective_options_for_Light_retention_in_Omniscript() throws InterruptedException {
        Arcadia_HomePage.LR_Omniscript_function();
    }

    @Then("I click on Balance Fatture Pagamento")
    public void i_click_on_Balance_Fatture_Pagamento() {
        Arcadia_HomePage.balancefatturepagamento();
    }

    @Then("User focusing on fattura Pdf new Window")
    public void userFocusingOnFatturaPdfNewWindow() {
        Arcadia_HomePage.switchtofatturapdfTab();
    }

    @Given("Query to check the Light retention Status")
    public void query_to_check_the_Light_retention_Status() {
        try (Connection connection = postgresDBI.getConnection()) {
            Statement statement = connection.createStatement();
            String query = "SELECT * from omcustom.T_ORDER a where a.order_num = '24253241' order by a.creation_date desc";
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


    @When("Completed the Query after check the Listino in Arcadia Home Page")
    public void completedTheQueryAfterCheckTheListinoInArcadiaHomePage() {
        Arcadia_HomePage.VerfiyListinoText();
    }
}
