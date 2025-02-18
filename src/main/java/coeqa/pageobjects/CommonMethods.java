package coeqa.pageobjects;

import com.aventstack.extentreports.Status;
import coeqa.testbase.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ExtentReport;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonMethods extends TestBase {
    public CommonMethods(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }
    @FindBy(xpath = "//span[text()='Continue']")
    @CacheLookup
    WebElement continueBtn;
    @FindBy(id = "continueBtn")
    @CacheLookup
    WebElement continueclick;
    @FindBy(name="Device manufacture")
    @CacheLookup
    WebElement DeviceName;
    @FindBy(id="IMEI")
    @CacheLookup
    WebElement IMEI_NO;
    @FindBy(xpath = "//button[text()='Continue']")
    WebElement Continue;
    @FindBy(id="social-security-number")
    WebElement SSN;
    @FindBy(xpath = "//div[text()='IMEI']")
    WebElement Cusror_out;
    @FindBy(xpath = "//div[text()='Social Security number']")
    WebElement Cusror_out_click;

    public void Continue_button(){
        waitForPageToLoad();
        scrollDown();
        waitForPageToLoad();
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Continue']")));
        jsClick(continueBtn, "Clicking on continue button");
    }
    public void Device_Selection(String Value){
        waitForPageToLoad();
        selectDropDownByValue(DeviceName,Value);
    }
    public void Enter_IMEI(String number){
        waitForPageToLoad();
        input(IMEI_NO,number,"User Entering IMEI number:"+number);
    }
    public void Dummy_Click1(){
        waitForPageToLoad();
        click(Cusror_out,"Clicking outside tha page");
        clickOnElement("//div[text()='IMEI']");
    }
    public void Continue_Click(){
        waitForPageToLoad();
        jsClick(Continue, "Clicking on continue button");
    }
    public void Enter_SSN(String ssn){
        waitForPageToLoad();
        input(SSN,ssn,"User Entering SSN number:"+ssn);
    }
    public void Dummy_Click2(){
        waitForPageToLoad();
        click(Cusror_out_click,"Clicking outside tha page");
    }
    public String verifyURL(String url) {
        String str="";
        try {
            if (containsIgnoreCase(url,"qa1")) {
                log.info(" Title  contains QA");
                ExtentReport.getExtentTest().log(Status.INFO, "QA URL is passed from Excel");
                str="QA";
            }
            else if (containsIgnoreCase(url,"sit1")) {
                log.info(" Title  contains QA");
                ExtentReport.getExtentTest().log(Status.INFO, "SIT URL is passed from Excel");
                str="SIT";
            }
            else if ((containsIgnoreCase(url,"uat1")) || (containsIgnoreCase(url,"stage"))) {
                log.info(" Title  contains UAT");
                ExtentReport.getExtentTest().log(Status.INFO, "UAT URL is passed from Excel");
                str="UAT";
            }
            else {
                log.info(" Title  contains PROD");
                ExtentReport.getExtentTest().log(Status.INFO, "PROD URL is passed from Excel");
                str="PROD";
            }
        }
        catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
            ExtentReport.getExtentTest().log(Status.FAIL, "NO match with giver URLs");
        }
        return str;
    }
    public void clickContinue() throws InterruptedException {
       Thread.sleep(5000);
        WebElement ele =driver.findElement(By.id("continueBtn"));
//        expWait().until(ExpectedConditions.visibilityOf(continueclick));
        scrollToElement(ele);
        expWait().until(ExpectedConditions.visibilityOf(ele));
//        jsClick(continueclick, "Continue Button");
        jsClick(ele, "Continue Button");
    }

    /**
     * This methos is for offer selection
     * @param no
     */
    public void SelectOffer(String no){
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='apply-button']")));
        WebElement Applybtn = driver.findElement(By.xpath("//button[text()='APPLY']"));
        click(Applybtn, "Apply button is clicked");
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//span[@class='cmp-offer__button-slider'])["+no+"]")));
        WebElement Offer = driver.findElement(By.xpath("(//span[@class='cmp-offer__button-slider'])["+no+"]"));
        jsClick(Offer, "Apply button is clicked");
    }
    public String getTimestamp(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String time =String.valueOf(dtf.format(now));
        return time;
    }
}
