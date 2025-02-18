package coeqa.managers;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.proxy.CaptureType;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.URL;
import java.util.EnumSet;

public class DriverManager {

    public static final Logger Log = Logger.getLogger(Thread.currentThread().getName());
    private static final String strSauceUS = "https://ondemand.saucelabs.com/wd/hub"; //desktop and virtual device - US Data center
    private static final String strSauceEU = "https://ondemand.eu-central-1.saucelabs.com/wd/hub"; //desktop and virtual device - EU Data center
    public static BrowserMobProxy browserProxy;
    static DesiredCapabilities capabilities;
    private static String sauceUserName;
    private static String sauceAccessKey;
    private static String tunnelName;

    static {
        DOMConfigurator.configure(System.getProperty("user.dir") + "//configs/log4j.xml");
    }

    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initSauceConfig() {
        try {
            sauceUserName = FileReaderManager.fileRead().getConfiguration().getSauceUsrName();
            sauceAccessKey = FileReaderManager.fileRead().getConfiguration().getSauceAccssKey();
            tunnelName = FileReaderManager.fileRead().getConfiguration().getTunnelName();
            Log.info("sauceUserName" + sauceUserName);
            Log.info("sauceUserName" + sauceAccessKey);
        } catch (Exception e) {
            Log.info("Exception occurred :" + e);
        }

    }

    public static WebDriver setSauceEmulator() {
        WebDriver webDriver;
        String emulatorType = FileReaderManager.fileRead().getConfiguration().getEmulatorType();
        String emulatorName = FileReaderManager.fileRead().getConfiguration().getEmulatorName();
        String suiteName = FileReaderManager.fileRead().getConfiguration().getRuntimePropValue("SUITE_NAME");
        String platformVersion = FileReaderManager.fileRead().getConfiguration().getRuntimePropValue("DEVICE_PLATFORM_VERSION");
        String appiumVersion = FileReaderManager.fileRead().getConfiguration().getRuntimePropValue("APPIUM_VERSION");
        initSauceConfig();
        try {
            //  DesiredCapabilities capabilities;
            if (emulatorType.toLowerCase().contains("iphone") || (emulatorType.toLowerCase().contains("ipad"))) {
                capabilities = DesiredCapabilities.iphone();
                capabilities.setCapability("platformName", "iOS");
                capabilities.setCapability("browserName", "Safari");
            } else {
                capabilities = DesiredCapabilities.android();
                capabilities.setCapability("platformName", "Android");
                capabilities.setCapability("browserName", "Chrome");
            }
            if (!tunnelName.isEmpty())
                capabilities.setCapability("tunnelIdentifier", tunnelName);

            capabilities.setCapability("username", sauceUserName);
            capabilities.setCapability("accessKey", sauceAccessKey);

            capabilities.setCapability("deviceOrientation", "portrait");
            capabilities.setCapability("appiumVersion", appiumVersion);
            capabilities.setCapability("idleTimeout", "500");
            capabilities.setCapability("commandTimeout", "600");
            capabilities.setCapability("build", "_v1");
            capabilities.setCapability("name", suiteName);
            setSauceCapPlatform(capabilities, emulatorName, platformVersion);
            String region;
            if (tunnelName.toLowerCase().contains("eu")) {
                region = strSauceEU;
            } else {
                region = strSauceUS;
            }
            webDriver = new RemoteWebDriver(new URL(region), capabilities);
            return webDriver;

        } catch (Exception t) {
            throw new AssertionError(emulatorName + " Driver not opened for test: ", t);
        }
    }

    public static void setSauceCapPlatform(DesiredCapabilities caps, String strDeviceName, String strPlatform) {
        caps.setCapability("deviceName", strDeviceName);
        caps.setCapability("platformVersion", strPlatform);
    }

    //Return current driver
    public WebDriver getWebDriver() {
        createWebDriver();
        return driver.get();
    }

    //set the remote driver
    public void setWebDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    //remove the remote webdriver
    public void removeDriver() {
        driver.get().close();
        driver.get().quit();
        driver.remove();
    }

