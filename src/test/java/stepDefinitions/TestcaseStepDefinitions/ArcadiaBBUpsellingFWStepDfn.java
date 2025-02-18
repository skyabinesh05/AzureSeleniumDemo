package stepDefinitions.TestcaseStepDefinitions;

import coeqa.cucumberio.ScenarioContext;
import coeqa.cucumberio.TestContext;
import coeqa.datareader.ConfigReader;
import coeqa.pageobjects.Arcadia_BB_Upselling_FW;
import coeqa.pageobjects.ArcadiaHomePage;
import coeqa.pageobjects.CommonMethods;
import coeqa.testbase.TestBase;
import com.applitools.eyes.BatchInfo;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import utils.ExtentReport;
import coeqa.datareader.GetdatafromExcel;
import utils.PostgresDBI;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import static utils.ExcelActions.getTestData;

public class ArcadiaBBUpsellingFWStepDfn {

    static String commonTestName;
    static TestBase testBase;
    TestContext testContext;
    BatchInfo Batch_EndState;
    ArcadiaHomePage Arcadia_HomePage;
    Arcadia_BB_Upselling_FW Arcadia_BB_UpsellingFW;
    ScenarioContext sContext;
    ExtentReport extentReport;
    String strbatchInfo;
    ConfigReader configReader = new ConfigReader();
    Common_StepDfn commonStepDfn = new Common_StepDfn();
    CommonMethods commonmethods;
    PostgresDBI postgresDBI;

    public ArcadiaBBUpsellingFWStepDfn() {

    }

    public ArcadiaBBUpsellingFWStepDfn(TestContext context) {
        this.testContext = context;

        commonmethods=testContext.getPageObjectManager().getCommonMethods();
        //coe
        Arcadia_BB_UpsellingFW = testContext.getPageObjectManager().getArcadia_BB_UpsellingFW();
        sContext = testContext.getScenarioContext();
        testBase = testContext.getPageObjectManager().getTestBase();
        extentReport = testContext.getExtentReport();
        strbatchInfo = commonStepDfn.getBatchInfo();
        Batch_EndState = new BatchInfo(strbatchInfo);
        Batch_EndState.setId(strbatchInfo + testBase.getDate());
        commonTestName = Common_StepDfn.getTestName()+ configReader.getEnv();
    }

    @Then("User click on BB under Consistenza")
    public void userClickOnBBUnderConsistenza() throws InterruptedException {
        Arcadia_BB_UpsellingFW.Click_BB_UnderConsistenza();
    }

    public String getTestData(String testdata) {
        return commonStepDfn.getTestData(testdata);
    }

    //Upselling - FW Address
    @Then("User enters {string} {string} and {string}")
    public void userEntersAnd(String Citta, String Indirizzo, String NumCivico) throws InterruptedException, IOException {
        Arcadia_BB_UpsellingFW.EnterAdrressDetails(getTestData(Citta), getTestData(Indirizzo), getTestData(NumCivico));
    }

    @Then("User click Broadband image in Verfica Copertura page")
    public void userClickBroadbandImageInVerficaCoperturaPage() throws InterruptedException {
        Arcadia_BB_UpsellingFW.verificaCoperturaPage();
    }

    @Then("User remove Voice under Gestione Consistenza in Composizione Offerta Page")
    public void userRemoveVoiceUnderGestioneConsistenzaInComposizioneOffertaPage() throws InterruptedException {
        Arcadia_BB_UpsellingFW.gestioneConsistenza();
        Arcadia_BB_UpsellingFW.RemoveVoice();
        Arcadia_BB_UpsellingFW.gestioneConsistenza_Avanti();

    }


    @Then("User chooses promo {string} in the Composizione Offerta Page")
    public void userChoosesPromoInTheComposizioneOffertaPage(String BB_Promo) throws InterruptedException {
        Arcadia_BB_UpsellingFW.AddBBPromo(getTestData(BB_Promo));
        Arcadia_BB_UpsellingFW.gestioneConsistenza_Avanti();

    }

    @Then("User click Fine in Delibera AGCOM page")
    public void userClickFineInDeliberaAGCOMPage() throws InterruptedException {
        Arcadia_BB_UpsellingFW.DeliberaAGCOM_ClickFine();
    }

