package coeqa.datareader;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.apache.poi.ss.usermodel.CellType.STRING;

public class GetdatafromExcel {

    public static final Logger Log = Logger.getLogger(GetdatafromExcel.class.getName());
    public GetdatafromExcel() {
        DOMConfigurator.configure(System.getProperty("user.dir") + "//configs/log4j.xml");
    }

    private static final String path = Paths.get("").toAbsolutePath().toString() + "//src//test//resources//DataFiles//";


    public  Map<String, Map<String, String>> get(String workBookName, String sheetName) {

        FileInputStream fis;
//        Log.info("workbook name==>" + workBookName);
//        Log.info("sheetName==>" + sheetName);
        Map<String, String> tcMap;
        Map<String, Map<String, String>> resultMap = new HashMap<>();
        try {
            fis = new FileInputStream(path + workBookName + ".xls");
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            HSSFSheet ws = wb.getSheet(sheetName);
            int rows = ws.getPhysicalNumberOfRows();
//            System.out.println("rows==>"+rows);
            for (int i = 1; i < rows; i++) {
                tcMap = new HashMap<>();
                int cols = ws.getRow(0).getPhysicalNumberOfCells();
                //System.out.println("cols==>"+(cols-1));
                String testname = ws.getRow(i).getCell(0).toString();
               // System.out.println("Testname==>"+testname);
                for (int j = 1; j < cols; j++) {

                    String key = testname + "_" + ws.getRow(0).getCell(j).toString();
                    if(ws.getRow(i).getCell(j)==null){
                        tcMap.put(key, null);

                    }
                    else{
                    String value = ws.getRow(i).getCell(j).toString();
                    tcMap.put(key, value);
                   // System.out.println(value+"key==>"+key);

                }}
                //System.out.println(test+"key==>"+key);
                resultMap.put(testname, tcMap);
               // fis.close();

            }

        } catch (Exception e) {
            Log.info("Exception occurred : " + e.getMessage()+" "+e.getStackTrace());
        }

        return resultMap;
    }

    public static  String fetchdata(String workBookName, String sheetName, String testcaseName, String columnName) {
        Map<String, Map<String, String>> result = new GetdatafromExcel().get(workBookName, sheetName);
        return result.get(testcaseName).get(testcaseName + "_" + columnName);

    }
    public void updateScenarioStatus(String workBookName, String sheetName, String testCaseName, String status) {
        String name;
        try {
            name=path + workBookName+".xls";
            System.out.println("workBookName-> "+workBookName);
            System.out.println("name-> "+name);
            FileInputStream fis = new FileInputStream(name);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheet(sheetName);
            Cell cell ;
            if(testCaseName!=null){
//            System.out.println(sheetName+"====="+testCaseName+"-----sheet-> "+sheetName);
            int row = findRow(sheet, testCaseName);
            int col = findColumn(sheet, "Status");
            if(row>=0 || col>=0){
            	
            System.out.println("row-> "+row);
            System.out.println("col-> "+col);
            HSSFRow sheetRow = sheet.getRow(row);
            if (sheetRow == null) {
                sheetRow = sheet.createRow(row);
            }
            //Updates the cell value
            cell = sheetRow.getCell(col);
            if (cell == null) {
                cell = sheetRow.createCell(col);
            }
            cell.setCellValue(status);
            fis.close();

            FileOutputStream outFile = new FileOutputStream(name);

            workbook.write(outFile);
            outFile.close();

        } }}catch (Exception e) {
            e.printStackTrace();
        }
    }
    private  int findRow(HSSFSheet sheet, String cellContent) {
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getCellType() == STRING) {
                    if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
                        return row.getRowNum();
                    }
                }
            }
        }
        return -1;
    }

    private  int findColumn(HSSFSheet sheet, String cellContent) {
        Row row1 = sheet.getRow(0);
        for (Cell cell : row1) {
            if (cell.getCellType() == STRING) {
                if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
                    return cell.getColumnIndex();
                }
            }
        }
        return -1;
    }

    public void writeToExcel(String workBookName, String sheetName, String testCaseName, String colName, String status) {
        String name;
        try {
            name = path + workBookName + ".xls";
            System.out.println("workBookName-> " + workBookName);
            System.out.println("name-> " + name);

            FileInputStream fis = new FileInputStream(name);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheet(sheetName);

            HSSFCellStyle cellStyle = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            Cell cell;
            if (testCaseName != null) {
                System.out.println(sheetName + "=====" + testCaseName + "-----sheet-> " + sheetName);
                int row = findRow(sheet, testCaseName);
                int col = findColumn(sheet, colName);
                if (row >= 0 || col >= 0) {

                    System.out.println("row-> " + row);
                    System.out.println("col-> " + col);
                    HSSFRow sheetRow = sheet.getRow(row);
                    if (sheetRow == null) {
                        sheetRow = sheet.createRow(row);
                    }
                    //Updates the cell value
                    cell = sheetRow.getCell(col);
                    if (cell == null) {
                        cell = sheetRow.createCell(col);
                    }
                    if(status.equalsIgnoreCase("PASSED")) {
                        cellStyle.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.index);
                        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        cellStyle.setFillPattern(FillPatternType.LEAST_DOTS);
                        cellStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
                        font.setFontName(XSSFFont.DEFAULT_FONT_NAME);
                        font.setFontHeightInPoints((short) 11);
                        font.setColor(IndexedColors.WHITE.getIndex());
                        font.setBold(true);
                        cellStyle.setFont(font);
                        cell.setCellValue(status);
                        cell.setCellStyle(cellStyle);
                    } else if(status.equalsIgnoreCase("FAILED")){
                        cellStyle.setFillBackgroundColor(IndexedColors.RED.index);
                        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        cellStyle.setFillPattern(FillPatternType.LEAST_DOTS);
                        cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                        font.setFontName(XSSFFont.DEFAULT_FONT_NAME);
                        font.setFontHeightInPoints((short) 11);
                        font.setColor(IndexedColors.WHITE.getIndex());
                        font.setBold(true);
                        cellStyle.setFont(font);
                        cell.setCellValue(status);
                        cell.setCellStyle(cellStyle);
                    } else {
                        cell.setCellValue(status);
                    }

                    fis.close();

                    FileOutputStream outFile = new FileOutputStream(name);

                    workbook.write(outFile);
                    outFile.close();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}