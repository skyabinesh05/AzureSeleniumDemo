package coeqa.pageobjects;

import coeqa.testbase.TestBase;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ExtentReport;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewSalesPage_Offerta extends TestBase {
    String pageName;

    @FindBy(xpath = "//a[@id='contrattoResidenzialeButton']")
    WebElement contratto_Residenziale_btn;

    @FindBy(xpath = "//a[text()='Nuovo Contratto Residenziale TV']")
    WebElement contratto_Residenziale_btn_TV;

    @FindBy(xpath = "//button[@class='btn btn-primary']")
    WebElement chudi_btn;

    @FindBy(xpath = "//font[contains(text(),'WIFI')]")
    WebElement WIFI_btn;

    @FindBy(xpath = "//button[contains(text(),'Verifica copertura')]")
    WebElement Verifica_coopertura;

    @FindBy(xpath = "//img[@id='BROADBAND_1_BB']")
    WebElement BB_img;

    @FindBy(xpath = "(//input[@value='S'])[1]")
    WebElement Sky_wifi_radio_btn;

    @FindBy(xpath = "(//button[@class='close']//b[contains(text(),'x')])[3]")
    WebElement Cancel_X_btn;

    @FindBy(xpath = "//img[@id='ns_skycalcio']")
    WebElement Sky_calcio_img;

    @FindBy(xpath = "//input[@ng-model='searchPromo']")
    WebElement Search_Promo;

    @FindBy(xpath = "//font[contains(text(),'1100575')]")
    WebElement Select_Promo;

    @FindBy(xpath = "//font[contains(text(),'7461')]")
    WebElement Select_Promo_BB;

    @FindBy(xpath = "//button[@class='btn rightArrow']")
    WebElement Right_arrow_btn;

    @FindBy(xpath = "//button[@ng-click='nessunCodiceVenditore()']")
    WebElement NessunCodice_Venditore_btn;

    @FindBy(xpath = "//button[contains(text(),'Conferma')]")
    WebElement Conferma_btn;

    @FindBy(xpath = "//input[@id='j_id0:anagraficaVCPQForm:anagraficaPagatoreNome']")
    WebElement Name_txt;

    @FindBy(xpath = "//input[@id='j_id0:anagraficaVCPQForm:anagraficaPagatoreCognome']")
    WebElement CogName_txt;

    @FindBy(xpath = "//a[@class='nonRicordoCF']")
    WebElement Fiscal_Code_Hyperlink;

    @FindBy(xpath = "//a[@class='nonRicordoIban']")
    WebElement IBAN_Hyperlink;

    @FindBy(xpath = "(//a[@class='nonRicordoIban'])[2]")
    WebElement IBAN_Hyperlink_BB;

    @FindBy(xpath = "//input[@sf-field='Email_BLB__c']")
    WebElement Email_fatturazione;

    @FindBy(xpath = "//button[@ng-click='avanti()']")
    WebElement Annula_txt;

    @FindBy(xpath = "//input[@sf-field='Localita_Nascita_2__c']")
    WebElement Localita_txt;

    @FindBy(xpath = "//input[@sf-field='Email__c']")
    WebElement Email_txt;

    @FindBy(xpath = "//input[@ng-model='anagrafica.pagatore.confirmEmailLead']")
    WebElement cnfrm_Email_txt;

    @FindBy(xpath = "//input[@ng-model='anagrafica.pagatore.telefono']")
    WebElement cellulare_txt;

    @FindBy(xpath = "//input[@sf-field='Numero_Documento__c']")
    WebElement Numero_Documento_c_txt;

    @FindBy(xpath = "//input[@sf-field='Data_Rilascio__c']")
    WebElement Data_Rilascio_cal;

    @FindBy(xpath = "//input[@sf-field='Data_Scadenza__c']")
    WebElement Data_scadenza_cal;

    @FindBy(xpath = "(//div[@class='radioQuestion']//table//tbody//tr//td//label[contains(text(),' Si')])[1]")
    WebElement Novita_e_offerte_btn;

    @FindBy(xpath = "(//div[@class='radioQuestion']//table//tbody//tr//td//label[contains(text(),' Si')])[2]")
    WebElement Offerte_e_contenut;

    @FindBy(xpath = "(//div[@class='radioQuestion']//table//tbody//tr//td//label[contains(text(),' Si')])[3]")
    WebElement  partner_di_Sky;

    @FindBy(xpath = "//div[@class='modal-content']//label[contains(text(),'MILANO,MI')]")
    WebElement Milano_txt;

    @FindBy(xpath = "//div[@class='modal-content']//label[contains(text(),'08332')]")
    WebElement ABI_txt;

    @FindBy(xpath = "//div[@class='modal-content']//label[contains(text(),'24400')]")
    WebElement CAB_txt;

    @FindBy(xpath = "//input[@id='confirmContBtn']")
    WebElement Conferma_btn_RP;

    @FindBy(xpath = "(//input[@value='Ottieni'])[1]")
    WebElement ottieni_txt;

    @FindBy(xpath = "(//input[@value='Ottieni'])[3]")
    WebElement ottieni_txt_BB;

    @FindBy(xpath = "//input[@value='Stampa da sistema']")
    WebElement Stampa_da_sistema_btn;


//    Added by pavithra

    //Common for TV and BB
    @FindBy(xpath = "//a[normalize-space()='MILANO,MI']")
    WebElement txtMilano;

    //Only BB execution
    @FindBy(xpath = "(//a[normalize-space()='MILANO,MI'])[2]")
    WebElement txtMilano_BB;

    @FindBy(xpath = "(//a[normalize-space()='MILANO,MI'])[2]")
    WebElement txtMilanoInIndirizzoPage;

    @FindBy(xpath = "(//a[normalize-space()='MILANO,MI'])[3]")
    WebElement txtMilanoInIndirizzoPage_BB;

    @FindBy(xpath = "(//a[normalize-space()='Italia'])")
    WebElement txtitalia_bb;

    @FindBy(xpath = "//a[normalize-space()='VIA ABANO']")
    WebElement txtAbanoInIndirizzoPage;

    @FindBy(xpath = "(//a[normalize-space()='VIA ABANO'])[2]")
    WebElement txtAbanoInIndirizzoPage_BB;

    @FindBy(xpath = "//a[contains(@class, 'ui-corner-all') and normalize-space()='14']")
    WebElement txtCivicoInIndirizzoPage;

    @FindBy(xpath = "(//a[contains(@class, 'ui-corner-all') and normalize-space()='14'])[2]")
    WebElement txtCivicoInIndirizzoPage_BB;

    @FindBy(xpath = "//a[normalize-space()='08332']")
    WebElement txtAbiInPagamentoPage;

    @FindBy(xpath = "//a[normalize-space()='24400']")
    WebElement txtCabInPagamentoPage;

    @FindBy(xpath = "//input[@id='anagraficaPagatoreCittadinanza']")
    WebElement Cittadinanza_txt;

    /***
     * Richiesta Voucher - Only in IT Chain
     * @Author Dhanya
     */
    @FindBy(xpath = "//label[text()='Richiesta Voucher']")
    WebElement RichiestaVoucher_Field;

    /***
     * Area CD Address - BB
     * @Author Dhanya
     */
    @FindBy(xpath = "//a[normalize-space()='MOZZATE,CO']")
    WebElement txtMozzate;

    @FindBy(xpath = "//a[normalize-space()='VIA DON LUIGI STURZO']")
    WebElement txtDonLuigiSturzoInIndirizzoPage;

    @FindBy(xpath = "//a[contains(@class, 'ui-corner-all') and normalize-space()='25']")
    WebElement txtCivicoAreaCDInIndirizzoPage;

    @FindBy(xpath = "//img[@id='ns_flatnazmobbb']")
    WebElement Voice_Unlimited_btn;

    @FindBy(xpath = "//a[normalize-space()='ROMA,RM']")
    WebElement txtRoma_BB;

    @FindBy(xpath = "//button[text()='Chiudi']")
    WebElement Chiudi_btn;

    public NewSalesPage_Offerta(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        pageName = this.getClass().getSimpleName();
    }

    public void Nuovo_Contratto_Residenziale() throws InterruptedException {
        waitForPageToLoad();
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Nuovo Contratto Residenziale']")));
        jsClick(contratto_Residenziale_btn,"User clicking on contratto_Residenziale_btn");
        Thread.sleep(1500);
        ExtentReport.getExtentTest().log(Status.PASS, "<<========Nuovo_Contratto_Residenziale Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Nuovo_Contratto_Residenziale")).build());
        jsClick(contratto_Residenziale_btn_TV,"User clicking on contratto_Residenziale_btn_TV");
        Thread.sleep(1500);
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='btn btn-primary']")));
        jsClick(chudi_btn,"User clicking on Chudi_btn");
        waitForSeconds(10);
    }

    //This is for BB
    public void check_coverage_BB() {
        waitForSeconds(20);
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//font[contains(text(),'WIFI')]")));
        jsClick(WIFI_btn, "User clicking on WIFI_btn Button");
        //MILANO
        driver.findElement(By.xpath("//input[@id='bundleblbLocalita']")).sendKeys("MILANO");
        waitForSeconds(2);
        waitForPresenceOfElement(By.xpath("//a[normalize-space()='MILANO,MI']"));
        tapOnPointInsideElement(txtMilano, "centre");
        waitForSeconds(10);
        mouseOver(txtMilano);
        clickElement(txtMilano);
        waitForSeconds(10);
        //VIA ABANO
        driver.findElement(By.xpath("//input[@id='bundleblbIndirizzo']")).sendKeys("VIA ABANO");
        waitForSeconds(2);
        waitForPresenceOfElement(By.xpath("//a[normalize-space()='VIA ABANO']"));
        tapOnPointInsideElement(txtAbanoInIndirizzoPage, "centre");
        waitForSeconds(5);
        mouseOver(txtAbanoInIndirizzoPage);
        clickElement(txtAbanoInIndirizzoPage);
        waitForSeconds(5);
        //CIVICO
        driver.findElement(By.xpath("//input[@id='bundleblbNumeroCivico']")).sendKeys("14");
        waitForSeconds(2);
        waitForPresenceOfElement(By.xpath("//a[contains(@class, 'ui-corner-all') and normalize-space()='14']"));
        tapOnPointInsideElement(txtCivicoInIndirizzoPage, "centre");
        waitForSeconds(3);
        mouseOver(txtCivicoInIndirizzoPage);
        clickElement(txtCivicoInIndirizzoPage);
        waitForSeconds(5);

        jsClick(Verifica_coopertura, "User clicking on WIFI_btn Button");
        waitForSeconds(5);

        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@id='BROADBAND_1_BB']")));
        jsClick(BB_img, "User clicking on WIFI_btn Button");
        waitForSeconds(5);
        jsClick(Sky_wifi_radio_btn, "User clicking on WIFI_btn Button");
        waitForSeconds(5);
        jsClick(Cancel_X_btn, "User clicking on WIFI_btn Button");
        waitForSeconds(5);