    @Then("User click Avanti in Metodo di Pagamento page")
    public void userClickAvantiInMetodoDiPagamentoPage() throws InterruptedException {
        Arcadia_BB_UpsellingFW.changeMOPCDCtoRID();
        Arcadia_BB_UpsellingFW.MOP_clickAvanti();
    }

    @Then("User click on Sky Wifi Hub option in Attivazione nuova linea o migrazione page")
    public void userClickOnSkyWifiHubOptionInAttivazioneNuovaLineaOMigrazionePage() throws InterruptedException {
        Arcadia_BB_UpsellingFW.click_SkyWifiHub();
    }

    @Then("User click on Cellulare Option in Numero di Contatto page")
    public void userClickOnCellulareOptionInNumeroDiContattoPage() throws InterruptedException {
        Arcadia_BB_UpsellingFW.click_CellulareOption();
    }

    @Then("User enter details in Verifica Documento page")
    public void userEnterDetailsInVerificaDocumentoPage() throws ParseException, InterruptedException {
        Arcadia_BB_UpsellingFW.verificaDocumento();
    }

    @Then("User click Next in Spedizione Tramite Corriere page")
    public void userClickNextInSpedizioneTramiteCorrierePage() throws InterruptedException {
        Arcadia_BB_UpsellingFW.SpedizioneTramiteCorriere();
    }

    @Then("User click checkbox and proceed in Diritto di ripensamento page")
    public void userClickCheckboxAndProceedInDirittoDiRipensamentoPage() throws InterruptedException {
        Arcadia_BB_UpsellingFW.DirittoDiRipensamento();
    }

    @Then("User Confirm Appointment in Appuntamento page")
    public void userConfirmAppointmentInAppuntamentoPage() throws InterruptedException {
        Arcadia_BB_UpsellingFW.AppuntamentoPage();
    }

    @Then("User Click Avanti in Conferma Ordine page")
    public void userClickAvantiInConfermaOrdinePage() throws InterruptedException {
        Arcadia_BB_UpsellingFW.ConfermaOrdinePage();
    }

    @Then("User click on Registrazione Vocal Order option in Firma Digitale page")
    public void userClickOnRegistrazioneVocalOrderOptionInFirmaDigitalePage() throws InterruptedException {
        Arcadia_BB_UpsellingFW.FirmaDigitalePage();
    }

    @Then("User enter details and give {string} in Canale di Comunicazione page")
    public void userEnterDetailsAndGiveInCanaleDiComunicazionePage(String email) throws InterruptedException {
        Arcadia_BB_UpsellingFW.CanaleDiComunicazionePage(getTestData(email));
    }


    //Upselling - OF Address
    @Then("User enters OF Address {string} {string} and {string}")
    public void userEntersOFAddressAnd(String Citta, String Indirizzo, String NumCivico) throws InterruptedException {
        Arcadia_BB_UpsellingFW.EnterPassiveAdrressDetails(getTestData(Citta), getTestData(Indirizzo), getTestData(NumCivico));
    }

    //Upselling - Passive Address
    @Then("User enters Passive Address {string} {string} and {string}")
    public void userEntersPassiveAddressAnd(String Citta, String Indirizzo, String NumCivico) throws InterruptedException {
        Arcadia_BB_UpsellingFW.EnterPassiveAdrressDetails(getTestData(Citta), getTestData(Indirizzo), getTestData(NumCivico));
    }

    @Given("Query to check the Contract Status after Upselling using contract number {string}")
    public void queryToCheckTheContractStatusAfterUpsellingUsingContractNumber(String CodiceContratto_Passivo) {
        Arcadia_BB_UpsellingFW.CheckPostgresStatus_afterUpselling_IT(getTestData(CodiceContratto_Passivo));
    }

    //Upselling - FW Address
    @Then("User enters FW Address in IT Chain {string} {string} and {string}")
    public void userEntersFWAddressInITChainAnd(String Citta, String Indirizzo, String NumCivico) throws InterruptedException {
        Arcadia_BB_UpsellingFW.EnterFWAdrressDetails(getTestData(Citta), getTestData(Indirizzo), getTestData(NumCivico));
    }
}
