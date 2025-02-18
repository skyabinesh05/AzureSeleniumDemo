package utils;

import coeqa.managers.FileReaderManager;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;

public class ExcelActions {

    //Logger logger = LogManager.getLogger(this.getClass().getName());

    private static HSSFWorkbook workbook;
    private static HSSFSheet sheet;
    private static HSSFRow row;
    private static HSSFCell cell;

    public static void fWriteTestNameToRunTimeExcel(String testName) throws IOException {
        String td_Value = "";
        //writeDateAsColumnHeaderToExcel();
        String filePath = Constants.testDatafile_PATH + Constants.testDataFile_Name;

        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);

        System.out.println("TEST NAME " + testName);
        System.out.println("FILE NAME " + filePath);

        workbook = new HSSFWorkbook(inputStream);
        sheet = workbook.getSheet(Constants.testDatafile_RuntimeSheetName);

        int rowCount = getRowCount();
        int rowNumber = 0;
        if (rowCount > 0) {
            rowNumber = getMatchingRowNumber(rowCount, "TESTNAME");
        } else {
            System.out.println(filePath + " is having no rows... Please Verify the TestData Sheet");
        }

        String cellValue = testName;
        sheet.getRow(1).createCell(2).setCellValue(cellValue);

        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();

        inputStream.close();
        writeDateAsColumnHeaderToExcel();
    }

    public static String fGetTestNameFromRunTimeExcel() throws IOException {

        String td_Value = "";
        String filePath = Constants.testDatafile_PATH + Constants.testDataFile_Name;

        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);

        workbook = new HSSFWorkbook(inputStream);
        sheet = workbook.getSheet(Constants.testDatafile_RuntimeSheetName);

        cell = sheet.getRow(1).getCell(2);
        td_Value = cell.toString();
        //  System.out.println("TESTNAME"+td_Value);
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();
        inputStream.close();

        return td_Value;
    }


    public static String getTestData(String td_Key) throws IOException {

        String td_Value = "";
        String filePath = Constants.testDatafile_PATH + Constants.testDataFile_Name;

        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);

        workbook = new HSSFWorkbook(inputStream);
        sheet = workbook.getSheet(Constants.testDatafile_RuntimeSheetName);

        int rowCount = getRowCount();
        int rowNumber = 0;
        if (rowCount > 0) {
            rowNumber = getMatchingRowNumber(rowCount, td_Key);
        } else {
            System.out.println(filePath + " is having no rows... Please Verify the TestData Sheet");
        }
        cell = sheet.getRow(rowNumber).getCell(2);
        td_Value = cell.toString();

        inputStream.close();

        return td_Value;
    }

    public static int getRowCount() {
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        return rowCount;
    }

    public static int getColumnCountBasedOnColumnHeaders() throws IOException {
        String td_Value = "";
        String filePath = Constants.testDatafile_PATH + Constants.testDataFile_Name;

        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);

        workbook = new HSSFWorkbook(inputStream);
        String runTimeTestName = fGetTestNameFromRunTimeExcel();
        String env = FileReaderManager.fileRead().getConfiguration().getEnv();
        if (env.equalsIgnoreCase("QA")) {
            sheet = workbook.getSheet(Constants.testDatafile_QA);
        } else if (env.equalsIgnoreCase("UAT")) {
            sheet = workbook.getSheet(Constants.testDatafile_UAT);
        } else if (env.equalsIgnoreCase("PROD")) {
            sheet = workbook.getSheet(Constants.testDatafile_PROD);
        }

        int columnCount1 = sheet.getRow(0).getPhysicalNumberOfCells();
        return columnCount1;
    }

    public static int getMatchingRowNumber(int rowCount, String td_Key) {
        int rowNumber = 0;

        for (int i = 0; i <= rowCount; i++) {
            cell = sheet.getRow(i).getCell(1);
            String cellValue = cell.toString();
            if (cellValue.equals(td_Key)) {
                rowNumber = i;
            }
        }

        return rowNumber;
    }

    public static void writeValueToExcel1(String td_Key, String cellValue) throws IOException {

        String td_Value = "";
        //writeDateAsColumnHeaderToExcel();
        String filePath = Constants.testDatafile_PATH + Constants.testDataFile_Name;

        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);

        workbook = new HSSFWorkbook(inputStream);

        // String runTimeTestName = fGetTestNameFromRunTimeExcel();
        //  System.out.println("TESTNAME" + runTimeTestName);
        String env = FileReaderManager.fileRead().getConfiguration().getEnv();
        if (env.equalsIgnoreCase("QA")) {
            sheet = workbook.getSheet(Constants.testDatafile_QA);
        } else if (env.equalsIgnoreCase("UAT")) {
            sheet = workbook.getSheet(Constants.testDatafile_UAT);
        } else if (env.equalsIgnoreCase("PROD")) {
            sheet = workbook.getSheet(Constants.testDatafile_PROD);
        }
        int rowCount = getRowCount();
        int rowNumber = 0;
        if (rowCount > 0) {
            rowNumber = getMatchingRowNumber(rowCount, td_Key);
        } else {
            System.out.println(filePath + " is having no rows... Please Verify the TestData Sheet");
        }

        int columnCount = getColumnCountBasedOnColumnHeaders();
        System.out.print("COLUMN COUNT" + columnCount);

      /*  HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.toHSSFColor(RED));
        style.setFont(font);
*/
        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormatter df = new DataFormatter();
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            String cellVal = df.formatCellValue(row.getCell(columnCount));
            if(cellVal.equals(null) || cellVal.isEmpty()) {
                row.createCell(columnCount).setCellValue(createHelper.createRichTextString(td_Key));

                //  row.createCell(columnCount).setCellStyle(style);
                row.createCell(columnCount + 1).setCellValue(cellValue);
                break;
            } else {
                System.out.println("cellValue is : " + cellVal);
            }
        }

        //    sheet.getRow(rowNumber).createCell(columnCount).setCellValue(cellValue);

        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();

        inputStream.close();
    }


    public static void writeValueToExcel(String td_Key, String cellValue) throws IOException {

        String td_Value = "";
        //writeDateAsColumnHeaderToExcel();
        String filePath = Constants.testDatafile_PATH + Constants.testDataFile_Name;

        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);

        workbook = new HSSFWorkbook(inputStream);

        String env = FileReaderManager.fileRead().getConfiguration().getEnv();
        if (env.equalsIgnoreCase("QA")) {
            sheet = workbook.getSheet(Constants.testDatafile_QA);
        } else if (env.equalsIgnoreCase("UAT")) {
            sheet = workbook.getSheet(Constants.testDatafile_UAT);
        } else if (env.equalsIgnoreCase("PROD")) {
            sheet = workbook.getSheet(Constants.testDatafile_PROD);
        }

        int columnCount = getColumnCountBasedOnColumnHeaders();
        System.out.print("COLUMN COUNT" + columnCount +"\n");

        HSSFCreationHelper createHelper = workbook.getCreationHelper();
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font1 = workbook.createFont();
        HSSFFont font2 = workbook.createFont();

        DataFormatter df = new DataFormatter();
        cellStyle.setFillForegroundColor(HSSFColorPredefined.LIGHT_TURQUOISE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Map<String, Object> properties = new HashMap<String, Object>();
// border around a cell
        properties.put(CellUtil.BORDER_TOP, BorderStyle.MEDIUM);
        properties.put(CellUtil.BORDER_BOTTOM, BorderStyle.MEDIUM);
        properties.put(CellUtil.BORDER_LEFT, BorderStyle.MEDIUM);
        properties.put(CellUtil.BORDER_RIGHT, BorderStyle.MEDIUM);
// Give it a color (RED)
        properties.put(CellUtil.TOP_BORDER_COLOR, IndexedColors.BLACK1.getIndex());
        properties.put(CellUtil.BOTTOM_BORDER_COLOR, IndexedColors.BLACK1.getIndex());
        properties.put(CellUtil.LEFT_BORDER_COLOR, IndexedColors.BLACK1.getIndex());
        properties.put(CellUtil.RIGHT_BORDER_COLOR, IndexedColors.BLACK1.getIndex());

// Decide which rows to process
        int rowStart = sheet.getFirstRowNum();
        int rowEnd = sheet.getLastRowNum();
        for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
            Row r = sheet.getRow(rowNum);
            String cellVal = df.formatCellValue(r.getCell(columnCount));
            if(cellVal == null || cellVal.isEmpty()) {
                Cell cell1 = r.createCell(columnCount - 1);
                cell1.setCellValue(createHelper.createRichTextString(td_Key));
                // Word Wrap MUST be turned on
                cellStyle.setWrapText(true);
                font1.setColor(HSSFColorPredefined.DARK_RED.getIndex());
                font1.setBold(true);
                cellStyle.setFont(font1);
                cell1.setCellStyle(cellStyle);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                CellUtil.setCellStyleProperties(cell1, properties);
                sheet.autoSizeColumn(columnCount - 1);

                Cell cell2 = r.createCell(columnCount);
                cell2.setCellValue(cellValue);
               /* if(td_Key.contains("session")){
                    cell2.setCellValue(cellValue);
                }else{
                    cell2.setCellValue(Double.parseDouble(cellValue));
                }*/
                font2.setColor(HSSFColorPredefined.BLUE_GREY.getIndex());
                // Word Wrap MUST be turned on
                cellStyle.setWrapText(true);
                cellStyle.setFont(font2);
                cell2.setCellStyle(cellStyle);
                CellUtil.setCellStyleProperties(cell2, properties);
                break;
            } else {
                System.out.println("cellValue is : " + cellVal);
            }
        }

        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();
        inputStream.close();
    }

    public static void writeValueToExcel(String td_Key, Double cellValue) throws IOException {

        String td_Value = "";
        //writeDateAsColumnHeaderToExcel();
        String filePath = Constants.testDatafile_PATH + Constants.testDataFile_Name;

        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);

        workbook = new HSSFWorkbook(inputStream);

        String env = FileReaderManager.fileRead().getConfiguration().getEnv();
        if (env.equalsIgnoreCase("QA")) {
            sheet = workbook.getSheet(Constants.testDatafile_QA);
        } else if (env.equalsIgnoreCase("UAT")) {
            sheet = workbook.getSheet(Constants.testDatafile_UAT);
        } else if (env.equalsIgnoreCase("PROD")) {
            sheet = workbook.getSheet(Constants.testDatafile_PROD);
        }

        int columnCount = getColumnCountBasedOnColumnHeaders();
        System.out.print("COLUMN COUNT" + columnCount +"\n");

        HSSFCreationHelper createHelper = workbook.getCreationHelper();
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font1 = workbook.createFont();
        HSSFFont font2 = workbook.createFont();

        DataFormatter df = new DataFormatter();
        cellStyle.setFillForegroundColor(HSSFColorPredefined.LIGHT_TURQUOISE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Map<String, Object> properties = new HashMap<String, Object>();
// border around a cell
        properties.put(CellUtil.BORDER_TOP, BorderStyle.MEDIUM);
        properties.put(CellUtil.BORDER_BOTTOM, BorderStyle.MEDIUM);
        properties.put(CellUtil.BORDER_LEFT, BorderStyle.MEDIUM);
        properties.put(CellUtil.BORDER_RIGHT, BorderStyle.MEDIUM);
// Give it a color (RED)
        properties.put(CellUtil.TOP_BORDER_COLOR, IndexedColors.BLACK1.getIndex());
        properties.put(CellUtil.BOTTOM_BORDER_COLOR, IndexedColors.BLACK1.getIndex());
        properties.put(CellUtil.LEFT_BORDER_COLOR, IndexedColors.BLACK1.getIndex());
        properties.put(CellUtil.RIGHT_BORDER_COLOR, IndexedColors.BLACK1.getIndex());

// Decide which rows to process
        int rowStart = sheet.getFirstRowNum();
        int rowEnd = sheet.getLastRowNum();
        for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
            Row r = sheet.getRow(rowNum);
            String cellVal = df.formatCellValue(r.getCell(columnCount));
            if(cellVal == null || cellVal.isEmpty()) {
                Cell cell1 = r.createCell(columnCount - 1);
                cell1.setCellValue(createHelper.createRichTextString(td_Key));
                // Word Wrap MUST be turned on
                cellStyle.setWrapText(true);
                font1.setColor(HSSFColorPredefined.DARK_RED.getIndex());
                font1.setBold(true);
                cellStyle.setFont(font1);
                cell1.setCellStyle(cellStyle);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                CellUtil.setCellStyleProperties(cell1, properties);
                sheet.autoSizeColumn(columnCount - 1);

                Cell cell2 = r.createCell(columnCount);
                cell2.setCellValue(cellValue);
                font2.setColor(HSSFColorPredefined.BLUE_GREY.getIndex());
                // Word Wrap MUST be turned on
                cellStyle.setWrapText(true);
                cellStyle.setFont(font2);
                cell2.setCellStyle(cellStyle);
                CellUtil.setCellStyleProperties(cell2, properties);
                break;
            } else {
                System.out.println("cellValue is : " + cellVal);
            }
        }

        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();
        inputStream.close();
    }

    public static void writeDateAsColumnHeaderToExcel() throws IOException {

        String filePath = Constants.testDatafile_PATH + Constants.testDataFile_Name;

        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);

        workbook = new HSSFWorkbook(inputStream);

        String runTimeTestName = fGetTestNameFromRunTimeExcel();
        System.out.println("TESTNAME" + runTimeTestName);
        String env = FileReaderManager.fileRead().getConfiguration().getEnv();

        if (env.equalsIgnoreCase("QA")) {
            sheet = workbook.getSheet(Constants.testDatafile_QA);
        } else if (env.equalsIgnoreCase("UAT")) {
            sheet = workbook.getSheet(Constants.testDatafile_UAT);
        } else if (env.equalsIgnoreCase("PROD")) {
            sheet = workbook.getSheet(Constants.testDatafile_PROD);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String recordTime = "LOAD TIME RECORDED ON " + formatter.format(date);

        HSSFCreationHelper createHelper = workbook.getCreationHelper();
        HSSFCellStyle cellStyle1 = workbook.createCellStyle();
        HSSFFont font3 = workbook.createFont();

        DataFormatter df = new DataFormatter();
        DataFormat fmt = workbook.createDataFormat();
        //    cellStyle.setFillForegroundColor(HSSFColor.LIME.index);
        //  cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Map<String, Object> properties = new HashMap<String, Object>();
// border around a cell
        properties.put(CellUtil.BORDER_TOP, BorderStyle.MEDIUM);
        properties.put(CellUtil.BORDER_BOTTOM, BorderStyle.MEDIUM);
        properties.put(CellUtil.BORDER_LEFT, BorderStyle.MEDIUM);
        properties.put(CellUtil.BORDER_RIGHT, BorderStyle.MEDIUM);
// Give it a color (RED)
        properties.put(CellUtil.TOP_BORDER_COLOR, IndexedColors.BLACK1.getIndex());
        properties.put(CellUtil.BOTTOM_BORDER_COLOR, IndexedColors.BLACK1.getIndex());
        properties.put(CellUtil.LEFT_BORDER_COLOR, IndexedColors.BLACK1.getIndex());
        properties.put(CellUtil.RIGHT_BORDER_COLOR, IndexedColors.BLACK1.getIndex());

        int columnCount = getColumnCountBasedOnColumnHeaders() + 1;

        //  CellStyle cellStyle = workbook.createCellStyle();

        cellStyle1.setDataFormat(
                fmt.getFormat("@"));

        HSSFRow rowNumber = sheet.getRow(0);
        HSSFCell cell1 = rowNumber.createCell(columnCount);
        HSSFCell cell2 = rowNumber.createCell(columnCount + 1);

        cell1.setCellValue(runTimeTestName);
        cell2.setCellValue(recordTime);

        CellUtil.setCellStyleProperties(cell1, properties);
        CellUtil.setCellStyleProperties(cell2, properties);
      /*  font3.setColor(Font.COLOR_NORMAL);
        font3.setBold(true);
        cellStyle1.setFont(font3);*/
   /*     cell1.setCellStyle(cellStyle1);
        cell2.setCellStyle(cellStyle1);*/

        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();

        inputStream.close();
    }


    public void copyCell(Cell oldCell, Cell newCell){
        HashMap<Integer, HSSFCellStyle> styleMap = new HashMap<Integer, HSSFCellStyle>();
        int styleHashCode = oldCell.getCellStyle().hashCode();
        HSSFCellStyle newCellStyle = styleMap.get(styleHashCode);
        if(newCellStyle == null){
            newCellStyle = (HSSFCellStyle) newCell.getSheet().getWorkbook().createCellStyle();
            newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
            styleMap.put(styleHashCode, (HSSFCellStyle) newCellStyle);
        }
        newCell.setCellStyle(newCellStyle);
    }

    private static void copyCell(Cell srcCell, Cell destCell, Map<Integer, HSSFCellStyle> styleMap) {
        if (styleMap != null) {
            int srcCellHashCode = srcCell.getCellStyle().hashCode();
            HSSFCellStyle newCellStyle = styleMap.get(srcCellHashCode);
            if (null == newCellStyle) {
                newCellStyle = (HSSFCellStyle) destCell.getSheet().getWorkbook().createCellStyle();
                newCellStyle.setAlignment(srcCell.getCellStyle().getAlignment());
                newCellStyle.setBorderBottom(srcCell.getCellStyle().getBorderBottom());
                newCellStyle.setBorderLeft(srcCell.getCellStyle().getBorderLeft());
                newCellStyle.setBorderRight(srcCell.getCellStyle().getBorderRight());
                newCellStyle.setBorderTop(srcCell.getCellStyle().getBorderTop());
                newCellStyle.setDataFormat(srcCell.getCellStyle().getDataFormat());
                newCellStyle.setFillBackgroundColor(srcCell.getCellStyle().getFillBackgroundColor());
                newCellStyle.setFillForegroundColor(srcCell.getCellStyle().getFillForegroundColor());
                newCellStyle.setFillPattern(srcCell.getCellStyle().getFillPattern());
                newCellStyle.setVerticalAlignment(srcCell.getCellStyle().getVerticalAlignment());
                newCellStyle.setWrapText(srcCell.getCellStyle().getWrapText());
                styleMap.put(srcCellHashCode, newCellStyle);
            }
            destCell.setCellStyle(newCellStyle);
        }

       /* if (srcCell.getCellType() == CellType.BLANK) {
            destCell.setBlank();
        } else if (srcCell.getCellType() == CellType.STRING) {
            destCell.setCellValue(srcCell.getStringCellValue());
        } else if (srcCell.getCellType() == CellType.NUMERIC) {
            destCell.setCellValue(srcCell.getNumericCellValue());
        } else if (srcCell.getCellType() == CellType.BOOLEAN) {
            destCell.setCellValue(srcCell.getBooleanCellValue());
        } else if (srcCell.getCellType() == CellType.FORMULA) {
            destCell.setCellFormula(srcCell.getCellFormula());
        } else if (srcCell.getCellType() == CellType.ERROR) {
            destCell.setCellErrorValue(srcCell.getErrorCellValue());
        }*/
    }

    /**
     *
     */

    public static void fWriteToExcel(String sheetName, int rowNum, int colNum, String value) throws IOException {
        String td_Value = "";

        String filePath = Constants.testDatafile_PATH + Constants.testDataFile_Name;

        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);

        System.out.println("FILE NAME " + filePath);

        workbook = new HSSFWorkbook(inputStream);
        sheet = workbook.getSheet(sheetName);

        sheet.getRow(rowNum).createCell(colNum).setCellValue(value);

        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();

        inputStream.close();
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public static String fGetDataFromSheet(String sheetName, int rowNum, int colNum) throws IOException {

        String td_Value = "";
        String filePath = Constants.testDatafile_PATH + Constants.testDataFile_Name;

        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);

        workbook = new HSSFWorkbook(inputStream);
        sheet = workbook.getSheet(sheetName);

        cell = sheet.getRow(rowNum).getCell(colNum);
        td_Value = cell.toString();
        //  System.out.println("TESTNAME"+td_Value);
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();
        inputStream.close();

        return td_Value;
    }

}