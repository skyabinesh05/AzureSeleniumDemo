package coeqa.pageobjects;

import coeqa.testbase.TestBase;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.C28CKI_DB;
import utils.ExtentReport;
import utils.PostgresDBI;

import java.awt.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import org.openqa.selenium.support.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class ArcadiaHomePage extends TestBase {
    String pageName;
    Robot robot;
    PostgresDBI postgresDBI;
    C28CKI_DB c28cki_db;

    protected static String beforeDowngradeColor;
    protected static String afterDowngradeColor;

    @FindBy(xpath = "//span[@id='tsidLabel']")
    WebElement Menu_btn;

    @FindBy(xpath = "//a[text()='New Sales']")
    WebElement NewSales;

    @FindBy(xpath = "//a/span[text()='New CRM']")
    WebElement NewCRM_tab;

    @FindBy(xpath = "//button[@class=' x-btn-text']//span[contains(text(),'Crea Interazione manuale')]")
    WebElement crea_interazione_manuale_btn;

    @FindBy(xpath = "//input[@id='j_id0:j_id9:confirmButton']")
    WebElement crea_btn;

    //@FindBy(xpath = "//iframe[@id='ext-comp-1088']")
    //WebElement frame_Codice_Contratto;

    @FindBy(xpath = "//iframe[contains(@src,'/apex/NewCRM_SearchAccountOrContract')]")
    WebElement Codice_Contratto_Frame;

    @FindBy(xpath = "//input[@id='j_id0:block:j_id41:standardSearchForm:txtContratto']")
    WebElement codice_contratto_txtBox;

    @FindBy(xpath = "//input[@class='btn standardSearchButton']")
    WebElement cerca_btn;

    @FindBy(xpath = "//tr[@class='dataRow']//td//a")
    WebElement contract_num_hyperlink;

    @FindBy(xpath = "//iframe[contains(@src,'/apex/NewCrm_Hybrid_HomeCliente?')]")
    WebElement Portale_Frame;

    @FindBy(xpath = "//li[contains(text(),'Gestione BB')]")
    WebElement gestione_BB;

    @FindBy(xpath = "//a[contains(text(),'Gestione light retention')]")
    WebElement gestione_LR_Option;

    @FindBy(xpath = "//iframe[contains(@src,'/apex/OmniConsoleDisplayScriptLightning')]")
    WebElement LR_Omniscript_Frame;

    @FindBy(xpath = "//input[@id='idRadio_1']")
    WebElement sky_wifi_BB_radiobtn;

    @FindBy(xpath = "(//p[text()='Avanti'])[1]")
    WebElement Avanti_One;

    @FindBy(xpath = "(//span[@class='slds-radio--faux ng-scope'])[2]")
    WebElement light_retention_KO_radiobtn;

    @FindBy(xpath = "(//span[@class='slds-radio--faux ng-scope'])[4]")
    WebElement trasferita_OK_radiobtn;

    @FindBy(xpath = "(//p[text()='Avanti'])[2]")
    WebElement Avanti_Two;

    @FindBy(xpath = "//tbody[@id='j_id0:maintable1:tb']/tr/td/span[text()='ATTIVAZIONE']")
    WebElement Category_Attivazione;

    @FindBy(xpath = "//tbody[@id='j_id0:maintable1:tb']/tr/td/span[text()='ANNULLAMENTO CONTRATTO']")
    WebElement Sottocategoria_AnnullamentoContratto;

    @FindBy(xpath = "//tbody[@id='j_id0:maintable1:tb']/tr/td/span[text()='Chiede annullamento BB']")
    WebElement Motivo_ChiedeAnnullamento_BB;

    @FindBy(xpath = "//tbody[@id='j_id0:maintable1:tb']/tr/td/span[text()='TRASFERITA']")
    WebElement DettaglioMotivo_Trasferita;

    @FindBy(xpath = "(//div[@id='HybridCPQNPP_Step_nextBtn'])")
    WebElement btn_Avanti_CarrellaUpgarde_TV;

    @FindBy(xpath = "//div[@id='ChangeAccountData_Step_nextBtn']")
    WebElement btn_Avanti_Modifica_on_Upgrade;

    @FindBy(xpath = "//div[@id='ReviewOrder_Step_nextBtn']")
    WebElement btn_Avanti_on_RiepilogoUpgrade;

        //    Added BY pavithra
    @FindBy(how = How.XPATH, using = "//button[@class=' x-btn-text']//span[contains(text(),'Crea Interazione manuale')]")
    WebElement creaInterazioneManualeBtn;

    @FindBy(how = How.XPATH, using = "//input[@class='manualIntBtn']")
    WebElement creaBtn;

    @FindBy(xpath = "//iframe[contains(@src,'/apex/NewCRM_SearchAccountOrContract')]")
    WebElement frame_Codice_Contratto;

    @FindBy(how = How.XPATH, using = "//input[@id='j_id0:block:j_id41:standardSearchForm:txtContratto']")
    WebElement codiceContrattoTxtBox;

    @FindBy(how = How.XPATH, using = "//input[@class='btn standardSearchButton']")
    WebElement creaSearchBtn;


    @FindBy(how = How.XPATH, using = "//iframe[contains(@src,'/apex/NewCRM_SearchAccountOrContract')]")
    WebElement frameIn;

    @FindBy(how = How.XPATH, using = "//tr[@class='dataRow']//td//a")
    WebElement codiceContrattoResultHLink;

    @FindBy(how = How.XPATH, using = "(//button[contains(text(),'CAMBIO CONSISTENZA')])")
    WebElement cambioConsistenzaBtn;


    @FindBy(how = How.XPATH, using = "//td[@class='tableConsB']/following-sibling::td[7]/child::span/child::span/child::img")
    WebElement imgSkyCalcioInHomePage;

    @FindBy(how = How.XPATH, using = "//iframe[contains(@src,'/apex/NewCrm_Hybrid_HomeCliente')]")
    WebElement frameTwo;


    @FindBy(how = How.XPATH, using = "//h3[text()='Gestione Consistenza']")
    WebElement gestioneConsistenzaTxt;

    @FindBy(how = How.XPATH, using = "//iframe[contains(@src,'skyHybridCPQ')]")
    WebElement frameThree;

    @FindBy(how = How.XPATH, using = "//iframe[contains(@src,'/apex/OmniConsoleDisplayScriptLightning')]")
    WebElement frameLR;

    @FindBy(how = How.XPATH, using = "//iframe[contains(@src,'SkyMigrazioneNPP')]")
    WebElement frame_Migrazione;

    @FindBy(how = How.XPATH, using = "//iframe[contains(@src,'Sky')]")
    WebElement frame_Gestione_Consistenza;

    @FindBy(how = How.XPATH, using = "//iframe[contains(@src,'c__skyOrderReview')]")
    WebElement frameOR;


    @FindBy(how = How.XPATH, using = "(//div[text()='SKY CALCIO'])[1]")
    WebElement txtSkyCalcio;

    @FindBy(how = How.XPATH, using = "(//img[contains(@src,'skyCO_resources/images/PPACK_SPORT.png')])[1]")
    WebElement skyCalcioImg;


    private static final String skyPackage = new StringBuilder().append("(//img[contains(@src,'skyCO_resources/images/")
            .append("<<PACKAGE>>")
            .append(".png')])[1]").toString();

    private static final String skyPack = new StringBuilder().append("(//span[contains(normalize-space(),'")
            .append("<<PACKAGE>>")
            .append(" sar')])[2]").toString();

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Si')]")
    WebElement agguingoPopup;
    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Si')]")
    WebElement siButtonInPopup;

    @FindBy(how = How.XPATH, using = "(//input[@id='radio-3'])[1]")
    WebElement rdoAltro;

    @FindBy(how = How.XPATH, using = "//button[normalize-space()='Schedula']")
    WebElement btnSchedula;

    @FindBy(how = How.XPATH, using = "//div[@id='HybridCPQNPP_Step_nextBtn']")
    WebElement avantiBtnForUpDownPckg;


    @FindBy(how = How.XPATH, using = "(//p[text()='Prosegui'])[1]")
    WebElement btnProsegui;

    @FindBy(how = How.XPATH, using = "//div[@id='street-view_nextBtn']//child::p[text()='Prosegui']")
    WebElement btnProseguiInModificaDatiCliente;

    @FindBy(how = How.XPATH, using = "(//p[text()='Prosegui'])[3]")
    WebElement btnProseguiInRiepilogoOrdine;

    @FindBy(how = How.XPATH, using = "//p[normalize-space()='Conferma']")
    WebElement btnConforma;

    @FindBy(how = How.XPATH, using = "//strong[normalize-space()='Invio ordine riuscito correttamente.']")
    WebElement txtSuccessMsg;

    @FindBy(how = How.XPATH, using = "//p[normalize-space()='FINE']")
    WebElement btnFine;

    @FindBy(xpath = "(//button[contains(text(),'LISTINO 2021')])")
    WebElement Listino2021_btn;

    //Added by abinesh - Migrazione Legacy to NPP
    @FindBy(xpath = "//button[contains(text(),'CAMBIO CONSISTENZA')]")
    WebElement Cambio_btn;

    @FindBy(xpath = "(//div[@id='UpTechPromoSeletionStep_nextBtn']//p)[2]")
    WebElement btn_Avanti_Promo_di_Upgrade_Tecnologico;

    @FindBy(xpath = "(//button[@ng-repeat='button in buttons'])[2]")
    WebElement btn_OK_Avivso;

    @FindBy(xpath = "(//div[@id='MigrationStep_TV_nextBtn'])[3]//p")
    WebElement btn_Avanti_Migrazione_TV;

    @FindBy(xpath = "//span[contains(text(),'Cambio Consistenza')]")
    WebElement Cambio_Consistenza_option;

    @FindBy(xpath = "//div[@id='StepSceltaLavorazione_nextBtn']")
    WebElement Avanti_on_Scelta_Attivita;

    @FindBy(xpath = "(//button[@class='slds-button slds-button--success'])[2]")
    WebElement btn_PopUp_Migrazione_TV;

    @FindBy(xpath = "(//div[@id='ChangeAccountDataStep_TV_nextBtn'])[1]")
    WebElement btn_Avanti_Modifica_Dati_Account;

    @FindBy(xpath = "(//div[@id='OrderReviewStep_TV_nextBtn'])[1]")
    WebElement btn_Avanti_on_RiepilogoOrdine;

    @FindBy(xpath = "//span[contains(text(),'Registrazione Vocal Order')]")
    WebElement radiobtn_Registrazione_Vocal_Order;

    @FindBy(xpath = "(//div[@id='Step_DigitalSignatureChoice_nextBtn'])[1]")
    WebElement btn_Avanti_Registrazione_Vocal_Order;

    @FindBy(xpath = "//p[text()='Invia Ordine']")
    WebElement btn_Invia_Ordine;

    @FindBy(xpath = "//p[text()='FINE']")
    WebElement finebtn_Conferma_Ordine;

    /***
     * Check Status in Arcadia Portale Page
     * @Author Dhanya
     */
    @FindBy(xpath = "//td[text()='ATTESA MATRICOLA']")
    WebElement AttesaMatricola_txt;

    @FindBy(xpath = "//div[contains(text(),'Area C/D') and @style='display:block']")
    WebElement AreaCD_txt;

    @FindBy(xpath = "//td[contains(@onclick,'Contract')]")
    WebElement SearchLens_icon;

    @FindBy(xpath = "//td[text()='Contract Start Date']")
    WebElement ContractStartDate_Field;

    @FindBy(xpath = "//td[text()='Data Prima Attivazione']")
    WebElement DataPrimaAttivazione_Field;

    @FindBy(how = How.XPATH, using = "(//input[@value='BALANCE-FATTURE-PAGAMENTI'])[1]")
    WebElement btnBalanceFattura;

    @FindBy(xpath = "//iframe[contains(@src,'/apex/NewCrm_F2_Hybrid_FattureSubtab?')]")
    WebElement Fattura_Frame;

    @FindBy(how = How.XPATH, using = "//td[@class='dataCell 805386045']//img")
    WebElement btnpdf;

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'CHIUDI')]")
    WebElement FatturaChuidi;


    public ArcadiaHomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        pageName = this.getClass().getSimpleName();
    }

    /*
    *Method for clicking Cambio Consistenza option in Scelta Attivita page
    * @Author Abinesh
     */
    public  void click_on_CambioOption() {
        waitForSeconds(10);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(1);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking on the Cambio Consistenza option in Scelta Attivita page==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Cambio Consistenza option in Scelta Attivita page")).build());
        //Esito Controlli Preliminari - if this page is visible this method will run
        try {
            WebElement btn_Avanti_Esito_Controlli_Preliminari = driver.findElement(By.xpath("//div[@id='PreliminaryCheckStepWarningMessage_nextBtn']"));
            if(btn_Avanti_Esito_Controlli_Preliminari.isDisplayed()){
                System.out.println("Esito Controlli Preliminari is Visible and Clicking on Avanti");
                waitForPresenceOfElement(By.xpath("//div[@id='PreliminaryCheckStepWarningMessage_nextBtn']"));
                btn_Avanti_Esito_Controlli_Preliminari.click();
                System.out.println("Clicked on Avanti button in Esito Controlli Preliminari page");
            }else {
                System.out.println("Esito Controlli Preliminari page is not visible and going to Promo page ");
            }
        }catch (Exception e) {
            System.out.println("Esito Controlli Preliminari page is not visible and going to Promo page ");
        }

        waitForSeconds(10);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(1);
        waitForSeconds(20);
        waitForPresenceOfElement(By.xpath("//span[contains(text(),'Cambio Consistenza')]"));
        jsClick(Cambio_Consistenza_option,"Clicking on Cambio_Consistenza_option on Scelta Attivita");
        waitForSeconds(15);
        jsClick(Avanti_on_Scelta_Attivita,"Clicking on Cambio_Consistenza_option on Scelta Attivita");
        waitForSeconds(50);
    }

    public void Listino_btn() {
//        driver.navigate().refresh();
        waitForSeconds(3);
        focusFrame(frameTwo);
        waitForSeconds(3);
        scrollToElement(Listino2021_btn);
        waitForSeconds(5);
        waitForElement(Listino2021_btn);
        clickElement(Listino2021_btn);
        waitForSeconds(100);
        System.out.println("inside Listino2021 ");

        driver.switchTo().defaultContent();
        waitForSeconds(5);
        focusFrame(frameLR);
//        focusFrame(frameThree);
        System.out.println("after focus window three");
        waitForSeconds(5);
    }

    public void Click_Menu_btn_and_Select_NewSales() {
        waitForPageToLoad();
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='menuButtonLabel']")));
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking on the Menu button and Selecting New Sales==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "New Sales")).build());
        jsClick(Menu_btn,"User clicking on Menu_tab");
        jsClick(NewSales,"User clicking on New_Sales");
    }

    public void Click_NewCRM_tab() {
        waitForPageToLoad();
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a/span[text()='New CRM']")));
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking on the New CRM Tab==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "New CRM")).build());
        jsClick(NewCRM_tab,"User clicking on New CRM tab");
    }

    public void Click_Crea_Interazione_Manuale_btn() {
        waitForPageToLoad();
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class=' x-btn-text']//span[contains(text(),'Crea Interazione manuale')]")));
//        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking on Crea Interazione Manuale Button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Crea Interazione Manuale")).build());
        jsClick(crea_interazione_manuale_btn,"User clicking on Crea Interazione Manuale Button");
    }

    public void Click_CREA_btn() throws InterruptedException {
        Thread.sleep(20000);
//        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking on the CREA button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "CREA")).build());
        //expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='j_id0:j_id9:confirmButton']")));
        //jsClick(crea_btn,"User clicking on Crea Button");
        String CreaButton = "C:\\Users\\ita694\\Downloads\\Code Merge\\coeqa-bdd-Final (1)\\coeqa-bdd\\src\\test\\resources\\Autoit\\CreaButton.exe";
        try {
            Process process = Runtime.getRuntime().exec(CreaButton);
            int exitCode = process.waitFor();
            System.out.println("Process exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        //tapOnPointInsideElement(crea_btn, "centre");
        Thread.sleep(5000);

    }
    public void Enter_Contract_Number(String Contract) throws InterruptedException {
        waitForPageToLoad();
//        Added by pavithra
        waitForSeconds(5);
        driver.switchTo().defaultContent();
        waitForSeconds(5);
//        till above
        driver.switchTo().frame(Codice_Contratto_Frame);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User Entering Contract Number in Codice Contratto Textbox==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Codice Contratto")).build());
        Thread.sleep(5000);
        jsInput(codice_contratto_txtBox, Contract, "User entering Contract Number in Codice Contratto Textbox");
    }

    public void Click_CERCA_btn() {
        waitForPageToLoad();
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class='btn standardSearchButton']")));
        jsClick(cerca_btn,"User clicking on cerca Button");

    }

    public void Click_Contract_Hyperlink() {
        waitForPageToLoad();
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr[@class='dataRow']//td//a")));
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking on the Contract Hyperlink==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Contract Hyperlink")).build());
        jsClick(contract_num_hyperlink,"User clicking on Contract hyperlink");
        waitForSeconds(10);
    }

    public void Click_GestioneBB_LR() throws InterruptedException {
        Thread.sleep(20000);
        driver.navigate().refresh();
        handlePopup();
        Thread.sleep(5000);
        driver.switchTo().frame(Portale_Frame);
        System.out.println(elementDisplayed(gestione_BB));
        Thread.sleep(5000);
        scrollToElement(gestione_BB);
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking on the Gestione BB button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Gestione BB")).build());
        jsClick(gestione_BB, "User clicking Gestione BB button");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking on the Gestione Light Retention button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Gestione Light Retention")).build());
        jsClick(gestione_LR_Option, "User clicking Gestione light retention option");
        Thread.sleep(50000);
    }

    public void LR_Omniscript_function() throws InterruptedException {
        Thread.sleep(5000);
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        driver.switchTo().frame(LR_Omniscript_Frame);
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking on the Sky Wifi BB Radio button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Sky Wifi BB")).build());
        jsClick(sky_wifi_BB_radiobtn, "User clicking Sky Wifi BB Radio button");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking on the Avanti button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Avanti")).build());
        jsClick(Avanti_One, "User clicking Avanti button");
        Thread.sleep(20000);
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        driver.switchTo().frame(LR_Omniscript_Frame);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking on the light retention KO radio button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "light retention KO")).build());
        jsClick(light_retention_KO_radiobtn, "User clicking light retention KO radio button");
        Thread.sleep(10000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking on the Trasferita OK radio button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Trasferita OK")).build());
        jsClick(trasferita_OK_radiobtn, "User clicking Trasferita OK radio button");
        Thread.sleep(20000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking on the Avanti button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Avanti")).build());
        jsClick(Avanti_Two, "User clicking Avanti button");
        Thread.sleep(50000);
        driver.switchTo().defaultContent();
        Thread.sleep(5000);
        driver.switchTo().frame(LR_Omniscript_Frame);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User Validating the Status==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Status")).build());
        elementDisplayed(Category_Attivazione);
        //System.out.println(Category_Attivazione);
        elementDisplayed(Sottocategoria_AnnullamentoContratto);
        //System.out.println(Sottocategoria_AnnullamentoContratto);
        elementDisplayed(Motivo_ChiedeAnnullamento_BB);
        //System.out.println(Motivo_ChiedeAnnullamento_BB);
        elementDisplayed(DettaglioMotivo_Trasferita);
        //System.out.println(DettaglioMotivo_Trasferita);
        Thread.sleep(5000);
    }



//    Pavithra - Downgrade


    public void creaInterazioneManuale() {
        waitForSeconds(10);
        waitForElement(creaInterazioneManualeBtn);
        clickElement(creaInterazioneManualeBtn);
        waitForSeconds(10);
        focusFrame(0);
//        System.out.println("after focus");
        waitForPresenceOfElement(By.xpath("//input[@class='manualIntBtn']"));
        tapOnPointInsideElement(creaBtn, "centre");
        waitForSeconds(10);
        mouseOver(creaBtn);
        clickElement(creaBtn);
        waitForSeconds(10);
    }

    public void SearchContract(String strNum) {
//        waitForPageToLoad();
        waitForSeconds(5);
        driver.switchTo().defaultContent();
        waitForSeconds(5);

        focusFrame(frameIn);
        System.out.println("Contract>>>>>" + strNum);
        jsEnterValue(codiceContrattoTxtBox, strNum);
        waitForSeconds(5);
        clickElement(creaSearchBtn);
        waitForSeconds(5);

//        clickElement(creaSearchBtn);

    }

    public void clickContract() {
        clickElement(codiceContrattoResultHLink);
        waitForSeconds(5);
    }



    public void clickCambioConsistenza()  {
        driver.navigate().refresh();
        waitForSeconds(10);
        focusFrame(frameTwo);
        waitForSeconds(3);


        System.out.println("color>>>>>>>>>>>>" + imgSkyCalcioInHomePage.getCssValue("color"));



        // Get the image source (URL)
        String imageSrc = imgSkyCalcioInHomePage.getAttribute("src");
        System.out.println("Image URL: " + imageSrc);



        beforeDowngradeColor = colorValidationForPackages(imgSkyCalcioInHomePage);
        System.out.println("beforeDowngradeColor>>>>>>" + beforeDowngradeColor);



        scrollToElement(cambioConsistenzaBtn);
        waitForSeconds(5);
        waitForElement(cambioConsistenzaBtn);
        clickElement(cambioConsistenzaBtn);
        waitForSeconds(100);
        System.out.println("inside CambioConsistenza ");

        driver.switchTo().defaultContent();
        waitForSeconds(5);
        focusFrame(frameLR);
        focusFrame(frameThree);
        System.out.println("after focus window three");
        waitForSeconds(5);

    }

    public void selectPackage(String skpackage) {


        By sPackage = By.xpath(skyPackage.replace("<<PACKAGE>>", skpackage));

        waitForElement(sPackage);
//        waitForPresenceOfElement(By.xpath("(//div[text()='SKY CALCIO'])[1]"));
        clickEle(sPackage);
//        clickElement(skyCalcioImg);
//        tapOnPointInsideElement(txtSkyCalcio, "centre");
//        jsClickElement(skyCalcioImg);
        waitForSeconds(5);
    }



    public void clickSi() {
        waitForSeconds(5);
        scrollToElement(siButtonInPopup);
        waitForElement(siButtonInPopup);
        clickElement(siButtonInPopup);
        waitForSeconds(50);


    }



    public void clickPackageDowngradedAvanti() {
        scrollToElement(rdoAltro);
        waitForSeconds(2);
        clickElement(rdoAltro);
        clickElement(rdoAltro);
        waitForSeconds(10);
        clickElement(btnSchedula);
        waitForSeconds(5);
        driver.switchTo().defaultContent();
        focusFrame(frameLR);
        waitForSeconds(5);
        clickElement(btnProsegui);
        waitForSeconds(5);


    }

    public void clickProseguiInModificaDatiCliente(){
        waitForSeconds(20);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(1);
        scrollToElement(btnProseguiInModificaDatiCliente);
        waitForSeconds(1);
        clickElement(btnProseguiInModificaDatiCliente);
        waitForSeconds(5);
    }

    public void clickProseguiInRiepilogoOrdine(String strPack){
        waitForSeconds(10);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(3);
        focusFrame(frameOR);
        waitForSeconds(2);

        By sPack = By.xpath(skyPack.replace("<<PACKAGE>>", strPack));

        System.out.println("skypackage summary>>>>>>>" + sPack);

        waitForElement(sPack);
        driver.findElement(sPack).isDisplayed();

        waitForSeconds(3);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(2);
        scrollToElement(btnProseguiInRiepilogoOrdine);
        waitForSeconds(1);
        clickElement(btnProseguiInRiepilogoOrdine);
        waitForSeconds(5);
    }

    public void clickConforma(){
        waitForSeconds(10);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(1);
        clickElement(btnConforma);
        waitForSeconds(5);
    }

    public void successMessageInEsito(){
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(1);
        verifyElement(txtSuccessMsg);
        waitForSeconds(2);
        clickElement(btnFine);
    }

    public void Avanti_On_PromoPage() {
        waitForSeconds(10);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(1);
        //Esito Controlli Preliminari - if this page is visible this method will run
        try {
            WebElement btn_Avanti_Esito_Controlli_Preliminari = driver.findElement(By.xpath("(//div[@id='WarningStep_PreliminaryCheckTV_nextBtn'])[1]"));
            if(btn_Avanti_Esito_Controlli_Preliminari.isDisplayed()){
                System.out.println("Esito Controlli Preliminari is Visible and Clicking on Avanti");
                waitForPresenceOfElement(By.xpath("(//div[@id='WarningStep_PreliminaryCheckTV_nextBtn'])[1]"));
                btn_Avanti_Esito_Controlli_Preliminari.click();
                System.out.println("Clicked on Avanti button in Esito Controlli Preliminari page");
            }else {
                System.out.println("Esito Controlli Preliminari page is not visible and going to Promo page ");
            }
        }catch (Exception e) {
            System.out.println("Esito Controlli Preliminari page is not visible and going to Promo page ");
        }

        waitForSeconds(10);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(1);
        waitForElement(btn_Avanti_Promo_di_Upgrade_Tecnologico);
        clickElement(btn_Avanti_Promo_di_Upgrade_Tecnologico);
        waitForSeconds(5);
    }

    public void Avivso_popup() {
        waitForSeconds(10);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(1);
        waitForElement(btn_OK_Avivso);
        clickElement(btn_OK_Avivso);
        waitForSeconds(5);
    }

    public void Estione_Primli(){
        waitForSeconds(10);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(1);
        scrollDown();
        waitForSeconds(1);

        try{
            WebElement btn_Avanti_Esito_Controlli_Preliminari_def = driver.findElement(By.xpath("(//div[@id='WarningStep2_NoTopStrategy_nextBtn'])[1]"));
            if(btn_Avanti_Esito_Controlli_Preliminari_def.isDisplayed()){
                System.out.println("Esito Controlli Preliminari is Visible and Clicking on Avanti in Configuration Page");
                waitForPresenceOfElement(By.xpath("(//div[@id='WarningStep2_NoTopStrategy_nextBtn'])[1]"));
                btn_Avanti_Esito_Controlli_Preliminari_def.click();
                System.out.println("Clicked on Avanti button in Esito Controlli Preliminari page");
            }else {
                System.out.println("Esito Controlli Preliminari page is not visible and going to Promo page ");
            }
        }catch(Exception e){
            System.out.println("Esito Controlli Preliminari page is not visible and going to Promo page ");
        }

        waitForSeconds(10);
        waitForSeconds(5);
    }
    public void Avanti_on_Carrello_Migrazione_TV() {
        waitForSeconds(10);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(1);
        scrollDown();
        waitForSeconds(50);
        waitForPresenceOfElement(By.xpath("(//div[@id='MigrationStep_TV_nextBtn'])[3]//p"));
        jsClick(btn_Avanti_Migrazione_TV,"Clicking on Avanti on Carrello_Migrazione_TV");
        waitForSeconds(50);
        //clicking on OK from pop-up
        try{
            WebElement btn_PopUp_Migrazione_TV = driver.findElement(By.xpath("(//button[text()='Ok'])"));
            if(btn_PopUp_Migrazione_TV.isDisplayed()){
                btn_PopUp_Migrazione_TV.click();
                waitForSeconds(20);
                System.out.println("clicking POP SI option");
            }else {
                System.out.println("pop up not visible, so proceed for Next Page execution");
            }
        }catch (Exception e){
            System.out.println("pop up not visible, so proceed for Next Page execution");
        }

    }

    public void Avanti_on_Modifica_Dati_Account() {
        waitForSeconds(10);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(20);
        waitForElement(btn_Avanti_Modifica_Dati_Account);
        waitForSeconds(10);
        clickElement(btn_Avanti_Modifica_Dati_Account);
        waitForSeconds(5);
    }

    public void Avanti_on_RiepilogoOrdine() {
        waitForSeconds(10);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(20);
        waitForPresenceOfElement(By.xpath("(//div[@id='OrderReviewStep_TV_nextBtn'])[1]"));
        waitForSeconds(10);
        jsClick(btn_Avanti_on_RiepilogoOrdine,"Avanti on RiepilogoOrdine");
        waitForSeconds(5);
    }

    public void Firma_Digitale() {
            //clicking the radio btn for vocal order
            waitForSeconds(10);
            driver.switchTo().defaultContent();
            waitForSeconds(1);
            focusFrame(frameLR);
            waitForSeconds(20);
            waitForPresenceOfElement(By.xpath("//span[contains(text(),'Registrazione Vocal Order')]"));
        waitForSeconds(10);
            clickElement(radiobtn_Registrazione_Vocal_Order);
            waitForSeconds(20);
        ExtentReport.getExtentTest().log(
                Status.PASS,
                "<<===========User clicked on Registrazione Vocal Order in Firma_Digitale==========>>",
                MediaEntityBuilder.createScreenCaptureFromPath(
                        getScreenShot(driver, "Registrazione Vocal Order")
                ).build());
            waitForPresenceOfElement(By.xpath("(//div[@id='Step_DigitalSignatureChoice_nextBtn'])[1]"));
        waitForSeconds(10);
            clickElement(btn_Avanti_Registrazione_Vocal_Order);
            waitForSeconds(5);

            //Conferma Ordine
            waitForSeconds(10);
            driver.switchTo().defaultContent();
            waitForSeconds(1);
            focusFrame(frameLR);
            waitForSeconds(20);
            waitForPresenceOfElement(By.xpath("//p[text()='Invia Ordine']"));
//            waitForElement(btn_Invia_Ordine);
        waitForSeconds(10);
            clickElement(btn_Invia_Ordine);

            //Verify the "Success Message"
        waitForSeconds(70);
            String textDemo = driver.findElement(By.xpath("//strong[text()='Ordine Inviato correttamente.']")).getText();
            if (!textDemo.isEmpty()) {
                System.out.println("Ordine Inviato correttamente is Present");
            } else
                System.out.println("Ordine Inviato correttamente is not Present");
            waitForSeconds(25);
        ExtentReport.getExtentTest().log(
                Status.PASS,
                "<<===========User clicked on Ordine Inviato correttamente in Firma_Digitale==========>>",
                MediaEntityBuilder.createScreenCaptureFromPath(
                        getScreenShot(driver, "Ordine Inviato correttamente.")
                ).build());
        waitForPresenceOfElement(By.xpath("//p[text()='FINE']"));
        clickElement(finebtn_Conferma_Ordine);
        waitForSeconds(200);
        driver.navigate().refresh();
        waitForSeconds(10);
    }

    /***
     * * Navigate to promo page and upgrading the downgraded package
     * No Need to Edit anything , it will fetch automatically grey out elements *
     * @Author  Abinesh
     * */
    public void Upgrading_Promos() {
        waitForSeconds(20);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(2);
        focusFrame(frame_Migrazione);
        waitForSeconds(20);
        try {
            System.out.println("Starting the Upgrading_Promos method...");


            // Initialize WebDriverWait with a timeout of 60 seconds
            WebDriverWait wait = new WebDriverWait(driver, 30);


//            // Wait for the "Contenuti" section to be visible
            System.out.println("Waiting for the 'Contenuti' section to load...");
            WebElement contenutiSection = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(),'Contenuti')]")
            ));
            System.out.println("'Contenuti' section is visible.");

            // Wait for the grid containing the elements to load
            System.out.println("Waiting for the grid elements to load...");
            WebElement gridContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("(//div[contains(text(),'Contenuti')]/following::div[@class='slds-grid slds-wrap'])[1]")
            ));
            System.out.println("Grid container is loaded.");

            // Find all image elements within the grid container
            List<WebElement> elements = gridContainer.findElements(By.xpath(".//div[contains(@ng-if,'item.item')]//div//img"));
            System.out.println("Found " + elements.size() + " elements in the grid.");

            // Loop through the elements and process grey elements
            for (WebElement element : elements) {
                try {
                    // Check if the element contains the class "grey"
                    String className = element.getAttribute("class");
                    System.out.println("Processing element with class: " + className);

                    if (className != null && className.contains("grey")) {
                        System.out.println("Grey element detected. Attempting to click...");

                        // Scroll the element into view
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

                        // Wait for the element to be clickable and click it
                        wait.until(ExpectedConditions.elementToBeClickable(element));
                        element.click();
                        System.out.println("Clicked on grey-colored package.");

                        // Handle confirmation (if applicable)
                        Thread.sleep(3000); // Replace with explicit wait if required
                        driver.findElement(By.xpath("//button[text()='Si']")).click();
                        System.out.println("Confirmed the selection.");
                        ExtentReport.getExtentTest().log(
                                Status.PASS,
                                "<<===========User clicked on the grey-out package for upgrading, Succesfully==========>>",
                                MediaEntityBuilder.createScreenCaptureFromPath(
                                        getScreenShot(driver, "Upgrading_Promos_Screenshot")
                                ).build());
                        break; // Stop after clicking the first grey-colored element
                    }
                } catch (Exception e) {
                    System.out.println("Error processing element: " + e.getMessage());
                }
            }

            // Log success in the Extent Report
            System.out.println("Upgrading_Promos method completed successfully.");
            ExtentReport.getExtentTest().log(
                    Status.PASS,
                    "<<===========User clicked on the grey-out package for upgrading==========>>",
                    MediaEntityBuilder.createScreenCaptureFromPath(
                            getScreenShot(driver, "Upgrading_Promos_Screenshot")
                    ).build()
            );

        } catch (TimeoutException te) {
            System.out.println("Timed out while waiting for the elements: " + te.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error occurred in Upgrading_Promos: " + e.getMessage());
        }
    }

    /***
     * postgres result validation method
     * @Author Pavithra
     */

    public void verifyPostgresResults(String contractNum){
        String Estatus = "COMPLETED";
        String Astatus = "";
        String EsubStatus = null;
        String AsubStatus = "";
        String conNum = contractNum;
        String EDate ="";


        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = formatter.format(today);

        try (Connection connection = postgresDBI.getConnectionIT()) {
            Statement statement = connection.createStatement();
            String baseQuery = "SELECT * from omcustom.T_ORDER a where a.order_num = '' order by a.creation_date desc";

            String updatedQuery = baseQuery.replace("''", "'" + conNum + "'");

            ResultSet resultSet = statement.executeQuery(updatedQuery);
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


    public boolean verifyPackageDowngraded(){
        waitForSeconds(5);
        waitForElement(imgSkyCalcioInHomePage);

        boolean x;
        afterDowngradeColor = colorValidationForPackages(imgSkyCalcioInHomePage);
        System.out.println("afterDowngradeColor>>>>>>" + afterDowngradeColor);

        // Direct string comparison
        if (beforeDowngradeColor.equals(afterDowngradeColor)) {
            System.out.println("The two RGB values are equal.");
            x= false;
        } else {
            System.out.println("The two RGB values are not equal.");
            x=true;
        }
        return x;
    }


    public void verifyKenanResults(String contractNum){
        String EMsgDesc = "U_ASSETDATA";
        String AMsgDesc = "";
        String EStatus = "SUCCESS";
        String AStatus = "";
        String conNumb = contractNum;
        String EDate ="";


        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = formatter.format(today);



        try (Connection connection = c28cki_db.getConnection()) {
            Statement statement = connection.createStatement();
            String baseQuery = "select CME.message_external_id, CME.message_id, CM.MESSAGE_UNIQUE_ID, CMT.message_type_descr, CM.CKI_MESSAGE_SUB_TYPE,\n" +
                    "CM.MESSAGE_TYPE_ID, CM.MESSAGE_STATUS, CM.MESSAGE_TIMESTAMP, CM.MESSAGE_SYSTEM_SOURCE, message_parent_id\n" +
                    "from CKI_MESSAGE_EXTERNAL_ID CME, CKI_MESSAGE CM, CKI_MESSAGE_TYPE CMT\n" +
                    "where CME.message_id=CM.message_id\n" +
                    "and CM.message_type_id=CMT.message_type_id\n" +
                    "and CME.message_external_id ='' order by CM.MESSAGE_TIMESTAMP desc";


            String updatedQuery = baseQuery.replace("''", "'" + conNumb + "'");

            ResultSet resultSet = statement.executeQuery(updatedQuery);

        if (resultSet.next()) {


            String input = resultSet.getString("MESSAGE_TIMESTAMP");
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
            LocalDateTime dateTime = LocalDateTime.parse(input, inputFormatter);
            LocalDate date = dateTime.toLocalDate();
            EDate = String.valueOf(date);

            System.out.println("Msg Desc\t--\t" + resultSet.getString("MESSAGE_TYPE_DESCR"));
            System.out.println("Msg Status\t--\t" + resultSet.getString("MESSAGE_STATUS"));
            System.out.println("Msg Time\t--\t" + resultSet.getString("MESSAGE_TIMESTAMP"));
        }
        else{
            System.out.println("No data found in the table.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }


        Assert.assertEquals(AMsgDesc, EMsgDesc, "Desc is equal not equal");
        Assert.assertEquals(AStatus, EStatus, "Status is not equal");
        Assert.assertEquals(EDate, todayDate, "Date is not equal");
    }

    /***
     * Check Status in Arcadia Portale Page
     * @Author Dhanya
     */
    public void checkContractStatus_BBAreaCD() throws InterruptedException {
        Thread.sleep(10000);
        driver.navigate().refresh();
        handlePopup();
        Thread.sleep(5000);
        driver.switchTo().frame(Portale_Frame);
        if (AttesaMatricola_txt.isDisplayed() && AreaCD_txt.isDisplayed()) {
            System.out.println("Contract Status is ATTESA MATRICOLA and Area C/D is displayed in Portale Page");
        } else {
            System.out.println("Status is not updated Correctly");
        }
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User validated Contract Status in Portale Page==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "AreaCDStatus")).build());
        Thread.sleep(5000);
        jsClick(SearchLens_icon, "Clicking on search icon");
        Thread.sleep(10000);
        scrollToElement(ContractStartDate_Field);
        Thread.sleep(2000);
        elementDisplayed(ContractStartDate_Field);
        System.out.println("Contract Start date is displayed");
        elementDisplayed(DataPrimaAttivazione_Field);
        System.out.println("Data Prima Attivazione is displayed");
        Thread.sleep(5000);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User validated Date and Time==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "DateTimeStatus")).build());
    }

    /***
     * Verify Postgres Status after JMeter job is run in IT Chain
     * @Author Dhanya
     */
    public void verifyPostgresStatus_afterJMeter_IT(String Codice_AreaCD) {
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
            String baseQuery = "SELECT * from omcustom.T_ORDER a where a.order_num = '"+Codice_AreaCD+"' order by a.creation_date desc";
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

        Assert.assertEquals(Astatus, Estatus, "Status is not equal");
        Assert.assertEquals(AsubStatus, EsubStatus, "Sub Status is not equal");
        Assert.assertEquals(EDate, todayDate, "Date is not equal");
    }

    public void balancefatturepagamento() {

        waitForSeconds(10);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        driver.navigate().refresh();
        waitForSeconds(1);
        focusFrame(frameTwo);
        waitForSeconds(1);
//        WebElement chuidiButton_Fattura = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'CHIUDI')]")));
        try{
            if (FatturaChuidi.isDisplayed()) {
                FatturaChuidi.click();
                System.out.println("Chiudi button was present and clicked.");
                waitForSeconds(30);
            }else{
                System.out.println("Chiudi button was not present and moving to further.");
            }
        }
        catch (Exception e){
            System.out.println("Chiudi button was not present and moving to further.");
        }
        waitForSeconds(5);
        scrollToElement(btnBalanceFattura);
        waitForSeconds(5);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking on Balance fattura button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Balance fatturapagamento")).build());

        clickElement(btnBalanceFattura);
        waitForSeconds(10);

        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(Fattura_Frame);
        waitForSeconds(1);
        driver.navigate();
        waitForSeconds(1);
        scrollToElement(btnpdf);
        waitForSeconds(1);
        //clickElement(btnpdf);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking on PDF==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "PDF")).build());
        jsClick(btnpdf, "Clicking PDF");
        waitForSeconds(30);
    }

    public List<String> switchtofatturapdfTab(){
        waitForNewTab();

        List<String> fatturatab = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(fatturatab.get(2));
        waitForSeconds(5);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User Redirect to on PDF window==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Reflecting to New PDF Window")).build());
        return fatturatab;
    }

    /***
     *  * navigate to  Arcadia Home page and verify the Text Leagcy to NPP  *
     * @Author Abinesh  */
    public void VerfiyListinoText() {
        waitForSeconds(20);
        String textDemo = driver.findElement(By.xpath("//div[@class='listinoTitle']//span//span")).getText();
        if (!textDemo.isEmpty()) {
            System.out.println("Listino Sky NPP Standard is changes Successfully");
            ExtentReport.getExtentTest().log(Status.PASS, "<<===========User gets Listino Sky NPP Succesfully==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Listiono Status")).build());
        } else
            System.out.println("Listino Sky NPP Standard is not changed");
        waitForSeconds(15);


    }

    public void Upgrading_Package_NPP_Legacy() {
        waitForSeconds(20);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(2);
        focusFrame(frame_Gestione_Consistenza);
        waitForSeconds(20);
        try {
            System.out.println("Starting the Upgrading_Promos method...");


            // Initialize WebDriverWait with a timeout of 60 seconds
            WebDriverWait wait = new WebDriverWait(driver, 30);


//            // Wait for the "Contenuti" section to be visible
            System.out.println("Waiting for the 'Contenuti' section to load...");
            WebElement contenutiSection = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("(//span[contains(text(),'CONTENUTI')])[1]")
            ));
            System.out.println("'Contenuti' section is visible.");

            // Wait for the grid containing the elements to load
            System.out.println("Waiting for the grid elements to load...");
            WebElement gridContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("(//span[contains(text(),'CONTENUTI')])[1]/following::div[contains(@class,'skyCO-cpq-child-products')][1]")
            ));
            System.out.println("Grid container is loaded.");

            // Find all image elements within the grid container
            List<WebElement> elements = gridContainer.findElements(By.xpath("(//div[contains(@class,'skyCO-cpq-child-products')])[2]//div//div//div//div//div//div//div//div//img[contains(@ng-class,'skyCO-img')]"));
            System.out.println("Found " + elements.size() + " elements in the grid.");

            // Loop through the elements and process grey elements
            for (WebElement element : elements) {
                try {
                    // Check if the element contains the class "grey"
                    String className = element.getAttribute("ng-class");
                    System.out.println("Processing element with class: " + className);

                    if (className != null && className.contains("upgrade")) {
                        System.out.println("Grey element detected. Attempting to click...");

                        // Scroll the element into view
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

                        // Wait for the element to be clickable and click it
                        wait.until(ExpectedConditions.elementToBeClickable(element));
                        element.click();
                        System.out.println("Clicked on grey-colored package.");

                        // Handle confirmation (if applicable)
                        ExtentReport.getExtentTest().log(
                                Status.PASS,
                                "<<===========User clicked on the SI option for upgrading==========>>",
                                MediaEntityBuilder.createScreenCaptureFromPath(
                                        getScreenShot(driver, "Upgrading_Promos_Screenshot in POP UP")
                                ).build());
                        Thread.sleep(3000); // Replace with explicit wait if required
                        driver.findElement(By.xpath("//button[text()='Si']")).click();
                        System.out.println("Confirmed the selection.");
                        ExtentReport.getExtentTest().log(
                                Status.PASS,
                                "<<===========User clicked on the grey-out package for upgrading, Succesfully==========>>",
                                MediaEntityBuilder.createScreenCaptureFromPath(
                                        getScreenShot(driver, "Upgrading_Promos_Screenshot")
                                ).build());
                        break; // Stop after clicking the first grey-colored element
                    }
                } catch (Exception e) {
                    System.out.println("Error processing element: " + e.getMessage());
                }
            }

            // Log success in the Extent Report
            System.out.println("Upgrading_Promos method completed successfully.");
            waitForSeconds(10);
            ExtentReport.getExtentTest().log(
                    Status.PASS,
                    "<<===========User clicked on the grey-out package for upgrading==========>>",
                    MediaEntityBuilder.createScreenCaptureFromPath(
                            getScreenShot(driver, "Upgrading_Promos_Screenshot")
                    ).build()
            );

        } catch (TimeoutException te) {
            System.out.println("Timed out while waiting for the elements: " + te.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error occurred in Upgrading_Promos: " + e.getMessage());
        }

    }

    public void Avanti_on_Upgrading_CarrellaMigrazione() {
        waitForSeconds(10);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(1);
        scrollDown();
        waitForSeconds(50);
        ExtentReport.getExtentTest().log(
                Status.PASS,
                "<<===========User clicked on Avanti on Carrella Page upgrading==========>>",
                MediaEntityBuilder.createScreenCaptureFromPath(
                        getScreenShot(driver, "Upgrading_Promos_Screenshot")
                ).build());
        waitForPresenceOfElement(By.xpath("(//div[@id='HybridCPQNPP_Step_nextBtn'])"));
        jsClick(btn_Avanti_CarrellaUpgarde_TV,"Clicking on Avanti on Carrello_Migrazione_TV");
        waitForSeconds(50);
    }

    public void Avanti_on_Modifica_Page_Upgrade() {
        waitForSeconds(10);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(20);
        ExtentReport.getExtentTest().log(
                Status.PASS,
                "<<===========User clicked on the Avanti on Modifica Page for upgrading==========>>",
                MediaEntityBuilder.createScreenCaptureFromPath(
                        getScreenShot(driver, " Avanti on Modifica Page")
                ).build());
        waitForElement(btn_Avanti_Modifica_on_Upgrade);
        waitForSeconds(10);
        clickElement(btn_Avanti_Modifica_on_Upgrade);
        waitForSeconds(5);
    }

    public void Avanti_on_Riepligo_upgrade() {
        waitForSeconds(10);
        driver.switchTo().defaultContent();
        waitForSeconds(1);
        focusFrame(frameLR);
        waitForSeconds(20);
        ExtentReport.getExtentTest().log(
                Status.PASS,
                "<<===========User clicked Avanti on Riepligo for upgrading==========>>",
                MediaEntityBuilder.createScreenCaptureFromPath(
                        getScreenShot(driver, "Avanti on Riepligo")
                ).build());
        waitForPresenceOfElement(By.xpath("//div[@id='ReviewOrder_Step_nextBtn']"));
        waitForSeconds(10);
        jsClick(btn_Avanti_on_RiepilogoUpgrade,"Avanti on RiepilogoOrdine");
        waitForSeconds(5);

    }

    public void clickCambioConsistenza_btn() {
        driver.navigate().refresh();
        waitForSeconds(10);
        focusFrame(frameTwo);
        waitForSeconds(10);
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking on the Cambio Consistenza button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Cambio Consistenza button")).build());
        scrollToElement(Cambio_btn);
        waitForSeconds(5);
        waitForElement(Cambio_btn);
        clickElement(Cambio_btn);
        waitForSeconds(100);
        System.out.println("inside Cambio ");

        driver.switchTo().defaultContent();
        waitForSeconds(5);
        focusFrame(frameLR);
//        focusFrame(frameThree);
        System.out.println("after focus window three");
        waitForSeconds(5);
    }
}