//        jsClick(Right_arrow_btn,"User clicking on Right Arrow button");

    }

    /***
     *  Enter Area CD Address in Check Coverage
     * @Author Dhanya
     */
    public void check_coverage_BB_AreaCD() {
        waitForSeconds(20);
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//font[contains(text(),'WIFI')]")));
        jsClick(WIFI_btn, "User clicking on WIFI_btn Button");
        waitForSeconds(10);
        //MOZZATE
        driver.findElement(By.xpath("//input[@id='bundleblbLocalita']")).sendKeys("MOZZATE");
        waitForSeconds(2);
        waitForPresenceOfElement(By.xpath("//a[normalize-space()='MOZZATE,CO']"));
        tapOnPointInsideElement(txtMozzate, "centre");
        waitForSeconds(10);
        mouseOver(txtMozzate);
        clickElement(txtMozzate);
        waitForSeconds(10);
        //VIA DON LUIGI STURZO
        driver.findElement(By.xpath("//input[@id='bundleblbIndirizzo']")).sendKeys("VIA DON LUIGI STURZO");
        waitForSeconds(2);
        waitForPresenceOfElement(By.xpath("//a[normalize-space()='VIA DON LUIGI STURZO']"));
        tapOnPointInsideElement(txtDonLuigiSturzoInIndirizzoPage, "centre");
        waitForSeconds(5);
        mouseOver(txtDonLuigiSturzoInIndirizzoPage);
        clickElement(txtDonLuigiSturzoInIndirizzoPage);
        waitForSeconds(5);
        //CIVICO
        driver.findElement(By.xpath("//input[@id='bundleblbNumeroCivico']")).sendKeys("25");
        waitForSeconds(2);
        waitForPresenceOfElement(By.xpath("//a[contains(@class, 'ui-corner-all') and normalize-space()='25']"));
        tapOnPointInsideElement(txtCivicoAreaCDInIndirizzoPage, "centre");
        waitForSeconds(3);
        mouseOver(txtCivicoAreaCDInIndirizzoPage);
        clickElement(txtCivicoAreaCDInIndirizzoPage);
        waitForSeconds(5);

        jsClick(Verifica_coopertura, "User clicking on WIFI_btn Button");
        waitForSeconds(5);

        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@id='BROADBAND_1_BB']")));
        jsClick(BB_img, "User clicking on WIFI_btn Button");
        waitForSeconds(5);
        jsClick(Sky_wifi_radio_btn, "User clicking on WIFI_btn Button");
        waitForSeconds(5);
        jsClick(Cancel_X_btn, "User clicking on WIFI_btn Button");
        waitForSeconds(5);
