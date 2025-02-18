package coeqa.testbase;

import com.aventstack.extentreports.Status;
import com.google.common.base.Function;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.lang3.StringUtils;
import org.jfree.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.*;
import utils.ExcelActions;
import utils.ExtentReport;
import utils.HelperClass;
import utils.WaitForProxyElement;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.NoSuchFileException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.testng.Assert.assertEquals;

public class TestBase extends HelperClass {
    // Global Var

    String userDir;
    String strOS;

    ExcelActions excelActions = new ExcelActions();

    //    Line 55 Added by pavithra
    protected final WebDriverWait wait;

    // Constructor
    boolean flag = true;

    public TestBase(WebDriver driver) {
        super(driver);
        this.driver = driver;
        //        Line 64 Added by pavithra
        wait = new WebDriverWait(this.driver, 10);
        init();

    }

    public WebDriver getDriver() {
        return driver;
    }

    public static String captureBase64(WebDriver driver) throws IOException {
        String encodedBase64 = null;
        FileInputStream fileInputStream = null;
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File Dest = new File("/Screencapture/" + System.currentTimeMillis() + ".png");
        String errflpath = Dest.getAbsolutePath();
        FileUtils.copyFile(scrFile, Dest);

        try {

            fileInputStream = new FileInputStream(Dest);
            byte[] bytes = new byte[(int) Dest.length()];
            fileInputStream.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));

        } catch (FileNotFoundException e) {
            Log.info(e);
        }
        return "data:image/png;base64," + encodedBase64;

    }

    public static String capture(WebDriver driver) throws IOException {

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File Dest = new File("/ScreenCapture/" + System.currentTimeMillis() + ".png");
        String errflpath = Dest.getAbsolutePath();
        FileUtils.copyFile(scrFile, Dest);
        return errflpath;
    }

    private static JSONArray getPerfEntryLogs(WebDriver driver) {
        LogEntries logEntries = driver.manage().logs().get(LogType.PERFORMANCE);
        JSONArray perfJsonArray = new JSONArray();
        AtomicReference<JSONObject> messageJSON = null;
        logEntries.forEach(entry -> {
            messageJSON.set(new JSONObject(entry.getMessage()).getJSONObject("message"));
            perfJsonArray.put(messageJSON);
        });

        LogEntries logEntries1 = driver.manage().logs().get(LogType.BROWSER);
        logEntries1.forEach(entry -> {
            JSONObject messageJSON1 = new JSONObject(entry.getMessage()).getJSONObject("message");
        });
        List<LogEntry> le = logEntries1.getAll();
        for (LogEntry l : le) {
            System.out.println(l.getMessage());
        }
        return perfJsonArray;
    }

    public void init() {
        try {
            userDir = System.getProperty("user.dir");

            String os = System.getProperty("os.name").toLowerCase();
            if (!os.contains("win")) {
                strOS = "Windows";

            } else {
                strOS = "Linux";
            }

        } catch (Exception e) {
            log.info("Exception occurred: " + e.getMessage());
        }
    }

    public void refresh() {
        driver.navigate().refresh();
    }

    public void getUrl(String url) {
        // driver.get(FileReaderManager.fileRead().getConfiguration().getURL())

        log.info("Launching the url " + url);

        driver.get(url);
        handlePrivacyFAIL();
        // handlePrivacyFAIL();
        ExtentReport.getExtentTest().log(Status.PASS, "Launching the url '" + url + "'");
    }

    public void verifyPageTitle(String arg) {
        waitForPageToLoad();

        String actualTitle;
        try {
            String expectedTitle = config.getProperty(arg);
            waitForPageToLoad();
            actualTitle = driver.getTitle();
            log.info("act received : " + actualTitle);
            log.info("exp received : " + expectedTitle);

            if (actualTitle.contains(expectedTitle)) {
                log.info(" Title  is verified successfully");
                ExtentReport.getExtentTest().log(Status.PASS, "Verified Page Title : '" + expectedTitle + "'");
            } else if (actualTitle.replaceAll("\\p{Pd}", "-").contains(expectedTitle)) {

                log.info(expectedTitle + " is verified successfully");
                ExtentReport.getExtentTest().log(Status.PASS, "Verified Page Title : '" + expectedTitle + "'");
            } else {
                log.info(actualTitle + " not contains " + expectedTitle);
                ExtentReport.getExtentTest().log(Status.FAIL, "Page Title is not matching with : '" + expectedTitle + "'");
            }
        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
            ExtentReport.getExtentTest().log(Status.FAIL, "Not able to get Title");
        }
    }

    public void openNewTab(WebElement identifier, String sdesc) {
        try {
            waitForPageToLoad();
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.elementToBeClickable(identifier));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].scrollIntoView();", identifier);
            ExtentReport.getExtentTest().log(Status.PASS, "Clicked on " + sdesc + " successfully");
            identifier.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
            log.info("opening on new tab");
        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());

        }
    }

    public void waitForNewTab() {
        int count = 0;

        while (driver.getWindowHandles().size() != 2) {
            try {
                new WebDriverWait(driver, 1);
                count++;
                if (count == 30) {
                    break;
                }
                log.info("Delayed by time" + count + " sec.");
            } catch (Exception e) {
                log.info("Exception occurred : " + e.getMessage());
            }

        }
    }

    public List<String> switchToChildTab() {
        waitForNewTab();

        List<String> tab = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tab.get(1));
        return tab;
    }

    public void switchToParentTab(List<String> tab) {
        driver.close();
        driver.switchTo().window(tab.get(0));

    }

    public String getLocName(WebElement arg) {

        return (arg.toString().split(":")[2]).replace("]]", "]");
    }

    /*
     * public void clickAndRedirectTab(WebElement identifier, String sdesc,
     * String expURL, String expTit) { try {
     *
     *
     * log.info(getLocName(identifier)); openNewTab(identifier, sdesc);
     * List<String> tab = switchToChildTab(); verifyPageURL(expURL);
     * verifyPageTitle(expTit); switchToParentTab(tab);
     *
     *
     * } catch (Exception e) { log.info("Exception occurred : " +
     * e.getMessage()); } }
     */
    public Date date() {
        return new Date();
    }

    @SuppressWarnings("deprecation")
    public void waitForCBPageToLoad() {
        log.info("Waiting for page load");
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(10, TimeUnit.SECONDS).pollingEvery(10, TimeUnit.SECONDS).ignoreAll(Arrays.asList(StaleElementReferenceException.class, InvalidElementStateException.class, WebDriverException.class, NoSuchElementException.class, ElementNotVisibleException.class, TimeoutException.class));

            wait.until((Function<WebDriver, WebElement>) driver1 -> driver.findElement(By.cssSelector("[class='page basicpage']")));

            log.info("page loaded");
        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());

        }
    }

    public void waitforActionLoader() {
        log.info("Waiting for ActionLoader");
        /*
         * try { log.info(driver.getCurrentUrl().contains(
         * "http://roe-disp1-sit1.ncw.webapps.rr.com/"));
         * if(driver.getCurrentUrl().contains(
         * "http://roe-disp1-sit1.ncw.webapps.rr.com/")) {
         * Log.info("inside waitforActionLoader and curr url is "+driver.
         * getCurrentUrl()); Wait<WebDriver> wait = new
         * FluentWait<>(driver).withTimeout(20,
         * TimeUnit.SECONDS).pollingEvery(3, TimeUnit.SECONDS)
         * .ignoreAll(Arrays.asList(StaleElementReferenceException.class,
         * InvalidElementStateException.class, WebDriverException.class,
         * NoSuchElementException.class, ElementNotVisibleException.class,
         * TimeoutException.class));
         *
         * //wait.until((Function<WebDriver, WebElement>) driver1 ->
         * driver.findElement(By.cssSelector("[id='utag_charter.marketing_899']"
         * ))); WebElement element=driver.findElement(By.
         * cssSelector("[class='loading-container fixed-loader'] ~  div.basicpage"
         * ));
         * wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(
         * element)));
         *
         *//*
         * WebElement element=driver.findElement(By.
         * cssSelector("[class='loading-container fixed-loader'] ~  div.basicpage"
         * )); wait(10).until(ExpectedConditions.not(ExpectedConditions.
         * visibilityOf(element)));
         *//*
         * }
         *
         *//*
         * boolean timer = elementDisplayed(driver.findElement(By.
         * cssSelector("[class='loading-container fixed-loader'] ~  div.basicpage"
         * ))); int counter = 0; while (timer) {
         *
         * try { Thread.sleep(1000); counter++; if (counter > 30) break;
         * } catch (Exception e) { e.printStackTrace(); } }
         *//*
         *
         * log.info("page loaded"); } catch (Exception e) {
         * log.info("Exception occurred : " + e.getMessage());
         *
         * }
         */
        try {
            while (driver.findElements(By.cssSelector("[class='loading-container fixed-loader'] ~  div.basicpage")).size() > 0)
                Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    @SuppressWarnings("deprecation")
    public  void waitForPageToLoad() {

        log.info("Waiting for page load");
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(10, TimeUnit.SECONDS).pollingEvery(10, TimeUnit.SECONDS).ignoreAll(Arrays.asList(StaleElementReferenceException.class, InvalidElementStateException.class, WebDriverException.class, NoSuchElementException.class, ElementNotVisibleException.class, TimeoutException.class));

            wait.until((Function<WebDriver, WebElement>) driver1 -> driver.findElement(By.cssSelector("iframe.aamIframeLoaded")));
            log.info("page loaded");
        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
        }
    }

    public void waitForUEPageToLoad() {

        log.info("Waiting for page load");
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(10, TimeUnit.SECONDS).pollingEvery(10, TimeUnit.SECONDS).ignoreAll(Arrays.asList(StaleElementReferenceException.class, InvalidElementStateException.class, WebDriverException.class, NoSuchElementException.class, ElementNotVisibleException.class, TimeoutException.class));

            wait.until((Function<WebDriver, WebElement>) driver1 -> driver.findElement(By.cssSelector("[id='utag_charter.marketing_899']")));
            log.info("page loaded");
        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
        }
    }

    public void verifyPageURL(String arg) {
        String actualURL;
        String expectedURL = config.getProperty(arg);
        log.info("verifying the url " + expectedURL);
        try {
            actualURL = driver.getCurrentUrl();
            // Assert.assertEquals(actualURL, expectedURL)
            if (actualURL.contains(expectedURL)) {
                log.info(expectedURL + " is verified successfully");
                ExtentReport.getExtentTest().log(Status.PASS, "Verified the Landing Page URL : '" + expectedURL + "'");

            } else {
                log.info(actualURL + " not contains " + expectedURL);
                ExtentReport.getExtentTest().log(Status.FAIL, actualURL + " not contains " + expectedURL);
            }
            log.info(expectedURL + " is verified successfully");

        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
            ExtentReport.getExtentTest().log(Status.FAIL, "Not able to fetch the URL");
        }
    }

    public String getDate() {
        return getCurrentTime();
    }

    public String getCurrentTime() {
        Date date = date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");
        String strDate = formatter.format(date);
        log.info("strDate:" + strDate);
        return strDate;

    }

    public void verifyText(String sectionName, WebElement element, String arg) {
        String actualText;
        String expectedText;

        if (config.getProperty(arg) != null) {

            expectedText = config.getProperty(arg);
        } else {
            expectedText = arg;
        }

        log.info("verifying the Text " + expectedText);
        try {
            actualText = element.getText();
            if (actualText.contains(expectedText)) {
                log.info(expectedText + " is verified successfully");
                ExtentReport.getExtentTest().log(Status.PASS, sectionName + "_Verified the text '" + actualText + "' with '" + expectedText + "'");

            } else {
                log.info(actualText + " not contains " + expectedText);
                ExtentReport.getExtentTest().log(Status.FAIL, sectionName + "_" + actualText + " not contains " + expectedText);

                throw new Exception();
            }
            log.info(expectedText + " is verified successfully");

        } catch (Exception e) {

            log.info("Exception occurred : " + e.getMessage());

        }

    }

    public void verifyText(String expectedText, String actualText) {
        log.info("verifying the Text " + expectedText);
        try {
            if (actualText.contains(expectedText)) {
                log.info(expectedText + " is verified successfully");
                ExtentReport.getExtentTest().log(Status.PASS, "Verified the text '" + actualText + "' with '" + expectedText + "'");

            } else {
                log.info(actualText + " not contains " + expectedText);
                ExtentReport.getExtentTest().log(Status.FAIL, actualText + " not contains " + expectedText);
            }
            log.info(expectedText + " is verified successfully");

        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
            ExtentReport.getExtentTest().log(Status.INFO, "Not able to fetch the URL");

        }

    }

    public void scrollTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
    }

    public JavascriptExecutor getJS() {
        return (JavascriptExecutor) driver;
    }

    public  WebDriverWait expWait() {
        return new WebDriverWait(driver, 20, 10);
    }

    public WebDriverWait expWait(int time) {
        return new WebDriverWait(driver, time);
    }

    public boolean elementDisplayed(WebElement element) {
        boolean val;

        try {

            val = element.isDisplayed();
            log.info("element displayed : " + val);

            return val;
        } catch (NoSuchElementException ig) {
            // log.info("Exception occurred : " + e.getMessage())

            return false;
        }

    }

    public void verifyExpdCollapse(WebElement element, String desc) {
        waitForCBPageToLoad();
        if (elementDisplayed(element)) {
            click(element, desc);
        }

    }

    public void handleContinue(WebElement element, String sdesc) {
        try {
            element.click();
            ExtentReport.getExtentTest().log(Status.PASS, "Clicked on " + sdesc + " successfully");
        } catch (org.openqa.selenium.StaleElementReferenceException ex) {
            element.click();
            element.click();

            log.info("Exception :" + ex + " occurred on element ->" + element);
            ExtentReport.getExtentTest().log(Status.WARNING, "Element not clickable " + element);

        }

    }

    public void click(WebElement element, String sdesc) {
        try {

            //     log.info(element);
            //       fluentWait(element);
            // Thread.sleep(5000);
     /*       expWait().until(ExpectedConditions.visibilityOf(element));
            expWait().until(new WaitForProxyElement(element));
            expWait().until(ExpectedConditions.visibilityOf(element));*/
            log.info("Clicking on element ->" + element);
            fluentWaitElementClickable(element);
            element.click();
            // waitforActionLoader();
            ExtentReport.getExtentTest().log(Status.PASS, "Clicked on " + sdesc + " successfully");
            log.info("Clicked on element ->" + element);

        } catch (Exception t) {
            log.info("Exception :" + t + " occurred on element ->" + element);
            ExtentReport.getExtentTest().log(Status.INFO, "Element not clickable " + element);

        }

    }

    @SuppressWarnings("deprecation")
    public void fluentWait(WebElement element) {
        try {
            log.info("Waiting for " + element);
            Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(60, TimeUnit.SECONDS)// change
                    // here
                    .pollingEvery(10, TimeUnit.SECONDS).ignoreAll(Arrays.asList(StaleElementReferenceException.class, InvalidElementStateException.class, WebDriverException.class, NoSuchElementException.class, ElementNotVisibleException.class, TimeoutException.class));

            wait.until((Function<WebDriver, WebElement>) input -> element);
            log.info("waiting completed");
        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
            ExtentReport.getExtentTest().log(Status.FAIL, "WebElement  " + element + " is not present");

        }
    }

    public void Robot_class_handling() throws InterruptedException, AWTException {
        Thread.sleep(5000);
        System.out.println("page Title==>" + driver.getCurrentUrl());
        Robot robot = new Robot();
        //Thread.sleep(4000);
        robot.keyPress(KeyEvent.VK_ESCAPE);
    }

    public void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void pressTab(WebElement element) {
        try {
            expWait().until(ExpectedConditions.visibilityOf(element));
            element.sendKeys(Keys.TAB);
        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
        }
    }

    public void scrollToElement(WebElement element) {
        log.info("verifying the Element " + element);
        try {

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(500);
        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
        }
    }



    public  void jsClick(WebElement element, String sdesc) {

        try {
            fluentWaitElementClickable(element);
            log.info("Clicking on :" + element.getText());

            //     expWait().until(new WaitForProxyElement(element));
            //      expWait().until(ExpectedConditions.visibilityOf(element));

            /*
             * JavascriptExecutor executor = (JavascriptExecutor)driver;
             * executor.executeScript("arguments[0].scrollIntoView(true);",
             * element);
             */

            scrollToElement(element);
            //    waitforActionLoader();
            getJS().executeScript("arguments[0].click();", element);

            ExtentReport.getExtentTest().log(Status.PASS, "Clicked on  " + sdesc + " successfully");

        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
            ExtentReport.getExtentTest().log(Status.FAIL, "Unable to click on" + element);

        }

    }
    // public void handle_LaunchersessionL() {
    // String title = driver.getTitle();
    // log.info("Title: " + title);
    // try {
    // if (title.contains("launcher.session")) {
    // log.info("Privacy page found: " + title);
    // expWait().until(ExpectedConditions.visibilityOf(driver.findElement(By.id("details-button"))));
    // driver.findElement(By.id("details-button")).click();
    // driver.findElement(By.id("proceed-link")).click();
    // log.info("Privacy page handled successfully: " + title);
    // }
    // Thread.sleep(2000);
    // }catch ( Exception e){
    // e.printStackTrace();
    // }
    //
    //
    // }

    public void jsClickWithTimeRecord(WebElement element, String sdesc, String desc) {

        try {
            fluentWaitElementClickable(element);
            log.info("Clicking on :" + element.getText());
            scrollToElement(element);
            String ele = getAllElementsInThePage();
            getJS().executeScript("arguments[0].click();", element);

            fRecordLoadTimeForPageLoadUsingTimeStamp(desc, ele);

            ExtentReport.getExtentTest().log(Status.PASS, "Clicked on  " + sdesc + " successfully");
        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
            ExtentReport.getExtentTest().log(Status.FAIL, "Unable to click on" + element);

        }

    }

    public void input(WebElement element, String value, String sdesc) {
        try {
            //	expWait().until(new WaitForProxyElement(element));
            //  expWait().until(ExpectedConditions.visibilityOf(element));
            //	elementDisplayed(element);
            //  boolean flag = false;
            fluentWaitElementClickable(element);
            //   System.out.println("Element is clickable : " + flag);
            //   log.info("Element is clickable : " + flag);

            log.info("Entering value : " + value + " to element : " + element);
            element.sendKeys(value);
            ExtentReport.getExtentTest().log(Status.PASS, "Entering the value " + value + " in " + sdesc + " successfull");
        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
            ExtentReport.getExtentTest().log(Status.FAIL, "Unable to enter the " + value + " on " + element);
            driver.close();
        }
    }

    public void verifyPhoneNo(String exp) {

        scrollTop();
        WebElement element = driver.findElement(By.cssSelector("div.ribbon-wrapper p"));
        expWait(30).until(ExpectedConditions.visibilityOf(element));
        verifyText(element.getText(), exp);
    }

    public void handlePrivacyFAIL() {
        String title = driver.getTitle();
        log.info("Title: " + title);
        try {
            if (title.contains("Privacy") || (title.contains("RESI-COM") || getCurrentUrl().contains("roe-disp1-qa1.ncw"))) {
                log.info("Privacy page found: " + title);
                expWait().until(ExpectedConditions.visibilityOf(driver.findElement(By.id("details-button"))));
                driver.findElement(By.id("details-button")).click();
                driver.findElement(By.id("proceed-link")).click();
                log.info("Privacy page handled successfully: " + title);
            }
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getTitle() {
        String title = null;
        try {
            log.info("current title ->" + driver.getTitle());
            title = driver.getTitle();

        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());

        }
        return title;
    }

    public void jsInput(WebElement element, String value, String sdesc) {
        try {
            //   expWait().until(ExpectedConditions.visibilityOf(element));
            fluentWaitElementClickable(element);
            System.out.println("Element is clickable : " + flag);
            log.info("Element is clickable : " + flag);
            getJS().executeScript("arguments[0].value='" + value + "';", element);
            ExtentReport.getExtentTest().log(Status.PASS, "Enter the value " + value + "on" + sdesc + " successfully");

        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
            ExtentReport.getExtentTest().log(Status.FAIL, "Unable to enter the " + value + "on" + element);
        }
    }

    //Added by abinesh
    public void inputText(WebElement element, String value, String sdesc) {
        try {
            fluentWaitElementClickable(element);
            System.out.println("Element is clickable: " + flag);
            log.info("Element is clickable: " + flag);
            element.clear();
            // Enter the value in the text box
            element.sendKeys(value);
            ExtentReport.getExtentTest().log(Status.PASS, "Entered the value '" + value + "' on " + sdesc + " successfully");

        } catch (Exception e) {
            log.info("Exception occurred: " + e.getMessage());
            ExtentReport.getExtentTest().log(Status.FAIL, "Unable to enter the value '" + value + "' on " + sdesc);
        }
    }


    public String getText(WebElement element) {
        String data = null;
        try {
            expWait().until(new WaitForProxyElement(element));
            expWait().until(ExpectedConditions.visibilityOf(element));
            data = element.getText();
        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
        }
        return data;

    }

    public String getAttr(WebElement element, String attr) {

        return element.getAttribute(attr);
    }

    public void selectDropDownByValue(WebElement element, String value) {

        try {
            log.info("selecting by value: " + value + " from drop Down List");

            new Select(element).selectByValue(value);

            ExtentReport.getExtentTest().log(Status.PASS, "Selecting by the value: " + value + " successful");

        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
            ExtentReport.getExtentTest().log(Status.FAIL, "Unable to select by value : " + value);
        }

    }

    public void selectDropDownByIndex(WebElement element, int index) {

        try {
            log.info("selecting " + index + " from drop Down List");
            new Select(element).selectByIndex(index);

            ExtentReport.getExtentTest().log(Status.PASS, "Selecting by the index : " + index + " successful");

        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
            ExtentReport.getExtentTest().log(Status.FAIL, "Unable to select by index : " + index);
        }
    }

    public void selectDropDownByText(WebElement element, String visibleText) {

        try {
            log.info("selecting by visible text: " + visibleText + " from drop Down List");
            new Select(element).selectByVisibleText(visibleText);
            ExtentReport.getExtentTest().log(Status.PASS, "Selecting by the visible text : " + visibleText + "on successfully");

        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
            ExtentReport.getExtentTest().log(Status.FAIL, "Unable to select by visible text : " + visibleText + "on successfully");
        }
    }

    public void verifyPhoneNumber(String exp, String sdesc) {
        try {
            waitForCBPageToLoad();
            WebElement element = driver.findElement(By.cssSelector("[data-linkname='18002331231']"));
            String act = element.getText();
            assertEquals(exp, act, "true");
            log.info(" -> Fetch text from element " + element.getText());
            ExtentReport.getExtentTest().log(Status.PASS, "Validated Phone number on " + sdesc + " successfully");

        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
            ExtentReport.getExtentTest().log(Status.FAIL, "Not able to validate the text on " + sdesc + " successfully");

        }

    }

    public void mouseHover(WebElement element) {

        try {

            Actions action = new Actions(driver);
            action.moveToElement(element).build().perform();

        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
        }
    }

    public boolean containsIgnoreCase(String exp, String act) {

        return StringUtils.containsIgnoreCase(exp, act);

    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    public String getPageSource() {
        return driver.getPageSource();
    }

    public void get(String val) {
        driver.get(val);
    }
    //// ========67++
    /// =========ajeesh code===========
    // public void verifyminicartprice(String exp, String sdesc) {
    // try {
    // waitForCBPageToLoad();
    // WebElement element =
    //// driver.findElement(By.cssSelector("[data-linkname='18002331231']"));
    // String act = element.getText();
    // assertEquals(exp, act, "true");
    // log.info(" -> Fetch text from element " + element.getText());
    // ExtentReport.getExtentTest().log(Status.PASS, "Validated Phone number on
    //// " + sdesc + " successfully");
    //
    // } catch (Exception e) {
    // log.info("Exception occurred : " + e.getMessage());
    // ExtentReport.getExtentTest().log(Status.FAIL, "Not able to validate the
    //// text on " + sdesc + " successfully");
    //
    // }

    // }

    public String getScreenshotPath(String screenShotName) {
        return getScreenShot(driver, screenShotName);
    }

    public void waitForPageToLoad(String arg) {
        try {

            int counter = 1;
            flag = containsIgnoreCase(driver.getCurrentUrl(), arg);
            if (flag) {
                flag = false;
            }
            // while (containsIgnoreCase(driver.getCurrentUrl(), arg)) {
            // noinspection ConstantConditions
            System.out.println("current url +" + getCurrentUrl());
            while (containsIgnoreCase(driver.getCurrentUrl(), arg)) {
                Thread.sleep(1000);
                counter++;
                // System.out.println("counter va");
                if (counter > 15) {
                    flag = false;
                    break;

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verifyMiniCart(String testData) {
        WebElement onMiniCart = driver.findElement(By.cssSelector("div.price-component > [aria-expanded='false']"));
        expWait().until(ExpectedConditions.visibilityOf(onMiniCart));
        click(onMiniCart, "expanding on mini cart");
        WebElement closeMiniCart = driver.findElement(By.cssSelector("div.price-component > [aria-expanded='false']"));
        click(closeMiniCart, "collapsing mini cart");

    }

    public void buildbundle(String name) {
        //waitForCBPageToLoad();
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'" + name + "')]")));
        WebElement element = driver.findElement(By.xpath("//div[contains(text(),'" + name + "')]"));

        if (elementDisplayed(element)) {
            //	waitForPageToLoad();
            jsClick(element, name + "Tab is clicked");
            waitForPageToLoad();
            ExtentReport.getExtentTest().log(Status.PASS, name + "Tab is been clicked");
        } else {
            ExtentReport.getExtentTest().log(Status.FAIL, name + "Tab is not clicked");
        }
    }

    public void BYOBOffer(String Offerid) {
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='apply-button']")));
//		WebElement element = driver.findElement(By.xpath("//div[contains(text(),'" + name + "')]"));*/
        WebElement Applybtn = driver.findElement(By.xpath("//button[@class='apply-button']"));

        click(Applybtn, "Apply button is clicked");
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='" + Offerid + "']//span")));
        WebElement Offercard = driver.findElement(By.xpath("//div[@id='" + Offerid + "']//span"));
        if (elementDisplayed(Offercard)) {
            jsClick(Offercard, Offerid + " is Selected");
            ExtentReport.getExtentTest().log(Status.PASS, Offerid + " Offercard is selected");

        } else {
            ExtentReport.getExtentTest().log(Status.FAIL, Offerid + "Offercard is not selected");
        }
    }


    public void SelectApt(String name, WebElement btn) {
        try {
//            waitForCBPageToLoad();
            expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='cmp-select-apartment__entry-card row']/div/p[text()='" + name + "']")));
            WebElement element = driver.findElement(By.xpath("//div[@class='cmp-select-apartment__entry-card row']/div/p[text()='" + name + "']"));
            fluentWaitElementClickable(element);
            scrollToElement(element);
            if (elementDisplayed(element)) {
                jsClick(element, name + "APT is clicked");
                waitForPageToLoad();
                jsClick(btn, "Continue Btn");
                ExtentReport.getExtentTest().log(Status.PASS, name + "Address has been selected");
            }
        } catch (Exception ex) {
            jsClick(btn, "Continue Btn");
            log.info("Exception :" + ex.getMessage() + " occurred on element ->" + btn);
            ExtentReport.getExtentTest().log(Status.WARNING, "Element not clickable " + btn);

        }
    }

    public void waitforSpinnerToComplete() {
        WebElement ele;

        try {
            //Thread.sleep(3000);
            expWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class=\"loading-container fixed-loader\"]")));
            ele = driver.findElement(By.cssSelector("[class=\"loading-container fixed-loader\"]"));
