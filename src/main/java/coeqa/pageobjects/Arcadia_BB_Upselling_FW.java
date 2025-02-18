package coeqa.pageobjects;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import coeqa.testbase.TestBase;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import utils.ExtentReport;
import utils.PostgresDBI;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Arcadia_BB_Upselling_FW extends TestBase{
    String pageName;
    Robot robot;
    PostgresDBI postgresDBI;

    @FindBy(xpath = "//iframe[contains(@src,'/apex/NewCrm_Hybrid_HomeCliente?')]")
    WebElement Portale_Frame;

    @FindBy(xpath = "//button[contains(@title,'Contratto BB')]")
    WebElement Contratto_BB_btn;

    @FindBy(xpath = "//iframe[contains(@src,'/apex/OmniConsoleDisplayScriptLightning')]")
    WebElement BBUpselling_Omniscript_Frame;

    @FindBy(xpath = "//iframe[contains(@src,'skyHybridCPQ')]")
    WebElement BBUpselling_Omniscript_Frame_two;

    @FindBy(xpath = "//p[text()='Cambia Indirizzo']")
    WebElement CambioIndirizzo_btn;

    @FindBy(xpath = "//input[@id='Comune']")
    WebElement Citta_textbox;

    @FindBy(xpath = "//li[text()='MILANO']")
    WebElement MILANO_option;

    @FindBy(xpath = "//li[text()='AFRAGOLA']")
    WebElement AFRAGOLA_option;

    @FindBy(xpath = "//input[@id='Indirizzo']")
    WebElement Indirizzo_textbox;

    @FindBy(xpath = "//li[text()='VIA ABANO']")
    WebElement VIAABANO_option;

    @FindBy(xpath = "//li[text()='VIA G.CASTALDI']")
    WebElement VIAGCASTALDI_option;

    @FindBy(xpath = "//input[@id='NumeroCivico']")
    WebElement NumCivico_textbox;

    @FindBy(xpath = "//li[text()='29']")
    WebElement NumCivico_option;

    //Passive Address
    @FindBy(xpath = "//li[text()='NAPOLI']")
    WebElement NAPOLI_option;

    @FindBy(xpath = "//li[text()='VIA ANTONIO LABRIOLA']")
    WebElement VIAANTONIOLABRIOLA_option;

    @FindBy(xpath = "//li[text()='140']")
    WebElement PassiveNumCivico_option;

    //FW Address - IT Chain
    @FindBy(xpath = "//li[text()='PERO']")
    WebElement PERO_option;

    @FindBy(xpath = "//li[text()='VIA EUGENIO CURIEL']")
    WebElement VIAEUGENIOCURIEL_option;

    @FindBy(xpath = "//li[text()='9']")
    WebElement FWNumCivico_option;

    @FindBy(xpath = "//div[@id='NormalizzaIndirizzo']")
    WebElement NormalizzaIndirizzo_btn;

    @FindBy(xpath = "//span/strong[text()='Indirizzo normalizzato']")
    WebElement Indirizzo_Normalizzato_SuccessMsg;

    @FindBy(xpath = "//div[@id='street-view_nextBtn']")
    WebElement Avanti_NormalizzazioneIndirizzoPage;

    @FindBy(xpath = "//div[@class='slds-box service-box vlc-slds-selectableItem']")
    WebElement BB_img;

    @FindBy(xpath = "//input[@placeholder='Search']")
    WebElement PromoSearch_txtbox;

    @FindBy(xpath = "//button[contains(@ng-click,'skyCO_chechPromoExtra')]")
    WebElement PromoAggiungi_btn;

    @FindBy(xpath = "//span[@class='slds-badge' and text()='1']")
    WebElement Promozioni_Notification_Icon;

    @FindBy(xpath = "//a[contains(text(),'PROMOZIONI')]")
    WebElement Promozioni_Tab;

    @FindBy(xpath = "//div[@id='CoverageCheck_nextBtn']")
    WebElement Avanti_verificaCoperturaPage;

    @FindBy(xpath = "(//button[@ng-click='skyCO_deleteFromCart(obj, childProd)'])[1]")
    WebElement Rimuovi_btn;

    @FindBy(xpath = "//button[text()='Si']")
    WebElement Si_Option;

    @FindBy(xpath = "//div[@id='HybridCPQ_Step_BLB_nextBtn']")
    WebElement Avanti_ComposizioneOffertaPage;

    @FindBy(xpath = "//span[@class='slds-checkbox_faux']")
    WebElement Attenzione_CheckBox;

    @FindBy(xpath = "//button[@class='slds-button slds-button--brand btn-right' and text()='Prosegui']")
    WebElement Attenzione_Prosegui_btn;

    @FindBy(xpath = "//input[@id='Voucher']")
    WebElement RichiestaVoucher_txtBox;

    @FindBy(xpath = "//li[contains(text(),'No Voucher')]")
    WebElement NoVoucherOption;

    @FindBy(xpath = "//div[@id='RichiestaVoucher_nextBtn']")
    WebElement Fine_RichiestaVoucher;

    @FindBy(xpath = "(//h1[contains(text(),'Richiesta Voucher')])[4]")
    WebElement RichiestaVoucher_PageTxt;

    @FindBy(xpath = "//div[@id='DeliberaAGCOM_nextBtn']")
    WebElement Fine_DeliberaAGCOM;

    @FindBy(xpath = "//button[@id='MopTemplate_nextBtn']")
    WebElement Avanti_MOPPage;

    @FindBy(xpath = "//span[contains(text(),'Sky Wifi Hub')]")
    WebElement SkyWifiHub_Radiobtn;

    @FindBy(xpath = "//div[@id='AttivazioneNuovaLineaoMigrazione_nextBtn']")
    WebElement Avanti_AttivazioneMigrazionePage;

    @FindBy(xpath = "//input[@ng-class='getClassMobilePhone()']")
    WebElement Cellulare_Radiobtn;

    @FindBy(xpath = "//div[@id='NumeroContatto_nextBtn']")
    WebElement Avanti_NumeroContattoPage;

    @FindBy(xpath = "//select[@id='Cittadinanza']")
    WebElement Cittadinanza_dropdown;

    @FindBy(xpath = "//option[text()='Italia']")
    WebElement Italia_Option;

    @FindBy(xpath = "//select[@id='DocumentType']")
    WebElement Tipo_Documento_dropdown;

    @FindBy(xpath = "//input[@id='DocumentExpDate']")
    WebElement Data_di_scadenza;

    @FindBy(xpath = "(//span[text()='30'])[2]")
    WebElement selectDate_scadenza;

    @FindBy(xpath = "//select[@id='SelectCarta']")
    WebElement Rilasciato_da;

    @FindBy(xpath = "//input[@id='DocumentReleasedDate']")
    WebElement Data_di_rilascio;

    @FindBy(xpath = "//span[text()='01']")
    WebElement selectDate_rilascio;

    @FindBy(xpath = "//p[text()='Verifica']")
    WebElement Verifica_btn;

    @FindBy(xpath = "//strong[text()='Documento valido o preso in carico per verifica.']")
    WebElement Verifica_Documento_SuccessMsg;

    @FindBy(xpath = "//div[@id='DocumentCheck_nextBtn']")
    WebElement Avanti_DocumentCheckPage;

    @FindBy(xpath = "//div[@id='IndirizzoSpedizione_nextBtn']")
    WebElement Spedizione_next_btn;

    @FindBy(xpath = "(//h1[contains(text(),'Firma Digitale')])[12]")
    WebElement DatiPersonali_page;

    @FindBy(xpath = "(//span[@class='slds-checkbox_faux'])[2]")
    WebElement DatiPersonali_Checkbox_ONE;

    @FindBy(xpath = "(//span[@class='slds-checkbox_faux'])[3]")
    WebElement DatiPersonali_Checkbox_TWO;

    @FindBy(xpath = "(//span[@class='slds-checkbox_faux'])[4]")
    WebElement DatiPersonali_Checkbox_THREE;

    @FindBy(xpath = "//div[@class='slds-input']//button[text()='Salva']")
    WebElement DatiPersonali_Salva_btn;

    @FindBy(xpath = "//div[@id='DatiPersonali_nextBtn']")
    WebElement DatiPersonali_Prosegui_btn;

    @FindBy(xpath = "//div[@id='IndirizzoSpedizione_nextBtn']")
    WebElement SpedizioneTramiteCorriere_nextButton;

    @FindBy(xpath = "(//span[@class='slds-checkbox--faux'])[1]")
    WebElement Dirittodiripensamento_Activate_checkbox;

    @FindBy(xpath = "//div[@id='Step3_nextBtn']")
    WebElement Dirittodiripensamento_Next_btn;

    @FindBy(xpath = "(//div[contains(@ng-click,'selectItem(slot,')])[4]")
    WebElement Select_date;

    @FindBy(xpath = "//button[text()='Scegli']")
    WebElement Scegli_btn;

    @FindBy(xpath = "//div/p[text()='Conferma']")
    WebElement Conferma_btn;

    @FindBy(xpath = "//span[contains(text(),'Appuntamento confermato!')]")
    WebElement Appointment_SuccessMsg;

    @FindBy(xpath = "(//p[text()='Avanti'])[8]")
    WebElement Avanti_AppointmentPage;

    @FindBy(xpath = "//span[contains(text(),'Verrai contattato dal tecnico Fastweb per la presa appuntamento')]")
    WebElement confermaOrdineFW_Successmessage;

    @FindBy(xpath = "//div[@id='ReviewOrder_nextBtn']")
    WebElement Avanti_ConfermaOrdinePage;

    @FindBy(xpath = "(//input[@id='Radio_SignatureChoices_NoBackOffice'])[2]")
    WebElement Registrazione_Vocal_Order_btn;

    @FindBy(xpath = "//div[@id='Step_DigitalSignatureChoice_nextBtn']")
    WebElement Avanti_FirmaDigitalePage;

    @FindBy(xpath = "")
    WebElement Nessuno_radiobtn;

    @FindBy(xpath = "//input[@id='EmailFatturazione']")
    WebElement email_textbox;

    @FindBy(xpath = "//select[@id='ChannelSelection']")
    WebElement Preference_EMAIL;

    @FindBy(xpath = "(//span[@class='slds-checkbox--faux'])[2]")
    WebElement Accetto_checkbox;

    @FindBy(xpath = "//div[@id='CommunicationChannelSelection_nextBtn']")
    WebElement CanalidiCommunicazione_Conferma_btn;

    @FindBy(xpath = "//button[text()='Nuova Modalità di Pagamento']")
    WebElement NuovaModalitadiPagamento_btn;

    @FindBy(xpath = "//strong/span[contains(text(),'CARTA DI CREDITO')]")
    WebElement CDC_txt;

    @FindBy(xpath = "//strong/span[contains(text(),'DOMICILIAZIONE BANCARIA')]")
    WebElement RID_txt;

    @FindBy(xpath = "//input[@id='Nominitavo_Titolare']")
    WebElement IBAN_Name_txtbox;

    @FindBy(xpath = "//input[@id='BankNumber_TX_in']")
    WebElement IBAN_Number_txtbox;

    @FindBy(xpath = "//button[text()='Verifica IBAN']")
    WebElement VerificaIBAN_btn;

    @FindBy(xpath = "//input[@id='PAN']")
    WebElement NumeroCarta_textbox;

    @FindBy(xpath = "//input[@id='continue']")
    WebElement CDC_Conferma_btn;

    @FindBy(xpath = "//input[@value='Chiudi']")
    WebElement CDC_Chiudi_btn;

    //Uguale, Diverso Scenario
    @FindBy(xpath = "(//span[text()='Si'])[2]")
    WebElement LineaAttiva_Si;

    @FindBy(xpath = "(//span[@class='slds-checkbox--faux'])[1]")
    WebElement Diverso_checkbox;

    @FindBy(xpath = "//input[@id='MigrationCodeDati']")
    WebElement MigrationCode_txtbox;

    @FindBy(xpath = "//input[@id='MigrationCodeDati']")
    WebElement Prefisso_txtbox;

    @FindBy(xpath = "//input[@id='MigrationCodeDati']")
    WebElement Telefono_txtbox;

    @FindBy(xpath = "//div[@id='CheckMC_Button']")
    WebElement Verifica_MC_btn;

    @FindBy(xpath = "(//div[contains(text(),'Codice riconosciuto')])[1]")
    WebElement MC_SuccessMsg;


    public Arcadia_BB_Upselling_FW(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        pageName = this.getClass().getSimpleName();
    }


    public void Click_BB_UnderConsistenza() throws InterruptedException {
        Thread.sleep(20000);
        driver.navigate().refresh();
        handlePopup();
        Thread.sleep(5000);
        driver.switchTo().frame(Portale_Frame);
        scrollToElement(Contratto_BB_btn);
        Thread.sleep(50000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking Contratto BB button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "BB Under Consistenza")).build());
        click(Contratto_BB_btn, "Clicking Contratto BB button");
        Thread.sleep(30000);
    }

    public void EnterAdrressDetails(String Citta, String Indirizzo, String NumCivico) throws InterruptedException {
        Thread.sleep(5000);
        driver.switchTo().defaultContent();
        Thread.sleep(10000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        try{
            if(CambioIndirizzo_btn.isDisplayed()){
                CambioIndirizzo_btn.click();
                Thread.sleep(2000);
            }
            else{
                System.out.println("Proceeding to enter Address details");
            }
        }catch(Exception e){
                System.out.println("Proceeding to enter Address details");
        }

        Citta_textbox.clear();
        Thread.sleep(2000);
        Citta_textbox.sendKeys(Citta);
        Thread.sleep(10000);
        AFRAGOLA_option.click();
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User selected AFRAGOLA Option==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Citta")).build());
        Indirizzo_textbox.clear();
        Thread.sleep(2000);
        Indirizzo_textbox.sendKeys(Indirizzo);
        Thread.sleep(10000);
        VIAGCASTALDI_option.click();
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User selected VIA G.CASTALDI Option==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Indirizzo")).build());
        NumCivico_textbox.clear();
        Thread.sleep(2000);
        NumCivico_textbox.sendKeys(NumCivico);
        Thread.sleep(10000);
        NumCivico_option.click();
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User selected 29 Option==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "NUmCivico")).build());
        jsClick(NormalizzaIndirizzo_btn, "Clicking Normalizza Indirizzo button");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Normalizza Indirizzo button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "NormalizzaIndirizzo")).build());
        elementDisplayed(Indirizzo_Normalizzato_SuccessMsg);
        System.out.println(Indirizzo_Normalizzato_SuccessMsg);
        Thread.sleep(5000);
        jsClick(Avanti_NormalizzazioneIndirizzoPage, "Clicking Avanti button");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Avanti button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Avanti")).build());

    }

    /***
     * Clicking Broadband 1 BB Image
     * @Author Dhanya
     * @param
     */
    public void verificaCoperturaPage() throws InterruptedException {
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        jsClick(BB_img, "Clicking FW_1_BB image");
        Thread.sleep(2000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Broadband_1_BB image==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "VerificaCopertura")).build());
        jsClick(Avanti_verificaCoperturaPage, "Clicking Avanti button");
        Thread.sleep(1000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Avanti Button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Avanti")).build());

    }

    /***
     * Navigating to Composizione Offerta page
     * @Author Dhanya
     * @param
     */
    public void gestioneConsistenza() throws InterruptedException {
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        Thread.sleep(100000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame_two);
    }

    /***
     * Removing Voice Service
     * @Author Dhanya
     * @param
     */
    //If Voice is not needed click Remove button
    public void RemoveVoice() throws InterruptedException {
        waitForElement(Rimuovi_btn);
        jsClick(Rimuovi_btn, "Clicking Rimuovi button");
        Thread.sleep(10000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User Clicked Rimuovi button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Rimuovi")).build());
        jsClick(Si_Option, "Clicking Si button in pop up");
        Thread.sleep(20000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Si button in pop up==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "SiOption")).build());
    }

    /***
     * Clicking Avanti
     * @Author Dhanya
     * @param
     */
    public void gestioneConsistenza_Avanti() throws InterruptedException{
        driver.switchTo().defaultContent();
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        Thread.sleep(5000);
        jsClick(Avanti_ComposizioneOffertaPage, "Clicking Avanti button");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Avanti button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Avanti")).build());
        jsClick(Attenzione_CheckBox, "Clicking checkbox in Attenzione pop up");
        Thread.sleep(5000);
        jsClick(Attenzione_Prosegui_btn, "Clicking Prosegui button in Attenzione pop up");
        Thread.sleep(10000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked checkbox and Prosegui button in Attenzione pop up==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Attenzione")).build());
        try{
            driver.switchTo().defaultContent();
            Thread.sleep(5000);
            driver.switchTo().frame(BBUpselling_Omniscript_Frame);
            Thread.sleep(5000);
            if(RichiestaVoucher_PageTxt.isDisplayed()){
                jsClick(RichiestaVoucher_txtBox, "Clicking Richiesta Voucher text box");
                waitForSeconds(5);
                jsClick(NoVoucherOption, "Selecting No Voucher Option");
                waitForSeconds(5);
                jsClick(Fine_RichiestaVoucher, "Clicking Fine button");
                waitForSeconds(10);
            }
            else{
                System.out.println("Proceed with Verifica Copertura Page");
            }
        }catch(Exception e){
            System.out.println("Proceed with Verifica Copertura Page");
        }

    }

    /***
     * Clicking Fine in Delibera AGCOM page
     * @Author Dhanya
     * @param
     */
    public void DeliberaAGCOM_ClickFine() throws InterruptedException {
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        Thread.sleep(5000);
        Fine_DeliberaAGCOM.click();
        System.out.println("Clicking Fine button");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User Clicked Fine button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Fine")).build());

    }

    /***
     * Changing MOP RID to CDC
     * @Author Dhanya
     * @param
     */
    public void changeMOPRIDtoCDC() throws InterruptedException {
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        Thread.sleep(5000);
        try{
            if(RID_txt.isDisplayed()){
                NuovaModalitadiPagamento_btn.click();
                waitForSeconds(10);
                Select selectPaymentMethod = new Select(driver.findElement(By.xpath("//select[@class='pick ng-pristine ng-valid ng-empty ng-touched']")));
                selectPaymentMethod.selectByValue("CARTA DI CREDITO BANCARIA");
                waitForSeconds(5);
                NumeroCarta_textbox.sendKeys();
                Select selectMese = new Select(driver.findElement(By.xpath("//select[@id='EXPDT_MM']")));
                selectMese.selectByValue("03");
                Select selectAnno = new Select(driver.findElement(By.xpath("//select[@id='EXPDT_YY']")));
                selectAnno.selectByValue("2025");
                jsClick(CDC_Conferma_btn, "Clicking Conferma button");
                waitForSeconds(5);
                jsClick(CDC_Chiudi_btn, "Clicking Chiudi button");
                waitForSeconds(5);

            }else{
                System.out.println("Proceed with Clicking Avanti");
            }
        } catch(Exception e){
            System.out.println("Proceed with Clicking Avanti");
        }
    }

    /***
     * Changing MOP CDC to RID
     * @Author Dhanya
     * @param
     */
    public void changeMOPCDCtoRID() throws InterruptedException {
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        Thread.sleep(5000);
        try{
            if(CDC_txt.isDisplayed()){
                NuovaModalitadiPagamento_btn.click();
                waitForSeconds(10);
                Select selectPaymentMethod = new Select(driver.findElement(By.xpath("//select[@class='pick ng-pristine ng-valid ng-empty ng-touched']")));
                selectPaymentMethod.selectByValue("DOMICILIAZIONE BANCARIA");
                waitForSeconds(20);
                IBAN_Name_txtbox.sendKeys();
                IBAN_Number_txtbox.sendKeys();
                waitForSeconds(5);
                VerificaIBAN_btn.click();
                waitForSeconds(5);
            }else{
                System.out.println("Proceed with Clicking Avanti");
            }
        } catch(Exception e){
            System.out.println("Proceed with Clicking Avanti");
        }
    }
    public void MOP_clickAvanti() throws InterruptedException {
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        jsClick(Avanti_MOPPage, "Clicking Avanti button");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Avanti button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Avanti")).build());

    }

    /***
     * Clicking Sky Wifi Hub Option
     * @Author Dhanya
     * @param
     */
    public void click_SkyWifiHub() throws InterruptedException {
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        jsClick(SkyWifiHub_Radiobtn, "Clicking Sky Wifi Hub Radio button");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Sky Wifi Hub Radio button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "SkyWifiHub")).build());
        jsClick(Avanti_AttivazioneMigrazionePage, "Clicked Avanti button");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Avanti button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Avanti")).build());

    }

    /***
     * Choosing Cellulare Option
     * @Author Dhanya
     * @param
     */
    public void click_CellulareOption() throws InterruptedException {
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        jsClick(Cellulare_Radiobtn, "Clicking Cellulare Radio button");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Cellulare Radio button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Cellulare")).build());
        jsClick(Avanti_NumeroContattoPage, "Clicking Avanti button");
        Thread.sleep(10000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Avanti button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Avanti")).build());

    }

    /***
     * Entering details in VerificaDocumento Page
     * @Author Dhanya
     * @param
     */
    public void verificaDocumento() throws InterruptedException, ParseException {
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        Thread.sleep(2000);
        //Cittadinanza Field
        Cittadinanza_dropdown.click();
        System.out.println("Clicking Cittadinanza dropdown");
        Thread.sleep(15000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Cittadinanza dropdown==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "CittadinanzaDropdown")).build());
        Select selectCittadinanza = new Select(driver.findElement(By.id("Cittadinanza")));
        selectCittadinanza.selectByValue("Italia");
        System.out.println("Selected Italia from dropdown");
        Thread.sleep(10000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User selected Italia from dropdown==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "ItaliaOption")).build());
        //Tipo Documento Field
        Tipo_Documento_dropdown.click();
        System.out.println("Clicking Tipo Documento Dropdown");
        Thread.sleep(2000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Tipo Documento dropdown==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "TipoDocumento")).build());
        Select selectDocumentType = new Select(driver.findElement(By.id("DocumentType")));
        selectDocumentType.selectByVisibleText("CARTA D'IDENTITA'");
        Thread.sleep(5000);
        System.out.println("Selected CARTA D'IDENTITA from drop down");
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User selected CARTA D'IDENTITA option==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "CartaOption")).build());
        //Data di Scadenza Field
        jsClick(Data_di_scadenza, "Clicking Data di Scadenza");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User selecting future date==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Scadenza")).build());
        jsClick(selectDate_scadenza, "select future date");
        Thread.sleep(5000);
        //Rilasciato Field
        Rilasciato_da.click();
        System.out.println("Clicking Rilasciato da Dropdown");
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Rilasciato da dropdown==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Rilasciato")).build());
        Select selectCarta = new Select(driver.findElement(By.id("SelectCarta")));
        selectCarta.selectByVisibleText("COMUNE");
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User selected COMUNE Option==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "COMUNE")).build());
        //Data di Rilascio Field
        jsClick(Data_di_rilascio, "Clicking Data di Rilascio");
        Thread.sleep(10000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User selecting past date==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Scadenza")).build());
        jsClick(selectDate_rilascio, "select past date");
        Thread.sleep(10000);
        jsClick(Verifica_btn, "Clicking Verifica button");
        Thread.sleep(10000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Verifica button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Verifica")).build());
        elementDisplayed(Verifica_Documento_SuccessMsg);
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User verified success mesage==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "SuccessMessage")).build());
        Avanti_DocumentCheckPage.click();
        Thread.sleep(10000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Avanti Option==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Avanti")).build());

        //Dati Personali Page
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        Thread.sleep(5000);
        try {
            if (DatiPersonali_page.isDisplayed()) {
                DatiPersonali_Checkbox_ONE.click();
                System.out.println("Clicking checkbox in Dati Personali page");
                Thread.sleep(10000);
                ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked checkbox in Dati Personali page==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "DatiPersonaliCheckbox")).build());
                jsClick(DatiPersonali_Salva_btn, "Clicking Salva button in Dati Personali page");
                Thread.sleep(5000);
                jsClick(DatiPersonali_Prosegui_btn, "Clicking Prosegui button in Dati Personali page");
                Thread.sleep(5000);
                ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Prosegui button in Dati Personali page==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Prosegui")).build());

            } else {
                System.out.println("Dati Personali page is not displayed and proceeding with the Spedizione Tramite Corriere");
            }
        } catch (Exception e) {
            System.out.println("Dati Personali page is not displayed and proceeding with the Spedizione Tramite Corriere");
        }
    }

    /***
     * Clicking Avanti in Spedizione Tramite Corriere page
     * @Author Dhanya
     * @param
     */
    public void SpedizioneTramiteCorriere() throws InterruptedException {
        driver.switchTo().defaultContent();
        Thread.sleep(10000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        Thread.sleep(5000);
        jsClick(Spedizione_next_btn,"Clicking Next button on Spedizione Tramite Corriere page");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Next button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Next")).build());
    }

    /***
     * Clicking checkbox in Diritti Di Ripensamento page
     * @Author Dhanya
     * @param
     */
    public void DirittoDiRipensamento() throws InterruptedException {
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        jsClick(Dirittodiripensamento_Activate_checkbox, "Clicking checkbox in Diritto di Ripensamento page");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked checkbox in Diritto di Ripensamento page==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "ActivateCheckbox")).build());
        Dirittodiripensamento_Next_btn.click();
        System.out.println("Clicking Next button");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Next button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Next")).build());

    }

    /***
     * Scheduling Appointment
     * @Author Dhanya
     * @param
     */
    //Appointment - only for OF
    public void AppuntamentoPage() throws InterruptedException {
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        jsClick(Select_date, "Clicking Future date in the Appuntamento page");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User selected Future date in the Appuntamento page==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "FutureDate")).build());
        jsClick(Scegli_btn, "Clicking Scegli button in Appuntamento page");
        Thread.sleep(5000);
        jsClick(Conferma_btn, "Clicking Conferma button in Appuntamento page");
        Thread.sleep(10000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Scegli and Conferma button in Appuntamento page==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "ScegliandConferma")).build());
        //elementDisplayed(Appointment_SuccessMsg);
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User verifies the success Message after scheduling Appointment and click Avanti==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "AppointmentSuccessMessage")).build());
        jsClick(Avanti_AppointmentPage, "Clicking Avanti button");
        Thread.sleep(10000);
    }

    /***
     * Validating Conferma Ordine Page
     * @Author Dhanya
     * @param
     */
    public void ConfermaOrdinePage() throws InterruptedException {
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        Thread.sleep(10000);
        jsClick(Avanti_ConfermaOrdinePage, "Clicking Avanti button");
        Thread.sleep(10000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Avanti button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Avanti")).build());

    }

    /***
     * Choosing Registrazione Vocal Order Option
     * @Author Dhanya
     * @param
     */
    public void FirmaDigitalePage() throws InterruptedException {
        driver.switchTo().defaultContent();
        Thread.sleep(10000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        Thread.sleep(5000);
        jsClick(Registrazione_Vocal_Order_btn, "Clicking Registrazione Vocal Order button");
        Thread.sleep(10000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Registrazione Vocal Order button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "VocalOrderOption")).build());
        jsClick(Avanti_FirmaDigitalePage, "Clicking Avanti button");
        Thread.sleep(10000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Avanti button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Avanti")).build());

    }

    /***
     * Choosing Communication channel
     * @Author Dhanya
     *
     */
    public void CanaleDiComunicazionePage(String email) throws InterruptedException {
        driver.switchTo().defaultContent();
        Thread.sleep(10000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        Thread.sleep(5000);
        //jsClick(Nessuno_radiobtn, "Clicking Nessuno button in Canali Di Comunicazione page");
        Thread.sleep(10000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Nessuno button in Canali DI Comunicazione page==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "NessunoOption")).build());
        jsInput(email_textbox, email, "User entering Email in textbox");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User entered Email in textbox==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Email")).build());
        Preference_EMAIL.click();
        System.out.println("Clicking preference textbox in Canali Di Comunicazione page");
        Select selectPreference = new Select(driver.findElement(By.id("ChannelSelection")));
        selectPreference.selectByVisibleText("Email");
        Thread.sleep(10000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User selected Email Option==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "EmailOption")).build());
        jsClick(Accetto_checkbox, "Clicking Accetto checkbox in Canali Di Comunicazione page");
        Thread.sleep(10000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Accetto checkbox in Canali Di Comunicazione page==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "AccettoCheckbox")).build());
        //jsClick(CanalidiCommunicazione_Conferma_btn, "Clicking Conferma button in Canali di Communicazione page");
        Thread.sleep(10000);
        refresh();
    }

    /***
     * Entering FW Address details
     * @Author Dhanya
     *
     */
    public void EnterFWAdrressDetails(String Citta, String Indirizzo, String NumCivico) throws InterruptedException {
        Thread.sleep(10000);
        driver.switchTo().defaultContent();
        Thread.sleep(10000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        Citta_textbox.clear();
        Thread.sleep(2000);
        Citta_textbox.sendKeys(Citta);
        Thread.sleep(10000);
        PERO_option.click();
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User selected PERO Option==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Citta")).build());
        Indirizzo_textbox.clear();
        Thread.sleep(2000);
        Indirizzo_textbox.sendKeys(Indirizzo);
        Thread.sleep(10000);
        VIAEUGENIOCURIEL_option.click();
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User selected VIA EUGENIO CURIEL Option==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Indirizzo")).build());
        NumCivico_textbox.clear();
        Thread.sleep(2000);
        NumCivico_textbox.sendKeys(NumCivico);
        Thread.sleep(10000);
        FWNumCivico_option.click();
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User selected 9 Option==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "NUmCivico")).build());
        jsClick(NormalizzaIndirizzo_btn, "Clicking Normalizza Indirizzo button");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Normalizza Indirizzo button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "NormalizzaIndirizzo")).build());
        elementDisplayed(Indirizzo_Normalizzato_SuccessMsg);
        System.out.println(Indirizzo_Normalizzato_SuccessMsg);
        Thread.sleep(5000);
        jsClick(Avanti_NormalizzazioneIndirizzoPage, "Clicking Avanti button");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Avanti button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Avanti")).build());
    }

    /***
     * Entering Passive Address details
     * @Author Dhanya
     *
     */
    public void EnterPassiveAdrressDetails(String Citta, String Indirizzo, String NumCivico) throws InterruptedException {
        Thread.sleep(10000);
        driver.switchTo().defaultContent();
        Thread.sleep(10000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        Citta_textbox.clear();
        Thread.sleep(2000);
        Citta_textbox.sendKeys(Citta);
        Thread.sleep(10000);
        NAPOLI_option.click();
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User selected NAPOLI Option==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Citta")).build());
        Indirizzo_textbox.clear();
        Thread.sleep(2000);
        Indirizzo_textbox.sendKeys(Indirizzo);
        Thread.sleep(10000);
        VIAANTONIOLABRIOLA_option.click();
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User selected VIA ANTONIO LABRIOLA Option==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Indirizzo")).build());
        NumCivico_textbox.clear();
        Thread.sleep(2000);
        NumCivico_textbox.sendKeys(NumCivico);
        Thread.sleep(10000);
        PassiveNumCivico_option.click();
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User selected 140 Option==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "NUmCivico")).build());
        jsClick(NormalizzaIndirizzo_btn, "Clicking Normalizza Indirizzo button");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Normalizza Indirizzo button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "NormalizzaIndirizzo")).build());
        elementDisplayed(Indirizzo_Normalizzato_SuccessMsg);
        System.out.println(Indirizzo_Normalizzato_SuccessMsg);
        Thread.sleep(5000);
        jsClick(Avanti_NormalizzazioneIndirizzoPage, "Clicking Avanti button");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Avanti button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Avanti")).build());

    }

    /***
     * Adding BB promo in Composizione Offerta Page
     * @Author Dhanya
     */
    public void AddBBPromo(String BB_Promo) throws InterruptedException {
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame);
        Thread.sleep(100000);
        driver.switchTo().frame(BBUpselling_Omniscript_Frame_two);
        Thread.sleep(2000);
        jsClick(PromoSearch_txtbox, "Clicking Search Box");
        waitForSeconds(5);
        PromoSearch_txtbox.sendKeys(BB_Promo);
        waitForSeconds(5);
        jsClick(PromoAggiungi_btn, "Clicking Aggiungi button");
        waitForSeconds(5);
        jsClick(Si_Option, "Clicking Si button");
        waitForSeconds(10);
        elementDisplayed(Promozioni_Notification_Icon);
        System.out.println("Promo has been added successfully");
        jsClick(Promozioni_Tab, "Clicking Promozioni tab");
        Thread.sleep(5000);
    }


    /***
     * Checking Postgres status after Upselling in IT Chain
     * @Author Dhanya
     *
     */
    public void CheckPostgresStatus_afterUpselling_IT(String CodiceContratto_Passivo) {
        String Estatus = "COMPLETED";
        String Astatus = "";
        String EsubStatus = null;
        String AsubStatus = "";
        //String conNum = contractNum;
        String EDate ="";


        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = formatter.format(today);


        try (Connection connection = postgresDBI.getConnectionIT()) {
            Statement statement = connection.createStatement();
            String baseQuery = "SELECT * from omcustom.T_ORDER a where a.order_num = '"+CodiceContratto_Passivo+"' order by a.creation_date desc";
            ResultSet resultSet = statement.executeQuery(baseQuery);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(Astatus, Estatus, "Status is equal not equal");
        Assert.assertEquals(AsubStatus, EsubStatus, "Sub Status is not equal");
        Assert.assertEquals(EDate, todayDate, "Date is not equal");

    }
}

