package stepDefinitions.TestcaseStepDefinitions;

import coeqa.cucumberio.ScenarioContext;
import coeqa.cucumberio.TestContext;
import coeqa.datareader.ConfigReader;
import coeqa.pageobjects.CommonMethods;
import coeqa.pageobjects.ArcadiaHomePage;
import coeqa.pageobjects.NewSalesPage_Offerta;
import coeqa.testbase.TestBase;
import com.applitools.eyes.BatchInfo;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.testng.Assert;
import utils.ExcelReader;
import utils.ExtentReport;
import utils.PostgresDBI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class BB_29223_OrderEntry_OF_AreaCD_RID {

    static String commonTestName;
    static TestBase testBase;
    TestContext testContext;
    BatchInfo Batch_EndState;
    ArcadiaHomePage Arcadia_HomePage;
    NewSalesPage_Offerta NewSales_Page_Off;
    ScenarioContext sContext;
    ExtentReport extentReport;
    String strbatchInfo;
    ConfigReader configReader = new ConfigReader();
    Common_StepDfn commonStepDfn = new Common_StepDfn();
    CommonMethods commonmethods;
    PostgresDBI postgresDBI;

    public BB_29223_OrderEntry_OF_AreaCD_RID() {

    }

    public BB_29223_OrderEntry_OF_AreaCD_RID(TestContext context) {
        this.testContext = context;

        commonmethods=testContext.getPageObjectManager().getCommonMethods();
        //coe
        NewSales_Page_Off = testContext.getPageObjectManager().getNewSalesPage();
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

    /***
     * Area CD - Check Coverage
     * @Author Dhanya
     */
    @Then("User click on BB option and Enter the Address fields for Area CD")
    public void userClickOnBBOptionAndEnterTheAddressFieldsForAreaCD() {
        NewSales_Page_Off.check_coverage_BB_AreaCD();
    }

    @Then("User click on Packages and select Promo codes for BB Area CD {string}")
    public void userClickOnPackagesAndSelectPromoCodesForBBAreaCD(String Promo) {
        NewSales_Page_Off.Packages_and_Promos_AreaCD(getTestData(Promo));
        NewSales_Page_Off.Inserisci_i_Codici_Venditori();
    }


    @Then("User should select the {string} in Anagrafica intestatario contratto for BB Area CD details")
    public void userShouldSelectTheInAnagraficaIntestatarioContrattoForBBAreaCDDetails(String Località_di_nascita) {
        NewSales_Page_Off.Anagrafica_Localita_BB_AreaCD(getTestData(Località_di_nascita));
    }

    @Then("User should enter the Indirizo details {string} in BB Area CD")
    public void userShouldEnterTheIndirizoDetailsInBBAreaCD(String Citofono) {
        NewSales_Page_Off.Indirizzo_Pagatore_BB_AreaCD(getTestData(Citofono));
    }


    @Then("User should enter the Domiciliazione bancaria IBAN details in BB Area CD")
    public void userShouldEnterTheDomiciliazioneBancariaIBANDetailsInBBAreaCD() {
        NewSales_Page_Off.IBAN_Pagamento_details_BB_AreaCD();
        String Extracted_Contracted_Number = NewSales_Page_Off.getExtractedContract_Number();
        System.out.println(Extracted_Contracted_Number);
        String filePath = "src/test/resources/DataFiles/TestData_BB.xls";
        ExcelReader.writeStringToExcel(Extracted_Contracted_Number, filePath);
        }

    @Given("Query to check the Created BB Area CD Contract Status using contract number {string}")
    public void queryToCheckTheCreatedBBAreaCDContractStatusUsingContractNumber(String CodiceContratto_AreaCD) {
        String Estatus = "EXECUTION";
        String Astatus = "";
        String EsubStatus = "ACCEPTANCE";
        String AsubStatus = "";
        //String conNum = CodiceContratto_AreaCD;
        String EDate = "";

        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = formatter.format(today);

        try (Connection connection = postgresDBI.getConnectionIT()) {
            String Extracted_Contracted_Number = NewSales_Page_Off.getExtractedContract_Number();
            Thread.sleep(3000);
            NewSales_Page_Off.click_Chiudi();
            Thread.sleep(3000);
            Statement statement = connection.createStatement();
            String query = "SELECT * from omcustom.T_ORDER a where a.order_num = '" + Extracted_Contracted_Number + "' order by a.creation_date desc";
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {

                Astatus = resultSet.getString("status");
                AsubStatus = resultSet.getString("sub_status");

                String input = resultSet.getString("creation_date");
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
                LocalDateTime dateTime = LocalDateTime.parse(input, inputFormatter);
                LocalDate date = dateTime.toLocalDate();
                EDate = String.valueOf(date);
                System.out.println("AStatus>>>>>>" + Astatus);
                System.out.println("ASubStatus>>>>>>" + AsubStatus);
                System.out.println("order_id\t--\t" + resultSet.getString("order_id"));
                System.out.println("order_ref\t--\t" + resultSet.getString("order_ref"));
                System.out.println("order_num\t--\t" + resultSet.getString("order_num"));
                System.out.println("status\t--\t" + resultSet.getString("status"));
                System.out.println("sub_status\t--\t" + resultSet.getString("sub_status"));
                System.out.println("creation_date\t--\t" + resultSet.getString("creation_date"));
                System.out.println("processing_date\t--\t" + resultSet.getString("processing_date"));
            }
            else {
                System.out.println("No data found in the table.");
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(Astatus, Estatus, "Status is not equal");
        Assert.assertEquals(AsubStatus, EsubStatus, "Sub Status is not equal");
        Assert.assertEquals(EDate, todayDate, "Date is not equal");

    }

    @Given("Query to fetch Appointment Date and Time from Payload")
    public void queryToFetchAppointmentDateAndTimeFromPayload() {
        try (Connection connection = postgresDBI.getConnection()) {
            String Extracted_Contracted_Number = NewSales_Page_Off.getExtractedContract_Number();
            Statement statement = connection.createStatement();
            String query = "select * from omcustom.t_tracking_system where order_id ='S-00000000K18QY' order by timestamp_event desc";
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                // Process the result set


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Then("User validates the Status of BB Area CD Contract in portale page")
    public void userValidatesTheStatusOfBBAreaCDContractInPortalePage() throws InterruptedException {
        Arcadia_HomePage.checkContractStatus_BBAreaCD();
    }


    @Given("Query to check the contract status after JMeter job is run using contract number {string}")
    public void queryToCheckTheContractStatusAfterJMeterJobIsRunUsingContractNumber(String Codice_AreaCD) {
        Arcadia_HomePage.verifyPostgresStatus_afterJMeter_IT(getTestData(Codice_AreaCD));
    }


}