//        jsClick(Right_arrow_btn,"User clicking on Right Arrow button");

    }
    public void Packages_and_Promos(String Promo) {
        waitForPageToLoad();
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@ng-model='searchPromo']")));
        jsInput(Search_Promo, Promo, "Promo Cerca Text box");
        jsClick(Select_Promo,"User clicking on Selected Promo");
        ExtentReport.getExtentTest().log(Status.PASS, "<<========Packages_and_Promos Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Packages_and_Promos")).build());
        jsClick(Right_arrow_btn,"User clicking on Right Arrow button");
    }

    public void Packages_and_Promos_BB(String Promo) {
        waitForPageToLoad();
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@ng-model='searchPromo']")));
//        jsInput(Search_Promo, Promo, "Promo Cerca Text box");
        inputText(Search_Promo,Promo,"Promo Cerca Text box");
        waitForSeconds(15);
        jsClick(Select_Promo_BB,"User clicking on Selected Promo");
        waitForSeconds(15);
        ExtentReport.getExtentTest().log(Status.PASS, "<<========Packages_and_Promos Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Packages_and_Promos")).build());
        jsClick(Right_arrow_btn,"User clicking on Right Arrow button");
    }


    /***
     * Area CD - Choose Promo and Click Voice Unlimited
     * @Author Dhanya
     */
    public void Packages_and_Promos_AreaCD(String Promo) {
        waitForPageToLoad();
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@ng-model='searchPromo']")));
        jsInput(Search_Promo, Promo, "Promo Cerca Text box");
        jsClick(Select_Promo_BB,"User clicking on Selected Promo");
        ExtentReport.getExtentTest().log(Status.PASS, "<<========Packages_and_Promos Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Packages_and_Promos")).build());
        jsClick(Voice_Unlimited_btn,"User clicking on Right Arrow button");
        waitForSeconds(5);
        jsClick(Right_arrow_btn,"User clicking on Right Arrow button");
    }

    public void Inserisci_i_Codici_Venditori() {
        waitForPageToLoad();
        waitForSeconds(15);
        jsClick(NessunCodice_Venditore_btn,"User clicking on NessunCodice Venditore btn on Pop-up");
        ExtentReport.getExtentTest().log(Status.PASS, "<<========Inserisci_i_Codici_Venditori Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Inserisci_i_Codici_Venditori")).build());
//        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Conferma')]")));
//        jsClick(Conferma_btn,"User clicking on Conferma Button");
    }

    //Anagrafica
    public void Anagrafica_intestatario_contratto(String F_Nome,String Cognome,String Data_di_nascita) throws InterruptedException, ParseException {
        waitForPageToLoad();
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@id='anagraficaPagatoreTipoAnagrafica']")));
        Select Tipo_Anagrafica_drpdwn = new Select(driver.findElement(By.xpath("//select[@id='anagraficaPagatoreTipoAnagrafica']")));
        Tipo_Anagrafica_drpdwn.selectByVisibleText("PERSONA FISICA");
//        WebElement F_Nome = driver.findElement(By.xpath("//input[@id='j_id0:anagraficaVCPQForm:anagraficaPagatoreNome']"));
//        input(Name_txt,Nome,"FirstName");
        inputText(Name_txt,F_Nome,"First_Name");
        waitForSeconds(10);
//        jsInput(Name_txt, Nome, "Nome was inserted in Text box succesfully");
//        WebElement C_Nome = driver.findElement(By.xpath("//input[@id='j_id0:anagraficaVCPQForm:anagraficaPagatoreCognome']"));
//        input(CogName_txt,Cognome,"SecondName");
        inputText(CogName_txt,Cognome,"Cog_Name");
        waitForSeconds(10);
