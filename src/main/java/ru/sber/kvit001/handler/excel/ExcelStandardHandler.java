package ru.sber.kvit001.handler.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.sber.kvit001.model.misc.FileRow;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelStandardHandler {

    private List<String> columnNameListFromSettingsWithNoEmpty;
    private Sheet sheet;

    private List<String> columnNameListFromFile;

    public ExcelStandardHandler(InputStream inputStream, List<String> columnNameListFromSettingsWithNoEmpty) throws IOException {
        this.columnNameListFromSettingsWithNoEmpty = columnNameListFromSettingsWithNoEmpty;
        Workbook workbook = new XSSFWorkbook(inputStream);
        this.sheet = workbook.getSheetAt(0);
    }

    public List<FileRow> handle() throws Exception {
        DataFormatter formatter = new DataFormatter();

        checkStructure(columnNameListFromSettingsWithNoEmpty);

        List<FileRow> fileRowList = new ArrayList<>();
        int rowCounter = 0;
        for (Row row : sheet){
            String firstColValue = formatter.formatCellValue(row.getCell(0)).trim();
            if (firstColValue.isEmpty()) break;

            if (rowCounter == 0) {
                rowCounter++;
                continue;
            }

            FileRow fileRow = new FileRow();
            fileRow.setRowIndex(rowCounter);
            List<String> cellDataList = new ArrayList<>();
            for (Cell cell : row){
                cellDataList.add(formatter.formatCellValue(cell).isEmpty() ? "-" : formatter.formatCellValue(cell));
            }
            fileRow.setRowData(cellDataList);
            fileRowList.add(fileRow);
        }

        return fileRowList;
    }

    private void checkStructure(List<String> columnNameListFromSettingsWithNoEmpty) throws Exception {
        List<String> noColumnList = new ArrayList<>();
        columnNameListFromSettingsWithNoEmpty.forEach(s -> {
            if (!getColumnNameListFromFile().contains(s)) {
                noColumnList.add(s);
            }
        });

        if (noColumnList.size() > 0) {
            throw new Exception("не найдены поля : " + noColumnList.toString());
        }
    }

    public List<String> getColumnNameListFromFile() {
        if (columnNameListFromFile == null) {
            int lastColumnNum = sheet.getRow(0).getLastCellNum();
            columnNameListFromFile = new ArrayList<>();
            for (int i = 0; i < lastColumnNum; i++) {
                columnNameListFromFile.add(sheet.getRow(0).getCell(i).getStringCellValue());
            }
        }
        return columnNameListFromFile;
    }
}
