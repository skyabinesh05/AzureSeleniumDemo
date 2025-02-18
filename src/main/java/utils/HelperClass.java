package utils;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import coeqa.managers.FileReaderManager;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.ExtentReport.extentPath;

public class HelperClass {    //Global Varibables Section

    static final String appTestName = "cableBuyFlow";
    private static final String serverURL = "https://eyes.applitools.com";
    private static final String applitolsViewKey = "xGlV76pnhJKeMTK8nyrM8E8m2aRw2GazMCAJX5GK9mg110";
    protected static Properties config;
    static String applitoolFlag;

    static {
        try {
            applitoolFlag = FileReaderManager.fileRead().getConfiguration().getAppliFlag();
            DOMConfigurator.configure(System.getProperty("user.dir") + "//configs/log4j.xml");
            config = FileReaderManager.fileRead().getConfiguration().getConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //public static final Logger Log = Logger.getLogger(HelperClass.class.getName())
    public final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    protected WebDriver driver;
    BatchInfo cbfBInfo;
    private int width;
    private int height;


    //Constructor

    public HelperClass(WebDriver driver) {
        this.driver = driver;

    }

    public void captureSS(String status, String desc, String imgName) {
        try {
            log.info("status :" + status);
            log.info("imgName :" + imgName);
            if (status.contains("pass")) {
                ExtentReport.getExtentTest().log(Status.PASS, desc, MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, imgName)).build());
                //ExtentReport.getExtentTest().pass(MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, imgName)).build())
            } else {
                ExtentReport.getExtentTest().fail(MediaEntityBuilder.createScreenCaptureFromPath(getScreenShot(driver, imgName)).build());
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    //Utility Methods
    public Eyes setUpEyesConfig(BatchInfo batchInfo, String testName, String pageName, boolean hideSrlBar,
                                boolean stichMode, boolean fullPageSS, boolean hideCaret) {
        Eyes eyes = new Eyes();
        try {
            eyes.setServerUrl("https://eyesapi.applitools.com");
            eyes.setBatch(batchInfo);
            eyes.setApiKey("z8X7gOJQtzEk8UA91lKn4pU8W8EE5T9798Q9zb8wc106n8c110");
            eyes.setHideCaret(hideCaret);
            eyes.setSendDom(false);
            eyes.setHideScrollbars(hideSrlBar);
            if (stichMode) {
                eyes.setStitchMode(StitchMode.CSS);
            }
            eyes.setForceFullPageScreenshot(fullPageSS);
            viewPortSize();
            eyes.open(driver, testName, pageName, new RectangleSize(width, height));
            ExtentReport.getExtentTest().log(Status.INFO, "Eyes setup Completed ");
        } catch (Exception e) {
            ExtentReport.getExtentTest().log(Status.FAIL, "Exception Occurred in Eyes setup " + e);
            log.info("Exception occurred :" + e.getMessage());

        }
        return eyes;
    }

    public void viewPortSize() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            height = ((Number) js.executeScript("return window.innerHeight")).intValue();
            width = ((Number) js.executeScript("return window.innerWidth")).intValue();
            log.info("Updated window (width,height) is (" + width + "," + height + ")");
        } catch (Exception e) {
            log.info("Unable to get viewport size" + e);
            e.printStackTrace();
        }
    }

    public String comparePage(String appTestName, String pageName, BatchInfo batchinfo1, boolean forcewindow) {
        TestResults stepsResults;
        String appliToolUrl = null;
        if (!applitoolFlag.equalsIgnoreCase("true")) {
            log.info("Global Applitools Flag in config is set to : " + applitoolFlag);
            ExtentReport.getExtentTest().log(Status.INFO, "Global Applitools Flag in config is set to : " + applitoolFlag + "'");
        } else {
            Eyes eyes = setUpEyesConfig(batchinfo1, appTestName, pageName, false, true, true, false);
            stepsResults = captureFootprint(eyes, pageName, forcewindow);
            ExtentReport.getExtentTest().log(Status.INFO, "Global Applitool Link : " + stepsResults.getUrl());
            appliToolUrl = stepsResults.getUrl();
            log.info("" + appliToolUrl);
        }
        return appliToolUrl;
    }

    public TestResults captureFootprint(Eyes eyes, String testName, boolean fully) {
        TestResults stepsResults = null;
        ExtentReport.getExtentTest().log(Status.INFO, "Capturing applitool footprint ");
        try {
            if (eyes.getIsOpen()) {
                eyes.check(testName, Target.window().fully(fully));
                stepsResults = close(eyes, applitolsViewKey);
                log.info(stepsResults);
                log.info("Result==" + stepsResults.getMismatches());
                log.info("Comparing current page - " + testName + " with Applitools Baseline" +
                        "Compared the images. Applitools results url is: " + stepsResults.getUrl());
                if (stepsResults.getMismatches() == 0) {
                    ExtentReport.getExtentTest().log(Status.INFO, "No Mismatches compared to baseline image");
                    log.info("No Mismatches compared to baseline image");
                } else {
                    ExtentReport.getExtentTest().log(Status.WARNING, "Mismatches compared to baseline image please refer applitool footprint : ");
                    log.info("There are some mismatches compared to baseline image. Please refer the applitools results link and accept this as a baseline/reject it.");
                }

            } else {
                log.info("eyes not open");
                log.info("Comparing current page - " + testName + " with Applitools Baseline" + "Eyes not opened");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stepsResults;
    }

    public void visualValidation(String pageName) {

        comparePage(appTestName, pageName, cbfBInfo, false);

    }

    public TestResults close(Eyes eyes, String viewKey) throws Exception {
        TestResults results = eyes.close(false);

        boolean[] stepStates = calculateStepResults(results, viewKey);
        log.info(stepStates);
        return results;
    }

    public boolean[] calculateStepResults(TestResults results, String viewKey) throws Exception {
        return stepResults(getSessionId(results), getBatchId(results), viewKey);
    }

    public String getSessionId(TestResults results) {

        String url = String.format("^%s/app/batches/\\d+/(?<sessionId>\\d+).*$", serverURL);
        Pattern p = Pattern.compile(url);
        Matcher matcher = p.matcher(results.getUrl());
        if (!matcher.find())
            return null; // Something wrong with our url
        return matcher.group("sessionId");
    }

    public String getBatchId(TestResults results) {

        String url = String.format("^%s/app/batches/(?<batchId>\\d+)/\\d+.*$", serverURL);
        Pattern p = Pattern.compile(url);
        Matcher matcher = p.matcher(results.getUrl());
        if (!matcher.find())
            return null; // Something wrong with our url
        return matcher.group("batchId");
    }

    public boolean[] stepResults(String sessionId, String batchId, String viewKey) throws Exception {
        String url = String.format(serverURL + "/api/sessions/batches/%s/%s/?ApiKey=%s&format=json", batchId, sessionId,
                viewKey);
        String json = readJsonStringFromUrl(url);

        JSONObject obj = new JSONObject(json);
        JSONArray expected = obj.getJSONArray("expectedAppOutput");
        JSONArray actual = obj.getJSONArray("actualAppOutput");

        int steps = expected.length();
        boolean[] retStepResults = new boolean[steps];

        for (int i = 0; i < steps; i++) {
            retStepResults[i] = ((expected.get(i) == JSONObject.NULL) || (actual.get(i) == JSONObject.NULL)
                    || (actual.getJSONObject(i).getBoolean("isMatching")));
        }

        return retStepResults;
    }

    public String readJsonStringFromUrl(String url) throws Exception {

        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return readAll(rd);
        }
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public String getScreenShot(WebDriver driver, String screenshotName) {

        String strDate = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
      //  String path=System.getProperty("user.dir") + "/ExtentReport/Report'"+dateName+"'/Screencapture/";
        String destination = System.getProperty("user.dir") + "/ExtentReport/Report_"+strDate+"/Screencapture/";
        try {
            String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            //after execution, you could see a folder "FailedTestsScreenshots" under src folder
            destination = destination + screenshotName + dateName + ".png";
            File finalDestination = new File(destination);
            FileUtils.copyFile(source, finalDestination);

        } catch (Exception e) {
            log.info("Exception occurred in getScreenShot() " + e);
        }
        return destination;
    }

    //Abinesh Code SS
    public static String getScreenshot(WebDriver driver, String screenshotName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Ensure the path is relative to the report directory
            String relativePath = "screenshots/" + screenshotName + ".png";
            String fullPath = extentPath + relativePath;  // extentPath should be the path where the report is stored

            // Save the screenshot in the "screenshots" folder relative to the report
            FileUtils.copyFile(srcFile, new File(fullPath));

            // Return the relative path for Extent Report
            return relativePath;
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
            return null;
        }
    }

    //======new=================

    public TestResults compareModal(String identifier, String TestName, String PageName, BatchInfo batchinfo) throws Exception {
        TestResults stepsResults = null;
        Eyes eyes = new Eyes();
        try {
            //WebElement Ele=driver.findElement(By.cssSelector(".box"));
            WebElement Ele = driver.findElement(By.cssSelector(identifier));
//            WebElement Ele = driver.findElement(By.id(identifier));
            log.info("Comparing page");
            eyes.setSendDom(false);

            eyes.setBatch(batchinfo);
            eyes.setForceFullPageScreenshot(true);
            eyes.setStitchMode(StitchMode.CSS);

            viewPortSize();
            eyes.setHideScrollbars(false);
            eyes.setApiKey("z8X7gOJQtzEk8UA91lKn4pU8W8EE5T9798Q9zb8wc106n8c110");
            eyes.open(driver, TestName, PageName, new RectangleSize(width, height));

            JavascriptExecutor jsexe = (JavascriptExecutor) driver;
            //jsexe.executeScript("window.scrollTo(0,250);"); // scroll to top
            jsexe.executeScript("window.scrollTo(100,0);"); // scroll to top
            //jsexe.executeScript("window.scrollTo(0,0);"); // scroll to top

            eyes.check(Target.region(By.cssSelector(identifier)).fully());
//            eyes.check(Target.region(By.id(identifier)).fully());
            //eyes.check(Target.region(By.cssSelector(".box")).fully());
            ExtentReport.getExtentTest().log(Status.INFO, "Capturing applitool footprint ");
            stepsResults = close(eyes, applitolsViewKey);
            // eyes.close();
            log.info(stepsResults);
            log.info("Result==" + stepsResults.getMismatches());
            if (stepsResults.getMismatches() == 0) {
                ExtentReport.getExtentTest().log(Status.INFO, "No Mismatches compared to baseline image");
                log.info("No Mismatches compared to baseline image");
            } else {
                ExtentReport.getExtentTest().log(Status.WARNING, "Mismatches compared to baseline image please refer applitool footprint : ");
                log.info("There are some mismatches compared to baseline image. Please refer the applitools results link and accept this as a baseline/reject it.");
            }

            // return stepsResults;

        } catch (Exception e) {
            log.info("Error:" + e);
            // e.printStackTrace();
            // return stepsResults;


        } finally {
            eyes.abortIfNotClosed();

        }

        return stepsResults;

    }
    public TestResults compareModalwithScroll(String identifier, String TestName, String PageName, BatchInfo batchinfo) throws Exception {
        TestResults stepsResults = null;
        Eyes eyes = new Eyes();
        try {
            WebElement Ele = driver.findElement(By.cssSelector(identifier));
            log.info("Comparing page");
            eyes.setSendDom(false);

            eyes.setBatch(batchinfo);
            eyes.setForceFullPageScreenshot(true);
            eyes.setStitchMode(StitchMode.CSS);

            viewPortSize();
            eyes.setHideScrollbars(false);
            eyes.setApiKey("z8X7gOJQtzEk8UA91lKn4pU8W8EE5T9798Q9zb8wc106n8c110");
            eyes.open(driver, TestName, PageName, new RectangleSize(width, height));
            JavascriptExecutor jsexe = (JavascriptExecutor) driver;
            eyes.check(Target.window().scrollRootElement(By.cssSelector(identifier)).fully());
            ExtentReport.getExtentTest().log(Status.INFO, "Capturing applitool footprint ");
            stepsResults = close(eyes, applitolsViewKey);
            log.info(stepsResults);
            log.info("Result==" + stepsResults.getMismatches());
            if (stepsResults.getMismatches() == 0) {
                ExtentReport.getExtentTest().log(Status.INFO, "No Mismatches compared to baseline image");
                log.info("No Mismatches compared to baseline image");
            } else {
                ExtentReport.getExtentTest().log(Status.WARNING, "Mismatches compared to baseline image please refer applitool footprint : ");
                log.info("There are some mismatches compared to baseline image. Please refer the applitools results link and accept this as a baseline/reject it.");
            }

            // return stepsResults;

        } catch (Exception e) {
            log.info("Error:" + e);
            // e.printStackTrace();
            // return stepsResults;


        } finally {
            eyes.abortIfNotClosed();

        }

        return stepsResults;

    }
    public static String CommonPath(){
        String dateName = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
        String path=System.getProperty("user.dir") + "/ExtentReport/Report'"+dateName+"'/Screencapture/";
        return path;
    }


}