//            while (ele.isDisplayed()) {
//                log.info("Spinner is running");
//                if (expWait().until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(ele))))
//                    ele = driver.findElement(By.cssSelector("[class=\"loading-container fixed-loader\"]"));
//            }
        } catch (StaleElementReferenceException stale) {
            log.info("Stale");
        } catch (TimeoutException t) {
            log.info("Spinner not started");
        } catch (NoSuchElementException nse) {
            log.info("Spinner not found/stopped");
        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public void clickOnElement(String xPath) {
        waitForCBPageToLoad();
        expWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
        WebElement element = driver.findElement(By.xpath(xPath));
        if (elementDisplayed(element)) {
            jsClick(element, "Pop up is closed");
            //ExtentReport.getExtentTest().log(Status.PASS,"pop up is closed");
        } else {
            //ExtentReport.getExtentTest().log(Status.FAIL,"pop up is opened");
            log.info("pop up is opened");
        }
    }
//
//    public static void waitForSeconds(int seconds) {
//        try {
//            Thread.sleep(seconds * 1000L); // Convert seconds to milliseconds
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public double getFormattedString(double num) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.CEILING);
        return Double.parseDouble(df.format(num));
    }

    public void clearcache() throws AWTException {
        Robot robot = new Robot();


        driver.manage().deleteAllCookies();
        driver.navigate().to("chrome://settings/clearBrowserData");


        //driver.findElement(By.xpath("//settings-ui")).sendKeys(Keys.ENTER);
//		driver.switchTo().activeElement();
//		driver.findElement(By.xpath("//settings-ui")).sendKeys(Keys.ENTER);

        for (int i = 1; i < 8; i++) {
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public String[] String_spliting(String str) {
        log.info("filter string==>" + str);
        String[] arr = str.split("/");
//    	log.info("arr[0]==>"+arr[0]+"===>"+arr[1]);
//    	log.info("arr[1]==>"+arr[1]);
        return arr;
    }

    public void Get_session_ID() {
        SessionId s = ((RemoteWebDriver) driver).getSessionId();
        ExtentReport.getExtentTest().log(Status.PASS, "Session ID generated ==>" + s);
        System.out.println("Session Id is:======> " + s);
    }

    public String[] Filter_spliting(String str) {
        log.info("filter string==>" + str);
        String[] arr = str.split("\\+");
        return arr;
    }

    public String getpagesource() {
        return driver.getPageSource();
    }

    public String getScreenshotPath1(String screenShotName) {
        return getScreenShot(driver, screenShotName);
    }


    public WebElement relocateWebElement(By by, WebElement element) {
        try {

            //       expWait().ignoreAll(Collections.singleton(NoSuchElementException.class)).until(rerefreshed(visibilityOf(element)));

            log.info(("Element is available. ").concat(element.toString()));

        } catch (NoSuchElementException exception) {

            log.info(exception.getMessage());
        }

        return driver.findElement(by);
    }

    public String FindFilepath(String FileName) {
        File file = new File(FileName);
        String path = file.getAbsolutePath();
        return path.toString();
    }

    public void RedirectAndLaunchURL(String url) throws InterruptedException {
        Thread.sleep(3000);
        driver.getWindowHandles().forEach(tab -> driver.switchTo().window(tab));
        String currenturl = driver.getCurrentUrl();
        log.info("current url==>" + currenturl);
        String[] arr = currenturl.split("\\?", 2);
        String urlwithtoken = url + arr[1];
        log.info("URL with Token ==>" + urlwithtoken);
        driver.get(urlwithtoken);
    }

//*****************************************

    public String getAllElementsInThePage() {
        String ele = driver.findElement(By.tagName("html")).getText();
        return ele;
    }

    public void fRecordLoadTimeForSpinner(final String loadTimeKey) throws Exception {

        StopWatch timer = new StopWatch();
        timer.start();
        Thread.sleep(500);
        boolean checkClickStatus = false;
        try {
            for (int i = 1; i <= 180; i++) {
                //     Thread.sleep(500);
                checkClickStatus = driver.findElement(By.cssSelector("[class=\"loading-container fixed-loader\"]")).isDisplayed();
                if (!checkClickStatus) {
                    timer.stop();
                }
            }
        } catch (Exception e) {
            timer.stop();
        }

        long stdMS = 1000;
        long timeInMS = timer.getTime();
        double timeInSec = ((double) timeInMS / stdMS);
        String timeInMSStr = String.valueOf(timeInSec);
        System.out.println("timeInMSStr " + timeInMSStr);
        ExcelActions.writeValueToExcel(loadTimeKey, timeInMSStr);
    }

    public void fRecordTimeForElementClickable(final String loadTimeKey, WebElement element) throws Exception {

        StopWatch timer = new StopWatch();
        timer.start();
        //   Thread.sleep(500);
        boolean checkClickStatus = false;
        System.out.println("checkClickStatus " + checkClickStatus);
        try {
            WebDriverWait wait = new WebDriverWait(driver, 90);
            for (int i = 1; i <= 180; i++) {
                Thread.sleep(250);
                checkClickStatus = wait.until(ExpectedConditions.elementToBeClickable(element)).isDisplayed();
                //  checkClickStatus = driver.findElement(By.cssSelector("[class=\"loading-container fixed-loader\"]")).isDisplayed();
                if (checkClickStatus == true) {
                    timer.stop();
                    break;
                }
                System.out.println("checkClickStatus " + i + ":" + checkClickStatus);
            }
            System.out.println("checkClickStatus " + checkClickStatus);
        } catch (Exception e) {
            timer.stop();
        }

        long stdMS = 1000;
        long timeInMS = timer.getTime();
        double timeInSec = ((double) timeInMS / stdMS);
        String timeInSStr = String.valueOf(timeInSec);
        System.out.println("timeInSStr " + timeInSStr);
        ExcelActions.writeValueToExcel(loadTimeKey, timeInSStr);
    }

    public void fRecordLoadTimeForPageLoad(final String loadTimeKey) throws Exception {

        StopWatch timer = new StopWatch();
        timer.start();
        //   Thread.sleep(500);
        String ele = driver.findElement(By.tagName("html")).getText();
        System.out.println("html element is : ele " + ele);

        boolean checkLoadStatus = false;
        System.out.println("checkLoadStatus " + checkLoadStatus);
        try {

           /* FluentWait wait = new FluentWait(driver);
            wait.withTimeout(180, TimeUnit.SECONDS);
            wait.pollingEvery(500, TimeUnit.MILLISECONDS);
            List list = new ArrayList();
            list.add(NoSuchElementException.class);
            list.add(NoSuchFileException.class);
            wait.ignoreAll(list);*/
            WebDriverWait wait = new WebDriverWait(driver, 120);
            for (int i = 1; i <= 180; i++) {
                Thread.sleep(250);
                checkLoadStatus = wait.until(webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
                //  checkLoadStatus = driver.findElement(By.cssSelector("[class=\"loading-container fixed-loader\"]")).isDisplayed();
                /*if (checkLoadStatus == true) {
                    timer.stop();
                    System.out.println("checkLoadStatus is " + checkLoadStatus);
                    break;
                }*/
                String ele1 = driver.findElement(By.tagName("html")).getText();
                System.out.println("checkLoadStatus " + i + ": " + checkLoadStatus);
                if (!ele.equals(ele1)) {
                    timer.stop();
                    System.out.println("html element is : ele1" + ele1);
                    System.out.println("checkLoadStatus is " + checkLoadStatus);
                    break;
                }
                //  System.out.println("checkLoadStatus "+ i+ ":" + checkLoadStatus);
            }
            System.out.println("checkLoadStatus " + checkLoadStatus);
        } catch (Exception e) {
            timer.stop();
        }


        long stdMS = 1000;
        long timeInMS = timer.getTime();
        double timeInSec = ((double) timeInMS / stdMS);
        String timeInSStr = String.valueOf(timeInSec);
        System.out.println("timeInSStr " + timeInSStr);
        ExcelActions.writeValueToExcel(loadTimeKey, timeInSStr);
    }

    public void fRecordLoadTimeForPageLoad(final String loadTimeKey, String ele) throws Exception {

        StopWatch timer = new StopWatch();
        timer.start();
        //   Thread.sleep(500);
        //   String ele = driver.findElement(By.tagName("html")).getText();
        System.out.println("html element is : ele " + ele);

        boolean checkLoadStatus = false;
        System.out.println("checkLoadStatus " + checkLoadStatus);
        try {

           /* FluentWait wait = new FluentWait(driver);
            wait.withTimeout(180, TimeUnit.SECONDS);
            wait.pollingEvery(500, TimeUnit.MILLISECONDS);
            List list = new ArrayList();
            list.add(NoSuchElementException.class);
            list.add(NoSuchFileException.class);
            wait.ignoreAll(list);*/
            WebDriverWait wait = new WebDriverWait(driver, 90);
            for (int i = 1; i <= 180; i++) {
                Thread.sleep(250);
                checkLoadStatus = wait.until(webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
                //  checkLoadStatus = driver.findElement(By.cssSelector("[class=\"loading-container fixed-loader\"]")).isDisplayed();
                /*if (checkLoadStatus == true) {
                    timer.stop();
                    System.out.println("checkLoadStatus is " + checkLoadStatus);
                    break;
                }*/
                String ele1 = driver.findElement(By.tagName("html")).getText();
                System.out.println("checkLoadStatus " + i + ": " + checkLoadStatus);
                if (!ele.equals(ele1)) {
                    timer.stop();
                    System.out.println("html element is : ele1" + ele1);
                    System.out.println("checkLoadStatus is " + checkLoadStatus);
                    break;
                }
                //  System.out.println("checkLoadStatus "+ i+ ":" + checkLoadStatus);
            }
            System.out.println("checkLoadStatus " + checkLoadStatus);
        } catch (Exception e) {
            timer.stop();
        }


        long stdMS = 1000;
        long timeInMS = timer.getTime();
        double timeInSec = ((double) timeInMS / stdMS);
        String timeInSStr = String.valueOf(timeInSec);
        System.out.println("timeInSStr " + timeInSStr);
        ExcelActions.writeValueToExcel(loadTimeKey, timeInSStr);
    }

    public void fRecordLoadTimeForPageLoadUsingTimeStamp(final String loadTimeKey, String ele) throws Exception {
        long timeStamp1;
        long timeStamp2 = 0;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        timeStamp1 = timestamp.getTime();
        System.out.println("1 st time stamp " + timeStamp1);
        //   Thread.sleep(500);
        //   String ele = driver.findElement(By.tagName("html")).getText();
        System.out.println("html element is : ele " + ele);

        boolean checkLoadStatus = false;
        System.out.println("checkLoadStatus " + checkLoadStatus);
        try {

            WebDriverWait wait = new WebDriverWait(driver, 120);
            for (int i = 1; i <= 180; i++) {
                Thread.sleep(250);
                checkLoadStatus = wait.until(webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
                fluentWaitVisibilityOfElementLocated("tagname", "html");
                String ele1 = getAllElementsInThePage();
                System.out.println("checkLoadStatus " + i + ": " + checkLoadStatus);
                if (!ele.equals(ele1)) {
                    Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
                    timeStamp2 = timestamp1.getTime();
                    System.out.println("2nd time stamp " + timeStamp2);
                    System.out.println("html element is : ele1" + ele1);
                    System.out.println("checkLoadStatus is " + checkLoadStatus);
                    break;
                }
            }
            System.out.println("checkLoadStatus " + checkLoadStatus);
        } catch (Exception e) {
            log.info("error message : " + e.getMessage());
        }


        long stdMS = 1000;
        long timeInMS = timeStamp2 - timeStamp1;
        double timeInSec = ((double) timeInMS / stdMS);
        String timeInSStr = String.valueOf(timeInSec);
        System.out.println("timeInSStr " + timeInSStr);
        ExcelActions.writeValueToExcel(loadTimeKey, timeInSStr);
    }

    public void fRecordLoadTimeForPageLoad(String loadTimeKey, String locatorType, String path, long timeStamp1) throws Exception {
        //   long timeStamp1;
        long timeStamp2 = 0;

        try {

           /* Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            timeStamp1 = timestamp.getTime();*/
            Thread.sleep(2000);
            System.out.println("1 st time stamp " + timeStamp1);
            boolean flagCheck = fluentWaitVisibilityOfElementLocated(locatorType, path);
            System.out.println("Element found flag " + flagCheck);
            Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
            timeStamp2 = timestamp1.getTime();
            System.out.println("2nd time stamp " + timeStamp2);

            long stdMS = 1000;
            long timeInMS = timeStamp2 - timeStamp1;
            double timeInSec = ((double) timeInMS / stdMS);
            String timeInSStr = String.valueOf(timeInSec);
            System.out.println("timeInSStr " + timeInSStr);
           /* fGetNetworksRequests();
            GenerateHARDataFile generateHARDataFile = new GenerateHARDataFile();
            generateHARDataFile.writeHARDataToHARJsonFile();*/
            ExcelActions.writeValueToExcel(loadTimeKey, timeInSStr);

        } catch (Exception e) {
            log.info("error message : " + e.getMessage());
        }

    }

    public String fRecordLoadTimeForPageLoad(String locatorType, String path, long timeStamp1) throws Exception {
        //   long timeStamp1;
        long timeStamp2 = 0;
        String timeInSStr = null;
        double timeInSec = 0;
        try {
            Thread.sleep(500);
            log.info("1 st time stamp " + timeStamp1);
            boolean flagCheck = fluentWaitVisibilityOfElementLocated(locatorType, path);
            reduxStroreflag();
            //    checkForSpinner();
            //    getPageLoadTime();
            //    Long navigationStart = (Long) ((JavascriptExecutor) driver).executeScript("return Date.now() - performance.timeOrigin;");
            Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
            timeStamp2 = timestamp2.getTime();
            log.info("2nd time stamp " + timeStamp2);
            log.info("Element found flag " + flagCheck);

            long stdMS = 1000;
            long timeInMS = timeStamp2 - timeStamp1;
            timeInSec = ((double) timeInMS / stdMS);
            timeInSStr = String.valueOf(timeInSec);
            log.info("timeInSeconds " + timeInSStr);

        } catch (Exception e) {
            log.info("error message : " + e.getMessage());
        }

        return timeInSStr;
    }

    public String fRecordLoadTimeForElementLocated(String locatorType, String path, long timeStamp1) throws Exception {
        //   long timeStamp1;
        long timeStamp2 = 0;
        String timeInSStr = null;
        double timeInSec = 0;
        try {
            Thread.sleep(1000);
            log.info("1 st time stamp " + timeStamp1);
            boolean flagCheck = fluentWaitPresenceOfElementLocated(locatorType, path);
            //  reduxStroreflag();
            //    checkForSpinner();
            //    getPageLoadTime();
            //    Long navigationStart = (Long) ((JavascriptExecutor) driver).executeScript("return Date.now() - performance.timeOrigin;");
            Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
            timeStamp2 = timestamp2.getTime();
            log.info("2nd time stamp " + timeStamp2);
            log.info("Element found flag " + flagCheck);

            long stdMS = 1000;
            long timeInMS = timeStamp2 - timeStamp1;
            timeInSec = ((double) timeInMS / stdMS);
            timeInSStr = String.valueOf(timeInSec);
            log.info("timeInSeconds " + timeInSStr);

        } catch (Exception e) {
            log.info("error message : " + e.getMessage());
        }

        return timeInSStr;
    }

    @SuppressWarnings("deprecation")
    public boolean fluentWaitPageLoad() {
        boolean flag = false;
        try {
            FluentWait wait = new FluentWait(driver);
            wait.withTimeout(60, TimeUnit.SECONDS);
            wait.pollingEvery(500, TimeUnit.MILLISECONDS);
            List list = new ArrayList();
            list.add(NoSuchElementException.class);
            list.add(NoSuchFileException.class);
            list.add(StaleElementReferenceException.class);
            wait.ignoreAll(list);
            wait.until(webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
            flag = true;
            return flag;
        } catch (Exception e) {
            log.info("Element exception : " + e.getMessage());
            return flag;
        }

    }

    @SuppressWarnings("deprecation")
    public boolean fluentWaitElementClickable(WebElement ele) {
        boolean flag = false;
        try {
            FluentWait wait = new FluentWait(driver);
            wait.withTimeout(60, TimeUnit.SECONDS);
            wait.pollingEvery(500, TimeUnit.MILLISECONDS);
            List list = new ArrayList();
            list.add(NoSuchElementException.class);
            list.add(NoSuchFileException.class);
            list.add(StaleElementReferenceException.class);
            wait.ignoreAll(list);
            //  wait.until(ExpectedConditions.visibilityOfElementLocated((By) ele));
            wait.until(ExpectedConditions.elementToBeClickable(ele));
            flag = true;
            return flag;
        } catch (Exception e) {
            log.info("Element exception : " + e.getMessage());
            return flag;
        }

    }

    @SuppressWarnings("deprecation")
    public boolean fluentWaitVisibilityOfElementLocated(String locatorType, String path) {
        boolean flag = false;
        By elem_dynamic = null;
        try {

            FluentWait wait = new FluentWait(driver);
            wait.withTimeout(90, TimeUnit.SECONDS);
            wait.pollingEvery(500, TimeUnit.MILLISECONDS);
            List list = new ArrayList();
            list.add(NoSuchElementException.class);
            list.add(NoSuchFileException.class);
            list.add(StaleElementReferenceException.class);
            list.add(ElementNotVisibleException.class);
            list.add(NoSuchFrameException.class);
            list.add(ElementNotSelectableException.class);
            wait.ignoreAll(list);
            if (locatorType.equalsIgnoreCase("xpath")) {
                elem_dynamic = By.xpath(path);
            } else if (locatorType.equalsIgnoreCase("css")) {
                elem_dynamic = By.cssSelector(path);
            } else if (locatorType.equalsIgnoreCase("tagname")) {
                elem_dynamic = By.tagName(path);
            } else if (locatorType.equalsIgnoreCase("id")) {
                elem_dynamic = By.id(path);
            }

            wait.until(ExpectedConditions.visibilityOfElementLocated(elem_dynamic));
            wait.until(ExpectedConditions.presenceOfElementLocated(elem_dynamic));
            wait.until(ExpectedConditions.elementToBeClickable(elem_dynamic));
            Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
            long timeStamp1 = timestamp2.getTime();
            log.info("Time Stamp after load : " + timeStamp1);

            flag = true;
            return flag;
        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
            ExtentReport.getExtentTest().log(Status.FAIL, "WebElement  " + elem_dynamic + " is not present");
            return flag;
        }

    }

    @SuppressWarnings("deprecation")
    public boolean fluentWaitPresenceOfElementLocated(String locatorType, String path) {
        boolean flag = false;
        By elem_dynamic = null;
        try {

            FluentWait wait = new FluentWait(driver);
            wait.withTimeout(30, TimeUnit.SECONDS);
            wait.pollingEvery(500, TimeUnit.MILLISECONDS);
            List list = new ArrayList();
            list.add(NoSuchElementException.class);
            list.add(NoSuchFileException.class);
            list.add(StaleElementReferenceException.class);
            list.add(ElementNotVisibleException.class);
            list.add(NoSuchFrameException.class);
            list.add(ElementNotSelectableException.class);
            wait.ignoreAll(list);
            if (locatorType.equalsIgnoreCase("xpath")) {
                elem_dynamic = By.xpath(path);
            } else if (locatorType.equalsIgnoreCase("css")) {
                elem_dynamic = By.cssSelector(path);
            } else if (locatorType.equalsIgnoreCase("tagname")) {
                elem_dynamic = By.tagName(path);
            } else if (locatorType.equalsIgnoreCase("id")) {
                elem_dynamic = By.id(path);
            }

            //   wait.until(ExpectedConditions.visibilityOfElementLocated(elem_dynamic));
            wait.until(ExpectedConditions.presenceOfElementLocated(elem_dynamic));
            //    wait.until(ExpectedConditions.elementToBeClickable(elem_dynamic));
            Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
            long timeStamp1 = timestamp2.getTime();
            log.info("Time Stamp after load : " + timeStamp1);

            flag = true;
            return flag;
        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
            ExtentReport.getExtentTest().log(Status.FAIL, "WebElement  " + elem_dynamic + " is not present");
            return flag;
        }

    }

    @SuppressWarnings("deprecation")
    public boolean fluentWaitVisibilityOfListOfElementsLocated(String locatorType, String path) {
        boolean flag = false;
        By elem_dynamic = null;
        try {

            FluentWait wait = new FluentWait(driver);
            wait.withTimeout(120, TimeUnit.SECONDS);
            wait.pollingEvery(500, TimeUnit.MILLISECONDS);
            List list = new ArrayList();
            list.add(NoSuchElementException.class);
            list.add(NoSuchFileException.class);
            list.add(StaleElementReferenceException.class);
            wait.ignoreAll(list);
            if (locatorType.equalsIgnoreCase("xpath")) {
                elem_dynamic = By.xpath(path);
            } else if (locatorType.equalsIgnoreCase("css")) {
                elem_dynamic = By.cssSelector(path);
            } else if (locatorType.equalsIgnoreCase("tagname")) {
                elem_dynamic = By.tagName(path);
            } else if (locatorType.equalsIgnoreCase("id")) {
                elem_dynamic = By.id(path);
            }

            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elem_dynamic));
            //   wait.until(ExpectedConditions.elementToBeClickable(elem_dynamic));
            flag = true;
            return flag;
        } catch (Exception e) {
            log.info("Exception occurred : " + e.getMessage());
            ExtentReport.getExtentTest().log(Status.FAIL, "WebElement  " + elem_dynamic + " is not present");
            return flag;
        }

    }

    public String fGetNetworksRequests() throws IOException, InterruptedException {
        String sessionId = null;
        //  Thread.sleep(10000);
/*            String scriptGetInfo = "performance.setResourceTimingBufferSize(1000000);" +
                    "return performance.getEntriesByType('resource').map(JSON.stringify).join('\\n')";

            String har = ((JavascriptExecutor)driver).executeScript(scriptGetInfo).toString();
            Files.write(Paths.get("log.har"), har.getBytes());

		String scriptToExecute2 = "var performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {};var network = performance.getEntries() || {}; return network;";
		String netData = ((JavascriptExecutor)driver).executeScript(scriptToExecute2).toString();

		System.out.println("NET DATA 1"+netData);
		String scriptToExecute1 = "var network = performance.getEntries(); return network;";
		String networkRequestData = ((JavascriptExecutor)driver).executeScript(scriptToExecute1).toString();

		System.out.println("NET DATA 2"+networkRequestData);
    //    System.out.println("NET DATA 1"+ JSON.parse(networkRequestData).toString());*/

        //  System.out.println("Session Id : "+ JSON.parse(sessionId).toString());

        //      JSON.parse(JSON.stringify(sessionId));

        List<LogEntry> entries = driver.manage().logs().get(LogType.BROWSER).getAll();
        List<LogEntry> entries1 = driver.manage().logs().get(LogType.PERFORMANCE).getAll();
        System.out.println(entries.size() + " " + LogType.PERFORMANCE + " log entries found");
        System.out.println(entries.size() + " " + LogType.BROWSER + " log entries found");

        for (LogEntry entry : entries) {
            System.out.println("browser log entry : \n" + new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
        }
        for (LogEntry entry : entries1) {
            System.out.println("performance log entry : \n" + new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
        }
     /*   getSessionIDFromJsonObject(networkRequestData);


        GenerateHARDataFile generateHARDataFile = new GenerateHARDataFile();
        	generateHARDataFile.writeHARDataToHARJsonFile();*/
        return sessionId;
    }

    public String fGetJsonObjectFromNetwork(String key) throws IOException, InterruptedException {
        String keyValue = null;
        String sessionIdFromNetwork = "var network1 = window.sessionStorage.getItem('saContext'); return network1;";
        String obj = ((JavascriptExecutor) driver).executeScript(sessionIdFromNetwork).toString();
        //    System.out.println("sessionIdFromNetwork : "+ sessionId);

        JSONArray array = new JSONArray("[" + obj + "]");
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            keyValue = object.getString(key);
            System.out.println("session id : " + keyValue);
        }
        return keyValue;
    }

    public void getSessionIDFromJsonObject(final String jsonStringObj) throws IOException {
        JSONArray jsRR = new JSONArray(jsonStringObj);
        System.out.println("JSON ARR Length" + jsRR.length());
        int jsonObjLength = jsRR.length();
        String value1 = "";
        List<String> urlList = new ArrayList<String>();
        int count = 0;
        for (int i = 0; i < jsonObjLength; i++) {
            JSONObject jsonObject1 = jsRR.getJSONObject(i);
            String valueTemp = jsonObject1.optString("name");
            if (valueTemp.contains("sessionId=")) {
                urlList.add(valueTemp);
                count++;
                //break;
            }
        }
        System.out.println("COUNT OF URLS HAVING SESSION ID " + count);
        for (String sessionURL : urlList) {
            System.out.println("LIST OF URLS HAVING SESSION ID " + sessionURL);
            boolean bool = sessionURL.contains("sessionId=undefined");
            if (!bool) {
                System.out.println("URL WITH VALID SESSION ID = " + sessionURL);
                value1 = sessionURL;
            }
        }
        System.out.println("FINAL URL WITH VALID SESSION ID " + value1);
        URL url = new URL(value1);
        String protocol = url.getProtocol();
        String hostName = url.getHost();
        String hostURL = protocol + "://" + hostName;
        String trimmedURL = value1.replace(hostURL, "");
        String trimmedURL1 = trimmedURL.substring(trimmedURL.lastIndexOf("/") + 1);
        trimmedURL1 = trimmedURL.substring(trimmedURL.lastIndexOf("?") + 1);
        String[] urlKV = trimmedURL1.split("&");
        String sessionid = "";
        for (int i = 0; i < urlKV.length; i++) {
            if (urlKV[i].contains("sessionId")) {
                sessionid = urlKV[i].substring(10);
            }
        }
        System.out.println("SESSION ID FROM URL " + sessionid);
        ExcelActions.writeValueToExcel("SessionID", sessionid);
    }

    public void getHAR() throws IOException {
        File generateHARFile = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\HARData\\CharterHARData.har");
        //   har.writeTo(generateHARFile);
        String destinationFile = "/HARs/" + "CharterHARData" + ".har";
        ((JavascriptExecutor) driver).executeScript(
                "!function(e,o){e.src=\"https://cdn.jsdelivr.net/gh/Surin3794/chrome_har_js@master/chromePerfLogsHAR.js\",e.onload=function(){jQuery.noConflict(),console.log(\"jQuery injected\")},document.head.appendChild(e)}(document.createElement(\"script\"));");
        //   File file = new File(destinationFile);
        //    file.getParentFile().mkdirs();`
        FileWriter harFile = new FileWriter(generateHARFile);
        harFile.write((String) ((JavascriptExecutor) driver).executeScript(
                "return module.getHarFromMessages(arguments[0])", getPerfEntryLogs(driver).toString()));
        harFile.close();
    }

    public long getTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long timeStamp = timestamp.getTime();
        return timeStamp;
    }

    public void loadEventTime1() {

        // get the  page load time
        Long loadtime = (Long) ((JavascriptExecutor) driver).executeScript(
                "return performance.timing.loadEventEnd - performance.timing.navigationStart;");
        log.info("get the  page load time : : : " + loadtime / 1000);

    }

    public void loadEventTime() {
      /*
        ''' Use Navigation Timing  API to calculate the timings that matter the most '''
       */

        Long navigationStart = (Long) ((JavascriptExecutor) driver).executeScript("return window.performance.timing.navigationStart;");
        Long responseStart = (Long) ((JavascriptExecutor) driver).executeScript("return window.performance.timing.responseStart;");
        Long domComplete = (Long) ((JavascriptExecutor) driver).executeScript("return window.performance.timing.domComplete;");

        log.info("navigationStart1  : " + navigationStart);
        log.info("responseStart1  : " + navigationStart);
        log.info("domComplete1 : " + navigationStart);

        // get the  page load time

     /*
        ''' Calculate the performance'''
      */
        long backendPerformance_calc = responseStart - navigationStart;
        long frontendPerformance_calc = domComplete - responseStart;

        long stdMS = 1000;
        double backendPerformance_calc1 = ((double) backendPerformance_calc / stdMS);
        double frontendPerformance_calc1 = ((double) frontendPerformance_calc / stdMS);
        //   timeInSStr = String.valueOf(backendPerformance_calc1);

        log.info("Back End: %s" + backendPerformance_calc1);
        log.info("Front End: %s" + frontendPerformance_calc1);
    }

    public void getPageLoadTime() {
        //Creating the JavascriptExecutor interface object by Type casting
      /*  Object performanceTimeOrigin1 = ((JavascriptExecutor) driver).executeScript("return performance.timeOrigin;");
        String lng1 = String.valueOf(performanceTimeOrigin1);
        Long performanceTimeOrigin = Long.valueOf(lng1);
        log.info("performance.timeOrigin milli: " + performanceTimeOrigin);
        log.info("performance.timeOrigin seconds: " + performanceTimeOrigin/1000);*/

        Object navigationStart1 = null;
        navigationStart1 = ((JavascriptExecutor) driver).executeScript("return Date.now() - performance.timeOrigin;");
        // Long lng = Long.valueOf((String) navigationStart);
        log.info("Total Page Load Time1: " + String.valueOf(navigationStart1));
        /*      Long navigationStart2 = (Long) ((JavascriptExecutor) driver).executeScript("return performance.timeOrigin;");
        log.info("Total Page Load Time1: " + navigationStart2);
        Long navigationStart1 = (Long) ((JavascriptExecutor) driver).executeScript("return Date.now();");
        log.info("Total Page Load Time1: " + navigationStart1);*/
        String lng = String.valueOf(navigationStart1);
        Long navigationStart = Long.valueOf(lng);
        long pageLoadTime_Seconds = navigationStart / 1000;
        log.info("Total Page Load Time: " + (double) navigationStart + " milliseconds");
        log.info("Total Page Load Time: " + (double) pageLoadTime_Seconds + " seconds");
        //This will get you the time passed since you page navigation started
    }

    public void reduxStroreflag() {
        boolean reduxStroreflag = true;
        while (reduxStroreflag) {
            reduxStroreflag = (boolean) ((JavascriptExecutor) driver).executeScript("return window.reduxStore.getState().ui.isLoading;");
            log.info("redux strore flag: " + reduxStroreflag);
            if (reduxStroreflag = false)
                break;
        }
        log.info("redux strore flag: " + reduxStroreflag);
    }

    public void checkForSpinner() {
        boolean spinnnerFlag = true;
        try {
            while (spinnnerFlag) {
                spinnnerFlag = driver.findElement(By.cssSelector("[class=\"loading-container fixed-loader\"]")).isDisplayed();
                if (spinnnerFlag = false)
                    break;
            }
            log.info("spinnnerFlag: " + spinnnerFlag);
        } catch (StaleElementReferenceException stale) {
            log.info("Stale");
        } catch (TimeoutException t) {
            log.info("Spinner not started");
        } catch (NoSuchElementException nse) {
            log.info("Spinner not found/stopped");
        } catch (Exception e) {
            e.getMessage();
        }

    }
    public void jsScrollBy(int scrollValue) {

        String strScrollBy = new StringBuilder().append("\"window.scrollBy(0,").append(scrollValue).append(")\"").toString();

        System.out.println("Scroll by " + strScrollBy);

        JavascriptExecutor executor = (JavascriptExecutor) driver;

        executor.executeScript("window.scrollBy(0,500)");

        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
    }

    public void clickOnCalender(WebElement webElement) {
        webElement.click();
    }

    public void selectYear(String yearToselect) throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("Clicking on Year Dropdown");
        //clciking on Year Dropdown
        driver.findElement(By.xpath("//select[@class='ui-datepicker-year']")).click();
        //Locating all year from dropdown
        List<WebElement> Years = driver.findElements(By.xpath("//*[@id='ui-datepicker-div']/div/div/select[2]/option"));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        for(WebElement year : Years){
            if(year.getText().trim().equalsIgnoreCase(yearToselect)){
                js.executeScript("arguments[0].scrollIntoView()", year);
                year.click();
                break;
            }
        }
        System.out.println("Selected Year");
    }

    public void selectMonth(String monthToselect) throws ParseException, InterruptedException {
        Thread.sleep(3000);
        System.out.println("Clicking on Month Dropdown");

        //Clicking on Month Dropdown
        driver.findElement(By.xpath("//select[@class='ui-datepicker-month']")).click();
        //loacting all month from dropdwon
        List<WebElement> Months = driver.findElements(By.xpath("//*[@id='ui-datepicker-div']/div/div/select[1]/option"));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        for(WebElement month : Months){
            if(month.getText().trim().equalsIgnoreCase(monthToselect)){
                js.executeScript("arguments[0].scrollIntoView()", month);
                month.click();
                break;
            }
        }

        System.out.println("Selected Month");
    }


    public void selectDate(String dateToSelect){
        //Locating all the dates
        List<WebElement> days =  driver.findElements(By.xpath("//*[@data-handler='selectDay']"));

        //Iterate through all date when the given date is matching
        for(WebElement day : days){
            if(day.getText().equalsIgnoreCase(dateToSelect)){
                day.click();
                break;
            }
        }
        System.out.println("Selcted Date");
    }

    public void tapOnPointInsideElement(WebElement element, String sideOfTheBox) {
        Point location = element.getLocation();
        System.out.println("location>>" + location);
        Dimension size = element.getSize();
        System.out.println("size>>" + size);
        Point centreOfElement;
        if (sideOfTheBox.equalsIgnoreCase("centre")) {
            System.out.println("--------CENTRE");
            centreOfElement = getCenterOfElement(location, size);
        } else if (sideOfTheBox.equalsIgnoreCase("bottom right")) {
            System.out.println("--------bottom right");
            centreOfElement = getBottomRightOfElement(location, size);
        } else if (sideOfTheBox.equalsIgnoreCase("bottom left")) {
            System.out.println("--------bottom left");
            centreOfElement = getBottomLeftOfElement(location, size);
        } else {
            centreOfElement = getCenterOfElement(location, size);
        }

        System.out.println("------------->TAP POINTS" + centreOfElement);
    }

    public Point getBottomRightOfElement(Point location, Dimension size) {
        return new Point((int) (location.getX() + size.getWidth() / 1.1),
                (int) (location.getY() + size.getHeight() / 1.2));
    }

    public Point getBottomLeftOfElement(Point location, Dimension size) {
        return new Point((location.getX() + size.getWidth() / 7),
                (int) (location.getY() + size.getHeight() / 1.2));
    }

    public Point getCenterOfElement(Point location, Dimension size) {
        return new Point((location.getX() + size.getWidth() / 2),
                (location.getY() + size.getHeight() / 2));
    }

    public void handlePopup() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
            String Alert_popup = alert.getText();
            System.out.println(Alert_popup);
        } catch (NoAlertPresentException e) {
            System.out.println("No alert displayed");
        }
    }


    //    Added by Pavithra
    protected WebElement waitForElement(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForElement(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }


    public Boolean clickElement(WebElement element) {
        boolean blResult = false;
        try {
            new WebDriverWait(driver, 30).
                    until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            blResult = true;
        } catch (Exception e) {
            System.out.println(new StringBuilder().append("************Exception:  ")
                    .append(e.getLocalizedMessage())
                    .append("   occured in:")
                    .append(e.getStackTrace()[0])
                    .append("********************"));
        }
        return blResult;
    }


    public Boolean clickEle(By by) {
        boolean blResult = false;
        try {
            new WebDriverWait(driver, 30).
                    until(ExpectedConditions.elementToBeClickable(by));
            driver.findElement(by).click();
            blResult = true;
        } catch (Exception e) {
            System.out.println(new StringBuilder().append("************Exception:  ")
                    .append(e.getLocalizedMessage())
                    .append("   occured in:")
                    .append(e.getStackTrace()[0])
                    .append("********************"));
        }
        return blResult;
    }


    public void waitForSeconds(int secs) {
        try {
            Thread.sleep(secs * 1000);
        } catch (Exception e) {
        }
    }

    public void waitForPresenceOfElement(By by) {
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }


    public boolean focusFrame(int i) {
        boolean isFrameFocused = false;
        waitForSeconds(1);
        driver.switchTo().frame(i);
        isFrameFocused = true;
        return isFrameFocused;
    }

    public void mouseOver(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
    }

    public void mouseClick(WebElement element) {
        Actions actions = new Actions(driver);
        waitForElement(element);
        actions.moveToElement(element).click(element).build().perform();
    }

    public void enterValue(WebElement element,String value){
        new WebDriverWait(driver,30).
                until(ExpectedConditions.visibilityOf(element));
        element.click();
        element.clear();
        element.sendKeys(value);
    }

    public boolean focusFrame(WebElement element) {
        boolean isFrameFocused;
        waitForSeconds(1);
        driver.switchTo().frame(element);
        isFrameFocused = true;
        return isFrameFocused;
    }

    public void jsEnterValue(WebElement elmnt, String strValue) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", elmnt);
        elmnt.sendKeys(strValue);
    }

    public void jsClickElement(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();}, 100)", element);
        waitForSeconds(1);
    }

    public boolean verifyElement(WebElement element) {
        boolean isVerify = false;
        try {
            isVerify = element.isDisplayed();
        } catch (NoSuchElementException error) {
            error.getMessage();
            isVerify = false;
        }
        return isVerify;
    }

    /***
     * Color Validation for Upgrade and Downgrade Packages
     * @Author Pavithra
     */

    public String colorValidationForPackages(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script =
                "var img = arguments[0];" +
                        "var canvas = document.createElement('canvas');" +
                        "var ctx = canvas.getContext('2d');" +
                        "canvas.width = img.width;" +
                        "canvas.height = img.height;" +
                        "ctx.drawImage(img, 0, 0);" +
                        "var pixel = ctx.getImageData(0, 0, 1, 1).data;" +
                        "return 'rgb(' + pixel[0] + ',' + pixel[1] + ',' + pixel[2] + ')';";

        String color = (String) js.executeScript(script, element);
        System.out.println("Image Color: " + color);
        return color;
    }


}