    public void createWebDriver() {

        String browserLoc = FileReaderManager.fileRead().getConfiguration().getBrowerLoc();
        Log.info("BrowserLocation :->" + browserLoc);
        Log.info("BrowerType :->" + FileReaderManager.fileRead().getConfiguration().getBrowerName());
        try {
            if (browserLoc.equalsIgnoreCase("Local")) {
                setWebDriver(setUpLocalBrowser());
            } else {
                setWebDriver(executeFromSauce());
            }
        } catch (Exception e) {
            Log.info("Exception occurred : " + e.getMessage());
        }

    }

/*    public void setupBrowserProxy(){
        browserProxy = new BrowserMobProxyServer();
        browserProxy.start(0);

        EnumSet<CaptureType> captureTypes = CaptureType.getAllContentCaptureTypes();
        captureTypes.addAll(CaptureType.getRequestCaptureTypes());
        captureTypes.addAll(CaptureType.getResponseCaptureTypes());

        browserProxy.setHarCaptureTypes(captureTypes);
        browserProxy.newHar("CharterHAR");
    }*/

    public boolean containsIgnoreCase(String act, String exp) {
        return StringUtils.containsIgnoreCase(act, exp);
    }

    public WebDriver executeFromSauce() {
        WebDriver webDriver;
        String testType = FileReaderManager.fileRead().getConfiguration().getTestingType();
        Log.info("Testing type:" + testType);
        if (containsIgnoreCase(testType, "emulator")) {
            webDriver = setSauceEmulator();
        } else if (containsIgnoreCase(testType, "real")) {
            webDriver = setRealDevice();
        } else if (containsIgnoreCase(testType, "remote")) {
            webDriver = setSauceBrowser();
        } else {
            webDriver = setSauceBrowser();
        }
        return webDriver;
    }

