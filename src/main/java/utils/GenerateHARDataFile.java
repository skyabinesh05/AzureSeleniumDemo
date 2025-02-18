package utils;

import coeqa.managers.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.apache.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

public class GenerateHARDataFile extends DriverManager {

    public Logger Log = Logger.getLogger(this.getClass().getSimpleName());

        public final void writeHARDataToHARJsonFile() throws IOException {
            Har har = browserProxy.getHar();

            File generateHARFile = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\HARData\\CharterHARData.har");
            har.writeTo(generateHARFile);

            File generateJSONFile = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\HARData\\CharterJSONData.har");
            har.writeTo(generateJSONFile);

            Log.info("GENERATED HAR AND JSON FILE SUCCESSFULLY");
        }

        public static void main(String[] args) throws InterruptedException, IOException {
            BrowserMobProxy browserProxy = new BrowserMobProxyServer();
            browserProxy.start(0);

            Proxy seleniumBrowserProxy = new Proxy();
            seleniumBrowserProxy.setHttpProxy("localhost:"+browserProxy.getPort());
            seleniumBrowserProxy.setSslProxy("localhost:"+browserProxy.getPort());
            System.out.println("Selenium Proxy Port "+browserProxy.getPort());

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(CapabilityType.PROXY, seleniumBrowserProxy);
            capabilities.acceptInsecureCerts();
            capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

            EnumSet<CaptureType> captureTypes = CaptureType.getAllContentCaptureTypes();
            captureTypes.addAll(CaptureType.getCookieCaptureTypes());
            captureTypes.addAll(CaptureType.getHeaderCaptureTypes());
            captureTypes.addAll(CaptureType.getRequestCaptureTypes());
            captureTypes.addAll(CaptureType.getResponseCaptureTypes());

            browserProxy.setHarCaptureTypes(captureTypes);
            browserProxy.newHar("myhar");

            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.merge(capabilities);
            WebDriver webDriver = new ChromeDriver(options);

            System.out.println("Driver Capabilites "+((RemoteWebDriver)webDriver).getCapabilities().asMap().toString());

            webDriver.get("https://baedirectsales-stage.webapps.rr.com/checkout/localization?sc=SMB-BAE-OCBO");

            webDriver.manage().window().maximize();
            Thread.sleep(10000);

            Har har = browserProxy.getHar();

            File generateHARFile = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\HARData\\CharterHARData.har");
            har.writeTo(generateHARFile);

            File generateJSONFile = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\HARData\\CharterJSONData.json");
            har.writeTo(generateJSONFile);

            System.out.println("GENERATED HAR AND JSON FILE SUCCESSFULLY");
            webDriver.close();
            if(webDriver.getCurrentUrl() == null){
                System.out.print("NULL ");
            }
        }

}
