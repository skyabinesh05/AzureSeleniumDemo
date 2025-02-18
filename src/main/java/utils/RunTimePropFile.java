package utils;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.Properties;

public class RunTimePropFile {


    static Logger log = Logger.getLogger(RunTimePropFile.class.getName());

    private RunTimePropFile() {
    }

    public static void createRTP() {
        try {

            Properties prop = new Properties();
            String userDir = Paths.get("").toAbsolutePath().toString();
            String path = userDir + "//configs//runtime.properties";
            String xlsPath = userDir + "//AutomationSuite.xlsx";
            log.info(xlsPath);
            FileInputStream fis = new FileInputStream(xlsPath);
            Sheet configSheet;
            try (Workbook workbook = new XSSFWorkbook(fis)) {
                configSheet = workbook.getSheet("CONFIGURATION");
            }

            String userName = readConfig(configSheet, "USERNAME");
            String accessKey = readConfig(configSheet, "ACCESS_KEY");
            String testingType = readConfig(configSheet, "TESTING_TYPE");

            String browserLocation = readConfig(configSheet, "BROWSER_LOCATION");
            String browserName = readConfig(configSheet, "BROWSER_NAME");
            String browserVersion = readConfig(configSheet, "BROWSER_VERSION");
            String emulaterType = readConfig(configSheet, "EMULATOR_TYPE");
            String emulaterName = readConfig(configSheet, "EMULATOR_NAME");
            String deviceName = readConfig(configSheet, "DEVICE_NAME");
            String tunnelName = readConfig(configSheet, "TUNNEL_NAME");

            String pID = readConfig(configSheet, "PID");
            String password = readConfig(configSheet, "Password");
            if (userName != null)
                prop.put("USERNAME", userName);

            if (accessKey != null)
                prop.put("ACCESS_KEY", accessKey);

            if (testingType != null)
                prop.put("TESTING_TYPE", testingType);

            if (browserLocation != null)
                prop.put("BROWSER_LOCATION", browserLocation);

            if (browserName != null)
                prop.put("BROWSER_NAME", browserName);

            if (browserVersion != null)
                prop.put("BROWSER_VERSION", browserVersion);

            if (deviceName != null)
                prop.put("RealDevice", deviceName);


            if (emulaterType != null)
                prop.put("EMULATOR_TYPE", emulaterType);
            if (emulaterName != null)
                prop.put("EMULATOR_NAME", emulaterName);
            if (tunnelName != null)
                prop.put("TUNNEL_NAME", tunnelName);


            if (pID != null)
                prop.put("PID", pID);
            if (password != null)
                prop.put("Password", password);

            FileOutputStream outputStrem = new FileOutputStream(path);

            prop.store(outputStrem, "This is a sample properties file");
            //log.info("Properties file created......")
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readConfig(Sheet sheetConfig, String rowName) {

        String configData = null;

        try {

            int rowCount = sheetConfig.getPhysicalNumberOfRows() - 1;
            rowCount++;
            int colCount = 1;
            int fetchRowIndex = 0;


            for (int i = 0; i < rowCount; i++) {
                //get the row Element
                Row row = sheetConfig.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    //this gets the cell Value and sets it as blank if it's empty.
                    Cell cell = row.getCell(j);
                    String value = String.valueOf(cell);

                    if (rowName.equalsIgnoreCase(value)) {
                        fetchRowIndex = i;
                        // println "fetchRowIndex:" +fetchRowIndex
                    }
                }
            }

            if (fetchRowIndex != 0) {
                Cell cell = sheetConfig.getRow(fetchRowIndex).getCell(1);
                configData = String.valueOf(cell);


            }
        } catch (Exception e1) {
            e1.printStackTrace();

        }
        log.info(" " + rowName + "  " + configData);
        return configData;
    }


}