    @SuppressWarnings("deprecation")
    public WebDriver setUpLocalBrowser() {
        WebDriver webDriver;
//        Dimension dimension = new Dimension(1200, 600);
        try {
            String browserName = FileReaderManager.fileRead().getConfiguration().getBrowerName();
            String executionType = FileReaderManager.fileRead().getConfiguration().getRuntimePropValue("EXECUTION_TYPE");
            //   DesiredCapabilities capabilities;
            if (browserName.equalsIgnoreCase("Firefox")) {
//                WebDriverManager.firefoxdriver().setup();
//                cap = DesiredCapabilities.firefox();
//                FirefoxProfile profile = new FirefoxProfile();
//                cap.setCapability(FirefoxDriver.PROFILE, profile);
//                profile.setAcceptUntrustedCertificates(true);
//                webDriver = new FirefoxDriver(cap);
//                return webDriver;
                System.setProperty("webdriver.gecko.driver", "C:\\Users\\ajeeshr\\OneDrive - Virtusa\\Documents\\geckodriver-v0.29.1-win64\\geckodriver.exe");
                capabilities = DesiredCapabilities.firefox();
                capabilities.setCapability("marionette", true);
                webDriver = new FirefoxDriver(capabilities);
                return webDriver;

            } else if (browserName.equalsIgnoreCase("IE")) {

                WebDriverManager.iedriver().setup();
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capabilities.setCapability("requireWindowFocus", true);
                webDriver = new InternetExplorerDriver(capabilities);
                return webDriver;

            } else if (browserName.equalsIgnoreCase("Edge")) {

                WebDriverManager.edgedriver().setup();
                capabilities = DesiredCapabilities.edge();
                capabilities.setCapability("requireWindowFocus", true);
                webDriver = new EdgeDriver(capabilities);
                return webDriver;
            } else if (browserName.equalsIgnoreCase("Chrome")) {
                capabilities = new DesiredCapabilities();
//                WebDriverManager.chromedriver().setup();
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");
                ChromeOptions options = new ChromeOptions();
//                options.setExperimentalOption("useAutomationExtension", false);
                //options.setPageLoadStrategy(PageLoadStrategy.NONE);

//                options.addArguments("ignore-certificate-errors");
//                options.addArguments("disable-infobars");
                options.addArguments("start-maximized");
//                options.addArguments("window-size=1200x600");
//                options.addArguments("--incognito");
//                options.addArguments("--disable-blink-features=AutomationControlled");
//            //    options.addArguments("useAutomationExtension", false);
//                options.addArguments("excludeSwitches", "enable-automation");
//                options.addArguments("--remote-allow-origins=*");
//                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
//                if (executionType.equalsIgnoreCase("headless")) {
//                    options.addArguments("headless");
//                }

// More Performance Traces like devtools.timeline, enableNetwork and enablePage
              /*  Map<String, Object> perfLogPrefs = new HashMap<>();
                perfLogPrefs.put("traceCategories", "browser,devtools.timeline,devtools");
                perfLogPrefs.put("enableNetwork", true);
                perfLogPrefs.put("enablePage", true);
                options.setExperimentalOption("perfLoggingPrefs", perfLogPrefs);

// For Enabling performance Logs for WebPageTest
                //   LoggingPreferences logPrefs = new LoggingPreferences();
                // logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
                //    cap.setCapability("goog:loggingPrefs", logPrefs);
                //    cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);*/
                capabilities.merge(options);
                webDriver = new ChromeDriver(capabilities);
                System.out.println("Driver Capabilites " + ((RemoteWebDriver) webDriver).getCapabilities().asMap().toString());

            } else {

                browserProxy = new BrowserMobProxyServer();
                browserProxy.start(0);

                Proxy seleniumBrowserProxy = new Proxy();
                seleniumBrowserProxy.setHttpProxy("localhost:" + browserProxy.getPort());
                seleniumBrowserProxy.setSslProxy("localhost:" + browserProxy.getPort());
                System.out.println("Selenium Proxy Port " + browserProxy.getPort());
                //System.setProperty("webdriver.chrome.silentOutput", "true");
                //WebDriverManager.chromedriver().setup();
                capabilities = new DesiredCapabilities();
                // Set ACCEPT_SSL_CERTS variable to true
                capabilities.setCapability(CapabilityType.PROXY, seleniumBrowserProxy);
                capabilities.acceptInsecureCerts();
                // cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                //cap.setCapability("applicationCacheEnabled",false);

                EnumSet<CaptureType> captureTypes = CaptureType.getAllContentCaptureTypes();
                captureTypes.addAll(CaptureType.getCookieCaptureTypes());
                captureTypes.addAll(CaptureType.getHeaderCaptureTypes());
                captureTypes.addAll(CaptureType.getRequestCaptureTypes());
                captureTypes.addAll(CaptureType.getResponseCaptureTypes());

                browserProxy.setHarCaptureTypes(captureTypes);
                browserProxy.newHar("myhar");

                System.setProperty("webdriver.chrome.silentOutput", "true");
                WebDriverManager.chromedriver().setup();
                capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("useAutomationExtension", false);
                if (executionType.equalsIgnoreCase("headless")) {
                    options.addArguments("headless");
                }
                options.addArguments("window-size=1200x600");
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                webDriver = new ChromeDriver(capabilities);
            }
//            webDriver.manage().window().setSize(dimension);
            webDriver.manage().window().maximize();
            webDriver.manage().deleteAllCookies();
            return webDriver;
        } catch (Exception t) {
            throw new AssertionError(" Driver not opened", t);
        }
    }

    public BrowserMobProxy getProxyObj() {
        System.out.println("PROXY OBJECT " + browserProxy.toString());
        return browserProxy;
    }