//        jsInput(CogName_txt, Cognome, "Cog_Nome was inserted in Text box succesfully");
        waitForSeconds(10);
        jsClick(Fiscal_Code_Hyperlink,"User clicking on * - Non ricordi il codice fiscale? Button");
        waitForSeconds(10);
        driver.findElement(By.xpath("//input[@sf-field='Data_Nascita_2__c']")).sendKeys("07/09/1998");
        waitForSeconds(20);
        Select Sesso_drpdwn = new Select(driver.findElement(By.xpath("//select[@id='j_id0:anagraficaVCPQForm:anagraficaPagatoreSesso']")));
        Sesso_drpdwn.selectByVisibleText("M");
        waitForSeconds(10);
        Select Paese_drpdwn = new Select(driver.findElement(By.xpath("//select[@id='j_id0:anagraficaVCPQForm:anagraficaPagatorePaeseDiNascita']")));
        Paese_drpdwn.selectByVisibleText("ITALIA");
        waitForSeconds(10);
    }

    public void Anagrafica_Localita(String Località_di_nascita) {
        driver.findElement(By.xpath("//input[@sf-field='Localita_Nascita_2__c']")).sendKeys("MILANO");
        waitForSeconds(5);

//        Added by pavithra
        waitForSeconds(2);
        waitForPresenceOfElement(By.xpath("(//a[normalize-space()='MILANO,MI'])"));
        tapOnPointInsideElement(txtMilano, "centre");
        waitForSeconds(10);
        mouseOver(txtMilano);
        clickElement(txtMilano);
        waitForSeconds(10);

        jsClick(ottieni_txt,"User clicking on ottieni_txt Button");
        waitForSeconds(20);

    }

    public void Anagrafica_Localita_BB(String testData) {
        driver.findElement(By.xpath("//input[@sf-field='Localita_Nascita_2__c']")).sendKeys("MILANO");
        waitForSeconds(5);

        waitForSeconds(2);
        waitForPresenceOfElement(By.xpath("(//a[normalize-space()='MILANO,MI'])[2]"));
        tapOnPointInsideElement(txtMilano_BB, "centre");
        waitForSeconds(10);
        mouseOver(txtMilano_BB);
        clickElement(txtMilano_BB);
        waitForSeconds(10);

        jsClick(ottieni_txt,"User clicking on ottieni_txt Button");
        waitForSeconds(20);

        inputText(Cittadinanza_txt,"ITALIA","Citizenship");
        waitForSeconds(15);
        waitForPresenceOfElement(By.xpath( "(//a[normalize-space()='Italia'])"));
        tapOnPointInsideElement(txtitalia_bb, "centre");
        waitForSeconds(10);
        mouseOver(txtitalia_bb);
        clickElement(txtitalia_bb);
        waitForSeconds(10);


    }

    /***
     * Area CD - Enter ROMA in Localita di Nascita
     * @Author Dhanya
     */
    public void Anagrafica_Localita_BB_AreaCD(String Località_di_nascita) {
        driver.findElement(By.xpath("//input[@sf-field='Localita_Nascita_2__c']")).sendKeys("ROMA");
        waitForSeconds(5);
        waitForSeconds(2);
        waitForPresenceOfElement(By.xpath("//a[normalize-space()='ROMA,RM']"));
        tapOnPointInsideElement(txtRoma_BB, "centre");
        waitForSeconds(10);
        mouseOver(txtRoma_BB);
        clickElement(txtRoma_BB);
        waitForSeconds(10);

        jsClick(ottieni_txt, "User clicking on ottieni_txt Button");
        waitForSeconds(20);

        inputText(Cittadinanza_txt, "ITALIA", "Citizenship");
        waitForSeconds(15);
        waitForPresenceOfElement(By.xpath("(//a[normalize-space()='Italia'])"));
        tapOnPointInsideElement(txtitalia_bb, "centre");
        waitForSeconds(10);
        mouseOver(txtitalia_bb);
        clickElement(txtitalia_bb);
        waitForSeconds(10);
        try {
            if (RichiestaVoucher_Field.isDisplayed()) {
                waitForPageToLoad();
                expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@id='richiestaPromoAggiuntiva']")));
                Select Tipo_Anagrafica_drpdwn = new Select(driver.findElement(By.xpath("//select[@id='richiestaPromoAggiuntiva']")));
                Tipo_Anagrafica_drpdwn.selectByVisibleText("No Voucher");
            } else {
                System.out.println("Proceed with entering Email");
            }
        } catch (Exception e) {
            System.out.println("Proceed with entering Email");
        }

    }

    //Recapiti intestatario contratto
    public void Recapiti_intestatario_contratto(String email, String cnfrm_email, String cellulare) throws InterruptedException {
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@sf-field='Email__c']")));
        inputText(Email_txt, email, "Enter email in Text box");
        waitForSeconds(10);
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@ng-model='anagrafica.pagatore.confirmEmailLead']")));
        inputText(cnfrm_Email_txt, cnfrm_email, "Enter confrim email in Text box");
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@ng-model='anagrafica.pagatore.telefono']")));
        inputText(cellulare_txt,cellulare,"Italy phone number");
        waitForSeconds(10);
    }
    public void Recapiti_intestatario_contratto_BB(String email, String cellulare) throws InterruptedException {
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@sf-field='Email__c']")));
        inputText(Email_txt,email,"Sky Email ID");
        waitForSeconds(10);
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@ng-model='anagrafica.pagatore.telefono']")));
        inputText(cellulare_txt,cellulare,"Itlay phone number");
        waitForSeconds(10);

    }

    public void Documento_intestatario_contratto(String Numero_Documento_txt, String Data_Rilascio_txt, String Data_scadenza) throws InterruptedException, ParseException {
        Select Tipo_documento_dp = new Select(driver.findElement(By.xpath("//select[@ng-disabled=' disableTipoDocumentoPagatore']")));
        Tipo_documento_dp.selectByVisibleText("CARTA D'IDENTITA'");
        driver.findElement(By.xpath("//input[@sf-field='Numero_Documento__c']")).sendKeys(Numero_Documento_txt);
        waitForSeconds(10);
        Select Rilasciato_da_dp = new Select(driver.findElement(By.xpath("//select[@id='anagraficaPagatoreRilasciatoDa']")));
        Rilasciato_da_dp.selectByVisibleText("COMUNE");
        waitForSeconds(10);
        WebElement calenderElement_DR = driver.findElement(By.xpath("//span[@class='dateInput dateOnlyInput']//input[@ng-model='anagrafica.pagatore.dataRilascio']"));

        this.clickOnCalender(calenderElement_DR);
        //call selectYear() method to select the year
        this.selectYear("2019");
        //call selectMonth() method to select the year
        this.selectMonth("May");
        //call selectDay() method to select the day
        this.selectDate("5");
        waitForSeconds(15);
        WebElement calenderElement = driver.findElement(By.xpath("//span[@class='dateInput dateOnlyInput']//input[@ng-model='anagrafica.pagatore.dataScadenza']"));

        this.clickOnCalender(calenderElement);
        //call selectYear() method to select the year
        this.selectYear("2027");
        //call selectMonth() method to select the year
        this.selectMonth("May");
        //call selectDay() method to select the day
        this.selectDate("5");
        waitForSeconds(15);
        ExtentReport.getExtentTest().log(Status.PASS, "<<========Anagrafica Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Anagrafica")).build());
        driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
        waitForSeconds(20);
        driver.findElement(By.xpath("//a[@ng-click=\"proseguiSurvey('avanti')\"]")).click();
        waitForSeconds(10);
        jsClick(Novita_e_offerte_btn,"User clicking SI option on Novità e offerte radio button");
        waitForSeconds(10);
        jsClick(Offerte_e_contenut,"User clicking SI option on Offerte e contenuti personalizzati radio button");
        waitForSeconds(10);
        jsClick(partner_di_Sky,"User clicking SI option on Novità e offerte dai partner di Sky radio button");
        waitForSeconds(10);
        ExtentReport.getExtentTest().log(Status.PASS, "<<========Documento_intestatario_contracto Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Documento_intestatario_contracto")).build());
        driver.findElement(By.xpath("//button[@class='btn rightArrow ng-scope']")).click();
        waitForSeconds(25);
    }


    public void Indirizzo_Pagatore() {
        driver.findElement(By.xpath("//input[@sf-field='Localita_Pagatore__c']")).sendKeys("MILANO");
        waitForSeconds(10);

//        Added by Pavithra
        waitForSeconds(2);
        waitForPresenceOfElement(By.xpath("(//a[normalize-space()='MILANO,MI'])[2]"));
        tapOnPointInsideElement(txtMilanoInIndirizzoPage, "centre");
        waitForSeconds(5);
        mouseOver(txtMilanoInIndirizzoPage);
        clickElement(txtMilanoInIndirizzoPage);
        waitForSeconds(5);


//        //AutoIT Implementation neede for MILANO, MI
//        String Indrizzo_Localita = "C:\\Users\\ita694\\Downloads\\Code Merge\\coeqa-bdd-Final (1)\\coeqa-bdd\\src\\test\\resources\\Autoit\\IndrizzoLocalitaa.exe";
//        try {
//            Process process = Runtime.getRuntime().exec(Indrizzo_Localita);
//            int exitCode = process.waitFor();
//            System.out.println("Process exited with code: " + exitCode);
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        waitForSeconds(10);
        driver.findElement(By.xpath("//input[@ng-change=' resetCheckIndirizzoPagatore() ']")).sendKeys("VIA ABANO");
        waitForSeconds(10);


//        Added by Pavithra

        waitForPresenceOfElement(By.xpath("(//a[normalize-space()='VIA ABANO'])"));
        tapOnPointInsideElement(txtAbanoInIndirizzoPage, "centre");
        waitForSeconds(5);
        mouseOver(txtAbanoInIndirizzoPage);
        clickElement(txtAbanoInIndirizzoPage);
        waitForSeconds(5);


//        //AutoIT Implementation need for VIA ABANO
//        String Indrizzo_via = "C:\\Users\\ita694\\Downloads\\Code Merge\\coeqa-bdd-Final (1)\\coeqa-bdd\\src\\test\\resources\\Autoit\\Indrizzovia.exe";
//        try {
//            Process process = Runtime.getRuntime().exec(Indrizzo_via);
//            int exitCode = process.waitFor();
//            System.out.println("Process exited with code: " + exitCode);
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }

