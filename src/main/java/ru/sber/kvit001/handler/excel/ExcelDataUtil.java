package ru.sber.kvit001.handler.excel;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.sber.kvit001.model.misc.CommonReqs;
import ru.sber.kvit001.model.misc.FileRow;
import ru.sber.kvit001.model.misc.MainColEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelDataUtil {
    public static List<CommonReqs> getCommonReqsList(MainColEntity mainColumns, List<FileRow> fileRowList, List<String> columnNameListFromFile) throws Exception {
        List<CommonReqs> commonReqsList = new ArrayList<>();

        for (FileRow row : fileRowList){
            String contract = "";
            if (!mainColumns.getContract().isEmpty()) {
                contract = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getContract()));
            }

            String fio = "";
            if (!mainColumns.getFio().isEmpty()) {
                fio = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getFio()));
            }
            String adr = "";
            if (!mainColumns.getAdr().isEmpty()) {
                adr = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getAdr()));
            }

            String purpose = "";
            if (!mainColumns.getPurpose().isEmpty()) {
                purpose = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getPurpose()));
            }

            double sum = 0d;
            if (!mainColumns.getSum().equals("")) {
                sum = toDouble(row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getSum())));
            }

            String kbk = "";
            if (!mainColumns.getKbk().equals("")) {
                kbk = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getKbk()));
            }

            String oktmo = "";
            if (!mainColumns.getOktmo().equals("")) {
                oktmo = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getOktmo()));
            }

            commonReqsList.add(new CommonReqs(contract, fio, adr, purpose, sum, kbk, oktmo));
        }

        return commonReqsList;
    }

    public static double toDouble(String str) throws Exception {
        String newStr = str
                .replace(",", ".")
                .replace(" ", "")
                .replace(Character.toString((char) 160), "");

        double value;
        try{
            value = Double.parseDouble(deleteDoubleStrDelimiters(newStr));
        }catch (Exception e){
            throw new Exception("Невозможно привести к числу значение " + str);
        }

        return value;
    }

    public static String deleteDoubleStrDelimiters(String str){
        int count = str.length() - str.replace(".", "").length();
        if (count > 1){
            String newStr = str.replaceFirst(".", "");
            return deleteDoubleStrDelimiters(newStr);
        } else {
            return str;
        }
    }


    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    public static List<CommonReqs> getCommonReqsList(InputStream inputStream) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        XSSFSheet reqSheet = workbook.getSheet("Данные ПАЦИЕНТА");
        String contract = reqSheet.getRow(0).getCell(2).getStringCellValue();
        String fio = reqSheet.getRow(5).getCell(2).getStringCellValue();
        String adr = reqSheet.getRow(10).getCell(2).getStringCellValue();

        List<Sheet> billSheets = new ArrayList<>();
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()){
            Sheet next = sheetIterator.next();
            String sheetName = next.getSheetName().toUpperCase();
            if (sheetName.contains("ПЕРЕЧЕНЬ УСЛУГ")) billSheets.add(next);
        }

        DataFormatter formatter = new DataFormatter();
        XSSFFormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        List<CommonReqs> commonReqsList = new ArrayList<>();
        for (Sheet billSheet : billSheets ){
            String sumStr = formatter.formatCellValue(billSheet.getRow(17).getCell(7), evaluator);
            Double sum = ExcelDataUtil.toDouble(sumStr);

            String s = sumStr
                    .replace(",", ".")
                    .replace(" ", "")
                    .replace(Character.toString((char) 160), "");

            String ss = deleteDoubleStrDelimiters(s);

            if (sum > 0){
                commonReqsList.add(new CommonReqs(contract, fio, adr, sumStr, sum, s, ss));
            }
        }

        return commonReqsList;
    }
}
