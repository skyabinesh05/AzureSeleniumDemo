package coeqa.datareader;

import org.jfree.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {


    private static final String filename = "configs//";
    // private Properties prop;
    public static Properties prop;
    private static Properties rtProp;

    public ConfigReader() {
        initConfig();
        intiRTProp();

    }

    /**
     *
     */
    public void initConfig() {
        try (FileInputStream is = new FileInputStream(filename + "config.properties")) {
            prop = new Properties();
            prop.load(is);
        } catch (Exception e) {

            Log.info("Exception occurred : " + e.getMessage());
        }

    }

    public void intiRTProp() {
        try (FileInputStream is = new FileInputStream(filename + "runtime.properties")) {
            rtProp = new Properties();
            rtProp.load(is);
        } catch (Exception e) {

            Log.info("Exception occurred : " + e.getMessage());
        }

    }

    public String getURLForTestExecution() throws IOException {
        FileInputStream is = new FileInputStream(System.getProperty("user.dir") + "\\configs\\" + "runtime.properties");
        Properties propURL = new Properties();
        propURL.load(is);
        String prop_Environment = propURL.getProperty("ENVIRONMENT");
        System.out.println("ENVIRONMENT DATA AS PER RUNTIME PROPERTIES FILE " + prop_Environment);
        String prop_ChannelName = propURL.getProperty("CHANNEL_NAME");
        System.out.println("CHANNEL NAME AS PER RUNTIME PROPERTIES FILE " + prop_ChannelName);
//        String channelURL = getURLFromEnvironmentDataXML(prop_Environment, prop_ChannelName);
        String channelURL = Test(prop_Environment, prop_ChannelName);
        //   System.out.println("CHANNEL URL FETCHED FROM ENVIRONMENT DATA XML "+channelURL);
        return channelURL;
    }

    public String getURLForTestExecution(String channel) throws IOException {
        FileInputStream is = new FileInputStream(System.getProperty("user.dir") + "\\configs\\" + "runtime.properties");
        Properties propURL = new Properties();
        propURL.load(is);
        String prop_Environment = propURL.getProperty("ENVIRONMENT");
        System.out.println("ENVIRONMENT DATA AS PER RUNTIME PROPERTIES FILE " + prop_Environment);
//        String channelURL = getURLFromEnvironmentDataXML(prop_Environment, prop_ChannelName);
        String channelURL = Test(prop_Environment, channel.toUpperCase());
        //   System.out.println("CHANNEL URL FETCHED FROM ENVIRONMENT DATA XML "+channelURL);
        return channelURL;
    }

    public String getURLFromEnvironmentDataXML(String environmentKey, String channelKey) {
        System.out.println("ENVIRONMENT KEY " + environmentKey);
        System.out.println("CHANNEL KEY" + channelKey);
        try {
            Document document = getEnvironmentXMLDocumentObject();
            NodeList nodeList = document.getElementsByTagName(environmentKey);
            NodeList environmentList, urlValueList = null;

            for (int i = 0; i < nodeList.getLength(); i++) {
                environmentList = nodeList.item(i).getChildNodes();

                for (int j = 0; j < environmentList.getLength(); j++) {
                    urlValueList = environmentList.item(j).getChildNodes();
                    if (environmentKey.equalsIgnoreCase("QA")) {
                        urlValueList = environmentList.item(j).getChildNodes();
                        if (environmentList.item(j).getNodeName().equalsIgnoreCase(channelKey)) {
                            for (int k = 0; k < urlValueList.getLength(); k++) {
                                System.out.println(urlValueList.item(k).getNodeName());
                                if (urlValueList.item(k).getNodeName().equals("URL")) {
                                    System.out.println("URL VALUE " + urlValueList.item(k).getTextContent());
                                    return urlValueList.item(k).getTextContent();
                                }
                            }
                        }
                    } else if (environmentKey.equalsIgnoreCase("STAGE")) {
                        urlValueList = environmentList.item(j).getChildNodes();
                        if (environmentList.item(j).getNodeName().equalsIgnoreCase(channelKey)) {
                            for (int k = 0; k < urlValueList.getLength(); k++) {
                                System.out.println(urlValueList.item(k).getNodeName());
                                if (urlValueList.item(k).getNodeName().equals("URL")) {
                                    System.out.println("URL VALUE " + urlValueList.item(k).getTextContent());
                                    return urlValueList.item(k).getTextContent();
                                }
                            }
                        }
                    } else if (environmentKey.equalsIgnoreCase("PROD")) {
                        urlValueList = environmentList.item(j).getChildNodes();
                        if (environmentList.item(j).getNodeName().equalsIgnoreCase(channelKey)) {
                            for (int k = 0; k < urlValueList.getLength(); k++) {
                                System.out.println(urlValueList.item(k).getNodeName());
                                if (urlValueList.item(k).getNodeName().equals("URL")) {
                                    System.out.println("URL VALUE " + urlValueList.item(k).getTextContent());
                                    return urlValueList.item(k).getTextContent();
                                }
                            }
                        }
                    } else if (environmentKey.equalsIgnoreCase("SIT")) {
                        urlValueList = environmentList.item(j).getChildNodes();
                        if (environmentList.item(j).getNodeName().equalsIgnoreCase(channelKey)) {
                            for (int k = 0; k < urlValueList.getLength(); k++) {
                                System.out.println(urlValueList.item(k).getNodeName());
                                if (urlValueList.item(k).getNodeName().equals("URL")) {
                                    System.out.println("URL VALUE " + urlValueList.item(k).getTextContent());
                                    return urlValueList.item(k).getTextContent();
                                }
                            }
                        }
                    }
                }
            }
        } finally {
            System.out.println("URL FETCHED");
        }
        return "EMPTY URL";

    }

//    public String Test(String environmentKey, String channelKey) {
//        String url = "";
//        System.out.println("ENVIRONMENT KEY ===>" + environmentKey);
//        System.out.println("CHANNEL KEY ===>" + channelKey);
//        try {
//            File file = new File("configs/environmentData.xml");
//            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//            DocumentBuilder db = dbf.newDocumentBuilder();
//            Document doc = db.parse(file);
//            doc.getDocumentElement().normalize();
//            //  System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
//            NodeList nodeList = doc.getElementsByTagName(environmentKey);
//            for (int itr = 0; itr < nodeList.getLength(); itr++) {
//                Node node = nodeList.item(itr);
//                //   System.out.println("\nNode Name :" + node.getNodeName());
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    Element eElement = (Element) node;
//                    //   System.out.println("Env: "+ eElement.getElementsByTagName(channelKey).item(0).getTextContent());
//                    url = eElement.getElementsByTagName(channelKey).item(0).getTextContent();
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("error ===>" + e.getMessage());
//        }
//        return url.trim();
//    }

    public String Test(String environmentKey, String channelKey) {
        String url = "";
        System.out.println("ENVIRONMENT KEY ===>" + environmentKey);
        System.out.println("CHANNEL KEY ===>" + channelKey);
        try {
            File file = new File("configs/environmentData.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName(environmentKey);
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    NodeList channelNodes = eElement.getElementsByTagName(channelKey);
                    if (channelNodes.getLength() > 0) {
                        url = channelNodes.item(0).getTextContent();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("error ===>" + e.getMessage());
        }
        return url.trim();
    }


    public Document getEnvironmentXMLDocumentObject() {
        Document document = null;
        try {
            File file = new File(System.getProperty("user.dir") + "\\configs\\environmentData.xml");
            System.out.println("file" + file);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        return document;
    }

    public String getAddressForTestExecution() throws IOException {
        FileInputStream is = new FileInputStream(System.getProperty("user.dir") + "\\configs\\" + "runtime.properties");
        Properties propURL = new Properties();
        propURL.load(is);
        String prop_Address = propURL.getProperty("ADDRESS");
        System.out.println("ADDRESS AS PER RUNTIME PROPERTIES FILE " + prop_Address);
        return prop_Address;
    }

    public String getSauceUsrName() {
        String browser = rtProp.getProperty("USERNAME");
        if (browser != null) return browser;
        else throw new RuntimeException("USERNAME not specified in the Configuration.properties file.");
    }

    public String getSauceAccssKey() {
        String browser = rtProp.getProperty("ACCESS_KEY");
        if (browser != null) return browser;
        else throw new RuntimeException("ACCESS_KEY not specified in the Configuration.properties file.");
    }

    public String getTestingType() {
        String browser = rtProp.getProperty("TESTING_TYPE");
        if (browser != null) return browser;
        else throw new RuntimeException("TESTING_TYPE not specified in the Configuration.properties file.");
    }

    public String getBrowerLoc() {
        String browser = rtProp.getProperty("BROWSER_LOCATION");
        if (browser != null) return browser;
        else throw new RuntimeException("Browser not specified in the Configuration.properties file.");
    }

    public String getBrowerName() {
        String browser = rtProp.getProperty("BROWSER_NAME");
        if (browser != null) return browser;
        else throw new RuntimeException("BROWSER_NAME not specified in the Configuration.properties file.");
    }

    public String getRunTimeBrowserVersion() {
        String browser = rtProp.getProperty("BROWSER_VERSION");
        if (browser != null) return browser;
        else throw new RuntimeException("BROWSER_VERSION not specified in the Configuration.properties file.");
    }

    public String getRealDevice() {
        String browser = rtProp.getProperty("RealDevice");
        if (browser != null) return browser;
        else throw new RuntimeException("RealDevice not specified in the Configuration.properties file.");
    }

    public String getEmulatorType() {
        String browser = rtProp.getProperty("EMULATOR_TYPE");
        if (browser != null) return browser;
        else throw new RuntimeException("EMULATOR_TYPE not specified in the Configuration.properties file.");
    }

    public String getEmulatorName() {
        String browser = rtProp.getProperty("EMULATOR_NAME");
        if (browser != null) return browser;
        else throw new RuntimeException("EmulatorName not specified in the Configuration.properties file.");
    }


    public String getTunnelName() {
        String browser = rtProp.getProperty("TUNNEL_NAME");
        if (browser != null) return browser;
        else throw new RuntimeException("Tunnel not specified in the Configuration.properties file.");
    }

    public String getPID() {
        String browser = rtProp.getProperty("PID");
        if (browser != null) return browser;
        else throw new RuntimeException("PID not specified in the Configuration.properties file.");
    }

    public String getPsswd() {
        String browser = rtProp.getProperty("Password");
        if (browser != null) return browser;
        else throw new RuntimeException("password not specified in the Configuration.properties file.");
    }

    public String getEnv() {
        String env = rtProp.getProperty("ENVIRONMENT");
        if (env != null) return env;
        else throw new RuntimeException("Env not specified in the runtime.properties file.");
    }

    public String getRuntimePropValue(String propKey) {
        String rPropValue = rtProp.getProperty(propKey);
        if (rPropValue != null) return rPropValue;
        else throw new RuntimeException("prop not specified in the runtime.properties file.");
    }
    public String getConfigPropValue(String propKey) {
        String rPropValue = rtProp.getProperty(propKey);
        if (rPropValue != null) return rPropValue;
        else throw new RuntimeException("prop not specified in the config properties file.");
    }
    //End of Runtime config getters methods
    public String getbrowser() {
        String browser = prop.getProperty("browsername");
        if (browser != null) return browser;
        else throw new RuntimeException("Browser not specified in the Configuration.properties file.");
    }


    public Properties getConfig() {
        return prop;
    }


    public static String getURL() {
        String url = prop.getProperty("URL");
        if (url != null) return url;
        else throw new RuntimeException("URL not specified in the Configuration.properties file.");
    }

    public long getWaitTime() {
        String sync = prop.getProperty("syncTime");
        if (sync != null) return Long.parseLong(sync);
        else throw new RuntimeException("syncTime not specified in the Configuration.properties file.");
    }


    public String getisRemote() {
        String isremote = prop.getProperty("isRemote");
        if (isremote != null) return isremote;
        else throw new RuntimeException("isRemote not specified in the Configuration.properties file.");
    }

    public String getHubURL() {
        String hub = prop.getProperty("HubURL");
        if (hub != null) return hub;
        else throw new RuntimeException("HUb not specified in the Configuration.properties file.");
    }

    public String getBrowserVersion() {
        String version = prop.getProperty("Browser_Version");
        if (version != null) return version;
        else throw new RuntimeException("Version not specified in the Configuration.properties file.");
    }

    public String getTunnel() {
        String browser = prop.getProperty("strSauceConnect");
        if (browser != null) return browser;
        else throw new RuntimeException("TunnelName not specified in the Configuration.properties file.");
    }

    public String getSauceUserName() {
        String browser = prop.getProperty("userName");
        if (browser != null) return browser;
        else throw new RuntimeException("Sauce UserName not specified in the Configuration.properties file.");
    }

    public String getSauceAccessKey() {
        String browser = prop.getProperty("accessKey");
        if (browser != null) return browser;
        else throw new RuntimeException("Sauce UserName not specified in the Configuration.properties file.");
    }


    public String getBrowserType() {
        String browser = prop.getProperty("browserType");
        if (browser != null) return browser;
        else throw new RuntimeException("BrowserType not specified in the Configuration.properties file.");
    }

    public String getExtentConfig() {
        return System.getProperty("user.dir") + "./extent-config.xml";
    }

    public String getReportConfigPath() {
        String reportConfigPath = System.getProperty("reportConfigPath");
        if (reportConfigPath != null) return reportConfigPath;
        else
            throw new RuntimeException("Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");
    }

    public static String getEnvName() {
        //  String environment = FileReaderManager.fileRead().getConfiguration().getEnvName();
        String environment = rtProp.getProperty("ENVIRONMENT");
        if (environment == null) {
            environment = prop.getProperty("URL");
        }
        if (environment != null) return environment;
        else throw new RuntimeException("Environment not specified in Run time");
    }

    public String getAppliFlag() {
        return prop.getProperty("applitoolFlag");
    }
}
