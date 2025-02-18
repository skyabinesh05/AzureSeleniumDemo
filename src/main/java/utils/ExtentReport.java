package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.JsonFormatter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import coeqa.datareader.ConfigReader;
import coeqa.managers.FileReaderManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ExtentReport {

    //   public static final String extentPath =CommonPath();
    public static final String extentPath = System.getProperty("user.dir") + "\\ExtentReport\\";
    protected static ExtentReports report;
    protected static ExtentSparkReporter spark;
    protected static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static ExtentReports intiExtentReports() {
        try {
            Date date = new Date();
            String envName= ConfigReader.getEnvName();
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
            String strDate = formatter.format(new Date());
            //     ExtentReport.getExtentTest().info("Browser Name " + FileReaderManager.fileRead().getConfiguration().getBrowerName());
            spark = new ExtentSparkReporter(extentPath +"Report_"+strDate+"\\"+ envName+"_BDD_Automation_Report_" + strDate + ".html");
            JsonFormatter json = new JsonFormatter(extentPath +"Report_"+strDate+"\\"+ envName+"_BDD_Automation_Report_" + strDate + ".json");
            report = new ExtentReports();
            report.createDomainFromJsonArchive(extentPath +"Report_"+strDate+"\\"+ envName+"_BDD_Automation_Report_" + strDate + ".json");
            report.attachReporter(json, spark);
            report.setSystemInfo("Host Name", System.getProperty("user.name"));
            report.setSystemInfo("Broswer Name", FileReaderManager.fileRead().getConfiguration().getBrowerName());
            spark.config().setDocumentTitle("COE Automation Report");
            spark.config().setReportName("COE Automation Report");
            spark.config().setTheme(Theme.DARK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;

    }
    public static String CommonPath(){
        String dateName = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
        String path=System.getProperty("user.dir") + "\\ExtentReport\\Report'"+dateName+"'\\";
        return path;
    }
    public static void createExtentTest(String testName) {
        extentTest.set(intiExtentReports().createTest(testName));
    }

    public static ExtentTest getExtentTest() {
        return extentTest.get();
    }

    public void closeExtent() {
        report.flush();
    }
}