//        waitForSeconds(10);
        driver.findElement(By.xpath("//input[@sf-field='Numero_Civico_Pagatore__c']")).sendKeys("14");
        waitForSeconds(10);


//        Added by Pavithra
        waitForPresenceOfElement(By.xpath("(//a[contains(@class, 'ui-corner-all') and normalize-space()='14'])"));
        tapOnPointInsideElement(txtCivicoInIndirizzoPage, "centre");
        waitForSeconds(3);
        mouseOver(txtCivicoInIndirizzoPage);
        clickElement(txtCivicoInIndirizzoPage);
        waitForSeconds(5);


//        //AutoIT Implementation neede for Civico Num 14
//        String Indrizzo_Civico = "C:\\Users\\ita694\\Downloads\\Code Merge\\coeqa-bdd-Final (1)\\coeqa-bdd\\src\\test\\resources\\Autoit\\IndrizzoCivico.exe";
//        try {
//            Process process = Runtime.getRuntime().exec(Indrizzo_Civico);
//            int exitCode = process.waitFor();
//            System.out.println("Process exited with code: " + exitCode);
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        waitForSeconds(10);
        driver.findElement(By.xpath("//input[@sf-field='Citofono__c']")).sendKeys("QWER12345");
        waitForSeconds(10);
        ExtentReport.getExtentTest().log(Status.PASS, "<<========Indrizzo_Pagatore Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Indrizzo_Pagatore")).build());
        driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
        waitForSeconds(15);
        //Right Arrow button in Materialli Page
        ExtentReport.getExtentTest().log(Status.PASS, "<<========Materialli Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Materialli")).build());
        driver.findElement(By.xpath("//button[@class='btn rightArrow']")).click();
        waitForSeconds(15);

        try {
            handlePopup();
        }catch (Exception e){
            System.out.println("No pop up were Dsiplayed");
        }

        //Avivo Modalita Firma Page
        jsClick(Stampa_da_sistema_btn,"User clicking Stampa da sistema option on Firma Page");
        waitForSeconds(10);

        ExtentReport.getExtentTest().log(Status.PASS, "<<========Indrizzo_Stampa Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Indrizzo_Stampa")).build());

        driver.findElement(By.xpath("//button[@class='btn rightArrow']")).click();
        waitForSeconds(15);
    }

    //BB Execution Indrizzo Page
    public void Indirizzo_Pagatore_BB() {
        driver.findElement(By.xpath("//input[@sf-field='Citofono__c']")).sendKeys("QWER12345");
        waitForSeconds(20);
        driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
        waitForSeconds(20);
        driver.findElement(By.xpath("//input[@label='NessunaLineaAttiva']")).click();
        waitForSeconds(10);
        WebElement confermaButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Conferma')]")));
        confermaButton.click();
        ExtentReport.getExtentTest().log(Status.PASS, "<<========Indrizzo_Pagatore Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Indrizzo_Pagatore")).build());