    public WebDriver setSauceBrowser() {
        WebDriver webDriver;
        try {
            initSauceConfig();
            MutableCapabilities sauceOptions = new MutableCapabilities();
            MutableCapabilities browserOptions;
            String browserName = FileReaderManager.fileRead().getConfiguration().getBrowerName();
            String browserVer = FileReaderManager.fileRead().getConfiguration().getRunTimeBrowserVersion();
            String suiteName = FileReaderManager.fileRead().getConfiguration().getRuntimePropValue("SUITE_NAME");
            Log.info("browser Name: " + browserName);
            Log.info(browserVer);
            String strSauceConnect = FileReaderManager.fileRead().getConfiguration().getTunnelName();
            sauceOptions.setCapability("username", sauceUserName);
            sauceOptions.setCapability("accessKey", sauceAccessKey);
            sauceOptions.setCapability("idleTimeout", "500");
            sauceOptions.setCapability("commandTimeout", "600");
            sauceOptions.setCapability("build", suiteName);
            sauceOptions.setCapability("name", suiteName + "_" + browserName + "_" + browserVer);
            if (!tunnelName.isEmpty())
                sauceOptions.setCapability("tunnelIdentifier", tunnelName);

            switch (browserName.toLowerCase()) {
                case "firefox":
                    sauceOptions.setCapability("screenResolution", "1920x1080");
                    browserOptions = new FirefoxOptions();
                    browserOptions.setCapability("platformName", "Windows 10");
                    browserOptions.setCapability("browserVersion", browserVer);
                    browserOptions.setCapability("sauce:options", sauceOptions);
                    break;
                case "chrome":
                    sauceOptions.setCapability("screenResolution", "1920x1080");
                    browserOptions = new ChromeOptions();
                    browserOptions.setCapability("platformName", "Windows 10");
                    ((ChromeOptions) browserOptions).setExperimentalOption("w3c", true);
                    browserOptions.setCapability("browserVersion", browserVer);
                    browserOptions.setCapability("sauce:options", sauceOptions);
                    break;
                case "ie":
                    sauceOptions.setCapability("screenResolution", "1920x1080");
                    browserOptions = new InternetExplorerOptions();
                    browserOptions.setCapability("platformName", "Windows 10");
                    browserOptions.setCapability("browserVersion", browserVer);
                    browserOptions.setCapability("sauce:options", sauceOptions);
                    break;
                case "edge":
                    sauceOptions.setCapability("screenResolution", "1920x1080");
                    browserOptions = new EdgeOptions();
                    browserOptions.setCapability("platformName", "Windows 10");
                    browserOptions.setCapability("browserVersion", browserVer);
                    browserOptions.setCapability("sauce:options", sauceOptions);
                    break;
                case "safari":
                    sauceOptions.setCapability("screenResolution", "1920x1440");
                    browserOptions = new SafariOptions();
                    browserOptions.setCapability("platformName", "macOS 12");
                    browserOptions.setCapability("browserVersion", browserVer);
                    browserOptions.setCapability("sauce:options", sauceOptions);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + browserName.toLowerCase());
            }
            String region;
            if (strSauceConnect.toLowerCase().contains("eu")) {
                region = strSauceEU;
            } else {
                region = strSauceUS;
            }
            webDriver = new RemoteWebDriver(new URL(region), browserOptions);
            webDriver.manage().window().maximize();

            return webDriver;

        } catch (Exception t) {
            String msg = "Sauce remote driver not created. Possible causes - check the tunnel name";
            throw new AssertionError(msg + " Driver not opened for test: ", t);
        }
    }

    public WebDriver setRealDevice() {
        WebDriver webDriver;
        //  DesiredCapabilities capabilities;
        String realDeviceName = FileReaderManager.fileRead().getConfiguration().getRealDevice();
        try {

            if (containsIgnoreCase(realDeviceName, "iphone")) {
                capabilities = DesiredCapabilities.iphone();
                capabilities.setCapability("platformName", "iOS");
                capabilities.setCapability("platformVersion", "13.2");
                capabilities.setCapability("browserName", "Safari");
            } else {
                capabilities = DesiredCapabilities.android();
                capabilities.setCapability("platformName", "Android");
                capabilities.setCapability("browserName", "Chrome");
                capabilities.setCapability("platformVersion", "7.0");
            }
            capabilities.setCapability("appiumVersion", "1.16.0");
            capabilities.setCapability("username", sauceUserName);
            capabilities.setCapability("accessKey", sauceAccessKey);

            if (!tunnelName.isEmpty())
                capabilities.setCapability("tunnelIdentifier", tunnelName);

            if (realDeviceName.toLowerCase().contains("galaxy"))
                capabilities.setCapability("deviceName", "Samsung Galaxy Tab.*");

            else if (realDeviceName.toLowerCase().contains("google"))
                capabilities.setCapability("deviceName", "Google.*");
            else if (realDeviceName.toLowerCase().contains("one plus"))
                capabilities.setCapability("deviceName", "OnePlus.*");

            else if (realDeviceName.toLowerCase().contains("iphone"))
                capabilities.setCapability("deviceName", "iPhone.*");
            else
                capabilities.setCapability("deviceName", "iPad.*");

            String region;
            if (realDeviceName.toLowerCase().contains("eu")) {
                region = strSauceEU;
            } else {
                region = strSauceUS;
            }
            webDriver = new RemoteWebDriver(new URL(region), capabilities);

            return webDriver;
        } catch (Exception t) {
            throw new AssertionError(realDeviceName + " Driver not opened for test: ", t);
        }

    }

    public void closeDriver() {
        removeDriver();
    }

}