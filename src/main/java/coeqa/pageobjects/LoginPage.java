package coeqa.pageobjects;

import coeqa.testbase.TestBase;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ExtentReport;

import java.awt.*;
import java.time.Duration;

public class LoginPage extends TestBase {
    String pageName;
    Robot robot;
    @FindBy(xpath = "//input[@name='username']")
    WebElement UserName;
    @FindBy(xpath = "//input[@name='password']")
    WebElement Password;
    @FindBy(xpath = "//input[@type='submit']")
    WebElement Submit;
    @FindBy(xpath = "//span[contains(text(),'New C')]")
    WebElement NewCRM;
    @FindBy(id = "forgot_password_link")
    WebElement text;
    //(//span[text()='pdvapmcollam'])[1]
//    @FindBy(xpath = "(//span[text()='pdv-collbb'])[1]")
//    WebElement pdv_btn;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        pageName = this.getClass().getSimpleName();

    }

    public void enterLoginCredential(String username, String pwd)  {
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User entering login credentials==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "Login Credential")).build());
        jsInput(UserName, username, "UserName Textbox");
        input(Password, pwd, "Password Textbox");
    }
    public void submitButton(){
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking on the submit button==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "submit")).build());
        jsClick(Submit,"User clicking on the submit button");
    }
    public void newCRM(){
        waitForPageToLoad();
//        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='New CRM']")));
        ExtentReport.getExtentTest().log(Status.PASS, "<<===========User clicking on the New CRM Tab==========>", MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, "Arcadia" + "New CRM tab")).build());
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'New C')]")));
        jsClick(NewCRM,"User clicking on new CRM tab");
    }
    public void click_Pdd_btn(String ENVIRONMENT) {
        waitForPageToLoad(String.valueOf(10));// IJ
        String url;
        String loginButtonXpath ;
        if (ENVIRONMENT.equals("ST")) {
            url = "https://pdvapmcollam.sky.local";
            loginButtonXpath = "(//span[text()='pdv-collbb'])[1]";// Example XPath for ST Login button
        } else if (ENVIRONMENT.equals("IT")) {
            url = "https://pdv-collbb.sky.local"; // Example URL for IT environment
            loginButtonXpath = "(//span[text()='pdvapmcollam'])[1]";  // Example XPath for IT Login button
        } else {
            throw new IllegalArgumentException("Invalid environment: " + ENVIRONMENT);
        }
        waitForSeconds(20);  // Increase time if needed
        try {
           driver.findElement(By.xpath(loginButtonXpath)).click();

        } catch (TimeoutException e) {
            throw new NoSuchElementException("Login button not found for environment: " + ENVIRONMENT, e);
        }
    }
    public void newTab(){
        switchToChildTab();
        log.info("=================="+text.getText());
    }


}