//        driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
        waitForSeconds(15);
        //Right Arrow button in Materialli Page
//        ExtentReport.getExtentTest().log(Status.PASS, "<<========Materialli Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Materialli")).build());
//        driver.findElement(By.xpath("//button[@class='btn rightArrow']")).click();
//        waitForSeconds(15);

        //Avivo Modalita Firma Page
        jsClick(Stampa_da_sistema_btn,"User clicking Stampa da sistema option on Firma Page");
        waitForSeconds(10);

        ExtentReport.getExtentTest().log(Status.PASS, "<<========Indrizzo_Stampa Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Indrizzo_Stampa")).build());

        driver.findElement(By.xpath("//button[@class='btn rightArrow']")).click();
        waitForSeconds(15);


    }

    /***
     * Area CD - Enter CIT in Citofono
     * @Author Dhanya
     */
    public void Indirizzo_Pagatore_BB_AreaCD(String Citofono) {
        driver.findElement(By.xpath("//input[@sf-field='Citofono__c']")).sendKeys("CIT");
        waitForSeconds(20);
        driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
        waitForSeconds(20);
        driver.findElement(By.xpath("//input[@label='NessunaLineaAttiva']")).click();
        waitForSeconds(10);
        WebElement confermaButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Conferma')]")));
        confermaButton.click();
        ExtentReport.getExtentTest().log(Status.PASS, "<<========Indrizzo_Pagatore Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Indrizzo_Pagatore")).build());
//        driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
        waitForSeconds(15);
        //Right Arrow button in Materialli Page
