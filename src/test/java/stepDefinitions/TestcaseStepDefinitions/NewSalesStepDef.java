package stepDefinitions.TestcaseStepDefinitions;

import coeqa.cucumberio.ScenarioContext;
import coeqa.cucumberio.TestContext;
import coeqa.datareader.ConfigReader;
import coeqa.pageobjects.CommonMethods;
import coeqa.pageobjects.NewSalesPage_Offerta;
import coeqa.testbase.TestBase;
import com.applitools.eyes.BatchInfo;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import utils.ExcelReader;
import utils.ExtentReport;
import utils.PostgresDBI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

public class NewSalesStepDef {

//    NewSalesPage_Offerta NewSalesPage_Offerta;
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


    public NewSalesStepDef() {

    }

    public NewSalesStepDef(TestContext context) {
        this.testContext = context;

        commonmethods=testContext.getPageObjectManager().getCommonMethods();
        //coe
        NewSales_Page_Off = testContext.getPageObjectManager().getNewSalesPage();
        sContext = testContext.getScenarioContext();
        testBase = testContext.getPageObjectManager().getTestBase();
        extentReport = testContext.getExtentReport();
        strbatchInfo = commonStepDfn.getBatchInfo();
        Batch_EndState = new BatchInfo(strbatchInfo);
        Batch_EndState.setId(strbatchInfo + testBase.getDate());
        commonTestName = Common_StepDfn.getTestName()+ configReader.getEnv();
    }

//    public String getTestData(String testdata) {
//        return commonStepDfn.getTestData(testdata);
//    }

    //Added by Abi
    public String getTestData(String testdata) {

        String value = commonStepDfn.getTestData(testdata);
        if (value == null) {
            System.out.println("Test data for key '" + testdata + "' is null. Please check the data source.");
            return ""; // Return an empty string or handle it as needed
        }
        return value;
    }

    @Then("User click on Nuovo Contratto Residenziale and select Nuovo Contratto Residenziale TV")
    public void user_click_on_Nuovo_Contratto_Residenziale_and_select_Nuovo_Contratto_Residenziale_TV() throws InterruptedException {
        NewSales_Page_Off.Nuovo_Contratto_Residenziale();
    }

    @Then("User click on BB option and Enter the Address fields")
    public void user_click_on_BB_option_and_Enter_the_Address_fields() {
        NewSales_Page_Off.check_coverage_BB();
//        NewSales_Page_Off.Inserisci_i_Codici_Venditori();
    }


    @Then("User click on Packages and select Promo codes for BB {string}")
    public void userClickOnPackagesAndSelectPromoCodesForBB(String Promo) {
        NewSales_Page_Off.Packages_and_Promos_BB(getTestData(Promo));
        NewSales_Page_Off.Inserisci_i_Codici_Venditori();
    }

    @Then("User click on Packages and select Promo codes for TV {string}" )
    public void user_click_on_Packages_and_select_Promo_codes(String Promo) {
        NewSales_Page_Off.Packages_and_Promos(getTestData(Promo));
        NewSales_Page_Off.Inserisci_i_Codici_Venditori();
    }

    @Then("User should enter the {string} and {string} and {string} Anagrafica intestatario contratto details")
    public void user_should_enter_the_and_and_and_Anagrafica_intestatario_contratto_details(String Nome, String CogNome, String Data_di_nascita) throws ParseException, InterruptedException {
        NewSales_Page_Off.Anagrafica_intestatario_contratto(getTestData(Nome),getTestData(CogNome),getTestData(Data_di_nascita));
    }

    @Then("User should select the {string} in Anagrafica intestatario contratto details")
    public void user_should_select_the_in_Anagrafica_intestatario_contratto_details(String Località_di_nascita) {
        NewSales_Page_Off.Anagrafica_Localita(getTestData(Località_di_nascita));
    }

    @Then("User should select the {string} in Anagrafica intestatario contratto for BB details")
    public void userShouldSelectTheInAnagraficaIntestatarioContrattoForBBDetails(String Località_di_nascita) {
        NewSales_Page_Off.Anagrafica_Localita_BB(getTestData(Località_di_nascita));
    }

    @Then("User should enter the {string} and {string} and {string} Recapiti intestatario contratto details")
    public void userShouldEnterTheAndAndRecapitiIntestatarioContrattoDetails(String email, String Conferma_mail, String cellulare) throws InterruptedException {
        NewSales_Page_Off.Recapiti_intestatario_contratto(getTestData(email),getTestData(Conferma_mail),getTestData(cellulare));
    }

    @Then("User should enter the {string} and {string} Recapiti intestatario contratto details")
    public void user_should_enter_the_and_and_Recapiti_intestatario_contratto_details(String email, String cellulare) throws InterruptedException {
       NewSales_Page_Off.Recapiti_intestatario_contratto_BB(getTestData(email),getTestData(cellulare));
    }

    @Then("User should enter the {string} and {string} and {string} Documento intestatario contratto details")
    public void user_should_enter_the_and_and_Documento_intestatario_contratto_details(String Numero_doc, String Data_Rilascio, String Data_scadenza) throws InterruptedException, ParseException {
        NewSales_Page_Off.Documento_intestatario_contratto(getTestData(Numero_doc),getTestData(Data_Rilascio),getTestData(Data_scadenza));
    }

    @Then("User should enter the Indirizo details")
    public void user_should_enter_the_Indirizo_details() {
        NewSales_Page_Off.Indirizzo_Pagatore();
    }

    @Then("User should enter the Indirizo details in BB")
    public void userShouldEnterTheIndirizoDetailsInBB() {
        NewSales_Page_Off.Indirizzo_Pagatore_BB();
    }

    @Then("User should enter the Pagamento details")
    public void user_should_enter_the_Pagamento_details() {
        NewSales_Page_Off.Pagamento();
    }
    @Then("User should enter the Pagamento details in BB")
    public void userShouldEnterThePagamentoDetailsInBB() {
        NewSales_Page_Off.Pagamento_BB();
    }

    @Then("User should enter the Domiciliazione bancaria IBAN details")
    public void userShouldEnterTheDomiciliazioneBancariaIBANDetails() {
        NewSales_Page_Off.IBAN_Pagamento_details();
        String Extracted_Contracted_Number = NewSales_Page_Off.getExtractedContract_Number();
        System.out.println(Extracted_Contracted_Number);
        String filePath = "src/test/resources/DataFiles/NewSalesLoginPage.xls";
        ExcelReader.writeStringToExcel(Extracted_Contracted_Number, filePath);
    }

    @Then("User should enter the Domiciliazione bancaria IBAN details in BB")
    public void userShouldEnterTheDomiciliazioneBancariaIBANDetailsInBB() {
        NewSales_Page_Off.IBAN_Pagamento_details_BB();
        String Extracted_Contracted_Number = NewSales_Page_Off.getExtractedContract_Number();
        System.out.println(Extracted_Contracted_Number);
        String filePath = "src/test/resources/DataFiles/NewSalesLoginPageBB.xls";
        ExcelReader.writeStringToExcel(Extracted_Contracted_Number, filePath);
    }

    @Given("Query to check the Created Contract Status using contract number {string}")
    public void query_to_check_the_Created_Contract_Status_using_contract_number(String Cont_number){
        try (Connection connection = postgresDBI.getConnection()) {
            String Extracted_Contracted_Number = NewSales_Page_Off.getExtractedContract_Number();
            Statement statement = connection.createStatement();
            String query = "SELECT * from omcustom.T_ORDER a where a.order_num = '"+Extracted_Contracted_Number+"' order by a.creation_date desc";
            System.out.println(query);
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