//        ExtentReport.getExtentTest().log(Status.PASS, "<<========Materialli Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Materialli")).build());
//        driver.findElement(By.xpath("//button[@class='btn rightArrow']")).click();
//        waitForSeconds(15);

        //Avivo Modalita Firma Page
        jsClick(Stampa_da_sistema_btn,"User clicking Stampa da sistema option on Firma Page");
        waitForSeconds(10);

        ExtentReport.getExtentTest().log(Status.PASS, "<<========Indrizzo_Stampa Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Indrizzo_Stampa")).build());

        driver.findElement(By.xpath("//button[@class='btn rightArrow']")).click();
        waitForSeconds(15);

    }

    public void Pagamento() {
        Select Tipo_Pagamento_dp = new Select(driver.findElement(By.xpath("//select[@id='pagamentoTipoMop']")));
        Tipo_Pagamento_dp.selectByVisibleText("DOMICILIAZIONE BANCARIA");
        waitForSeconds(10);
        Select Frequenza_di_pagamento_dp = new Select(driver.findElement(By.xpath("//select[@id='pagamentoPeriodo']")));
        Frequenza_di_pagamento_dp.selectByVisibleText("ANNUALE");
        waitForSeconds(10);
        Select Tipo_DECA_pagamento_dp = new Select(driver.findElement(By.xpath("//select[@id='tipoMopDECA']")));
        Tipo_DECA_pagamento_dp.selectByVisibleText("NO DECA");
        waitForSeconds(10);
        jsClick(IBAN_Hyperlink,"User clicking on * - Non ricordi il codice fiscale? Button");
        waitForSeconds(10);
        ExtentReport.getExtentTest().log(Status.PASS, "<<========Pagamento Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Pagamento")).build());
    }

    public void Pagamento_BB() {
        Select Tipo_Pagamento_dp = new Select(driver.findElement(By.xpath("//select[@id='pagamentoTipoMopBLB']")));
        Tipo_Pagamento_dp.selectByVisibleText("DOMICILIAZIONE BANCARIA");
        waitForSeconds(10);
        Select Frequenza_di_pagamento_dp = new Select(driver.findElement(By.xpath("//select[@id='pagamentoPeriodoBLB']")));
        Frequenza_di_pagamento_dp.selectByVisibleText("MENSILE");
        waitForSeconds(10);
        inputText(Email_fatturazione,"test_email@skytv.it","Pagamento Email ID");
        waitForSeconds(10);
        jsClick(IBAN_Hyperlink_BB,"User clicking on * - Non ricordi il codice fiscale? Button");
        waitForSeconds(10);
        ExtentReport.getExtentTest().log(Status.PASS, "<<========Pagamento Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Pagamento")).build());
    }

    public void IBAN_Pagamento_details() {
        //ABI Textbox value
        driver.findElement(By.xpath("//input[@sf-field='Abi__c']")).sendKeys("08332");
        waitForSeconds(10);

//        Added by Pavithra
        waitForPresenceOfElement(By.xpath("//a[normalize-space()='08332']"));
        tapOnPointInsideElement(txtAbiInPagamentoPage, "centre");
        waitForSeconds(5);
        mouseOver(txtAbiInPagamentoPage);
        clickElement(txtAbiInPagamentoPage);
        waitForSeconds(5);

        //CAB Textbox value
        driver.findElement(By.xpath("//input[@name='j_id0:pagamentoVCPQForm:pagamentoCabMasked']")).sendKeys("24400");
        waitForSeconds(10);


//        Added by Pavithra
        waitForPresenceOfElement(By.xpath("//a[normalize-space()='24400']"));
        tapOnPointInsideElement(txtCabInPagamentoPage, "centre");
        waitForSeconds(5);
        mouseOver(txtCabInPagamentoPage);
        clickElement(txtCabInPagamentoPage);
        waitForSeconds(5);

        waitForSeconds(8);

        driver.findElement(By.xpath("//input[@sf-field='Numero_Conto__c']")).sendKeys("000987654320");
        waitForSeconds(6);
        jsClick(ottieni_txt, "User clicking on ottieni_txt Button");
        waitForSeconds(6);
        driver.findElement(By.xpath("//button[contains(text(),'Chiudi')]")).click();
        waitForSeconds(6);
        jsClick(ottieni_txt, "User clicking on ottieni_txt Button");
        waitForSeconds(10);
        ExtentReport.getExtentTest().log(Status.PASS, "<<========IBAN_Pagamento Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "IBAN_Pagamento")).build());
        driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
        waitForSeconds(50);

        WebElement AppointmentPop_upNO = driver.findElement(By.xpath("//button[contains(text(),'No')]"));
        if(AppointmentPop_upNO.isDisplayed()){
            AppointmentPop_upNO.click();
            waitForSeconds(20);
//            driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
            System.out.println("No Need Appointment so clicking NO option");
        }else{
            System.out.println("Appointment pop up not visible, so proceed for TV execution");
        }

        try {
            WebElement confermaButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Conferma')]")));
            if (confermaButton.isDisplayed()) {
                confermaButton.click();
                System.out.println("Conferma button was present and clicked.");
                driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
                waitForSeconds(100);
            }else{
                driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
                waitForSeconds(100);
            }
        } catch (Exception e) {
            System.out.println("Conferma button not found, proceeding without clicking.");
        }

        driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();

        scrollTop();

        //Replico Page for Final Conferma button
        WebDriverWait wait_2 = new WebDriverWait(driver, 50);
        wait_2.until(ExpectedConditions.elementToBeClickable(Conferma_btn_RP));
        driver.findElement(By.xpath("//input[@id='confirmContBtn']")).click();
        waitForSeconds(30);
        ExtentReport.getExtentTest().log(Status.PASS, "<<========Contracted Created via NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Contract Number")).build());
    }

    public void IBAN_Pagamento_details_BB() {
        //ABI Textbox value
        driver.findElement(By.xpath("//input[@sf-field='Abi_BLB__c']")).sendKeys("08332");
        waitForSeconds(10);

//        Added by Pavithra
        waitForPresenceOfElement(By.xpath("//a[normalize-space()='08332']"));
        tapOnPointInsideElement(txtAbiInPagamentoPage, "centre");
        waitForSeconds(5);
        mouseOver(txtAbiInPagamentoPage);
        clickElement(txtAbiInPagamentoPage);
        waitForSeconds(5);

        //CAB Textbox value
        driver.findElement(By.xpath("//input[@id='j_id0:pagamentoVCPQForm:pagamentoCabMaskedBLB']")).sendKeys("24400");
        waitForSeconds(10);


//        Added by Pavithra
        waitForPresenceOfElement(By.xpath("//a[normalize-space()='24400']"));
        tapOnPointInsideElement(txtCabInPagamentoPage, "centre");
        waitForSeconds(5);
        mouseOver(txtCabInPagamentoPage);
        clickElement(txtCabInPagamentoPage);
        waitForSeconds(5);

        waitForSeconds(8);

        driver.findElement(By.xpath("//input[@sf-field='Numero_Conto_BLB__c']")).sendKeys("000987654320");
        waitForSeconds(6);
        jsClick(ottieni_txt_BB, "User clicking on ottieni_txt Button");
        waitForSeconds(6);
        driver.findElement(By.xpath("//button[contains(text(),'Chiudi')]")).click();
        waitForSeconds(6);
        jsClick(ottieni_txt_BB, "User clicking on ottieni_txt Button");
        waitForSeconds(10);
        ExtentReport.getExtentTest().log(Status.PASS, "<<========IBAN_Pagamento Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "IBAN_Pagamento")).build());
        driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
        waitForSeconds(100);

        WebElement AppointmentPop_upNO = driver.findElement(By.xpath("//button[contains(text(),'No')]"));
        if(AppointmentPop_upNO.isDisplayed()){
            AppointmentPop_upNO.click();
            waitForSeconds(20);
//            driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
            System.out.println("No Need Appointment so clicking NO option");
        }else{
            System.out.println("Appointment pop up not visible, so proceed for TV execution");
        }

        try {
            WebElement confermaButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Conferma')]")));
            if (confermaButton.isDisplayed()) {
                confermaButton.click();
                System.out.println("Conferma button was present and clicked.");
                driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
                waitForSeconds(30);
            }else{
                driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
                waitForSeconds(30);
            }
        } catch (Exception e) {
            System.out.println("Conferma button not found, proceeding without clicking.");
        }

//        driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
        try {
            WebElement AppointmentPop_upNO2 = driver.findElement(By.xpath("//button[contains(text(),'No')]"));
            if(AppointmentPop_upNO2.isDisplayed()){
                AppointmentPop_upNO2.click();
                waitForSeconds(20);
                System.out.println("No Need Appointment so clicking NO option");
            }else{
                System.out.println("Appointment pop up not visible, so proceed for TV execution");
                driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
            }
        }catch (Exception e){
            System.out.println("No second pop up were Displayed so going to Conferma page");
        }
        scrollTop();

        //Replico Page for Final Conferma button
        WebDriverWait wait_2 = new WebDriverWait(driver, 50);
        wait_2.until(ExpectedConditions.elementToBeClickable(Conferma_btn_RP));
        driver.findElement(By.xpath("//input[@id='confirmContBtn']")).click();
        waitForSeconds(20);
        ExtentReport.getExtentTest().log(Status.PASS, "<<========Contracted Created via NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Contract Number")).build());
    }

    /***
     *  BB Area CD Address - Enter Payment details
     * @Author Dhanya
     */
    public void IBAN_Pagamento_details_BB_AreaCD() {
        //ABI Textbox value
        driver.findElement(By.xpath("//input[@sf-field='Abi_BLB__c']")).sendKeys("08332");
        waitForSeconds(10);

//        Added by Pavithra
        waitForPresenceOfElement(By.xpath("//a[normalize-space()='08332']"));
        tapOnPointInsideElement(txtAbiInPagamentoPage, "centre");
        waitForSeconds(5);
        mouseOver(txtAbiInPagamentoPage);
        clickElement(txtAbiInPagamentoPage);
        waitForSeconds(5);

        //CAB Textbox value
        driver.findElement(By.xpath("//input[@id='j_id0:pagamentoVCPQForm:pagamentoCabMaskedBLB']")).sendKeys("24400");
        waitForSeconds(10);


//        Added by Pavithra
        waitForPresenceOfElement(By.xpath("//a[normalize-space()='24400']"));
        tapOnPointInsideElement(txtCabInPagamentoPage, "centre");
        waitForSeconds(5);
        mouseOver(txtCabInPagamentoPage);
        clickElement(txtCabInPagamentoPage);
        waitForSeconds(5);

        waitForSeconds(8);

        driver.findElement(By.xpath("//input[@sf-field='Numero_Conto_BLB__c']")).sendKeys("000987654320");
        waitForSeconds(6);
        jsClick(ottieni_txt_BB, "User clicking on ottieni_txt Button");
        waitForSeconds(6);
        driver.findElement(By.xpath("//button[contains(text(),'Chiudi')]")).click();
        waitForSeconds(6);
        jsClick(ottieni_txt_BB, "User clicking on ottieni_txt Button");
        waitForSeconds(10);
        ExtentReport.getExtentTest().log(Status.PASS, "<<========IBAN_Pagamento Details in NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "IBAN_Pagamento")).build());
        driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
        waitForSeconds(20);


        try {
            WebElement confermaButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Conferma')]")));
            if (confermaButton.isDisplayed()) {
                confermaButton.click();
                System.out.println("Conferma button was present and clicked.");
                driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
                waitForSeconds(30);
            }else{
                driver.findElement(By.xpath("//a[@class='btn rightArrow']")).click();
                waitForSeconds(30);
            }
        } catch (Exception e) {
            System.out.println("Conferma button not found, proceeding without clicking.");
        }

        //scrollTop();

        //Replico Page for Final Conferma button
        WebDriverWait wait_2 = new WebDriverWait(driver, 100);
        wait_2.until(ExpectedConditions.elementToBeClickable(Conferma_btn_RP));
        driver.findElement(By.xpath("//input[@id='confirmContBtn']")).click();
        waitForSeconds(45);
        ExtentReport.getExtentTest().log(Status.PASS, "<<========Contract Created via NEW SALES==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "New Sales" + "Contract Number")).build());
    }


    public String getExtractedContract_Number(){
        //Fetching Contract number using GetText() method.
        String Contract_Number = String.valueOf(driver.findElement(By.xpath("//b[@class='ng-binding']")).getText());
        System.out.println("Contract Number Created " + Contract_Number);
        waitForSeconds(30);

//        String text = "E' stato creato il contratto n°23209177";

        Pattern pattern = Pattern.compile("\\d+");  // \\d matches any digit and + ensures one or more matches

        Matcher matcher = pattern.matcher(Contract_Number);

        String Cont_number = null;
        if (matcher.find()) {
            Cont_number = matcher.group();
            System.out.println("Extracted number: " + Cont_number);
        } else {
            System.out.println("No number found.");
        }
        return Cont_number;

    }

    /***
     * Click Chiudi after contract is created
     * @Author Dhanya
     */
    public void click_Chiudi() throws InterruptedException {
        Thread.sleep(1000);
        jsClick(Chiudi_btn, "Clicking Chiudi button");
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Chiudi button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Chiudi")).build());
        Thread.sleep(10000);
        jsClick(Chiudi_btn, "Clicking Chiudi button");
        Thread.sleep(5000);
        System.out.println("User clicked Chiudi button after contract is created");
        waitForSeconds(300);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicked Chiudi button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Chiudi")).build());
    }
}

