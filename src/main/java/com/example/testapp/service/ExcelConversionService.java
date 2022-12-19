package com.example.testapp.service;

import com.example.testapp.model.HumanData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import static com.example.testapp.config.Constants.TEMP_FILE;

@Service
public class ExcelConversionService {
    public File convert(Collection<HumanData> dataList) {
        if (!dataList.isEmpty()) {
            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                Sheet sheet = createSheet(workbook);
                populateSheet(workbook, sheet, dataList);

                File currDir = new File(TEMP_FILE);
                FileOutputStream outputStream = new FileOutputStream(currDir);
                workbook.write(outputStream);
                return currDir;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private void populateSheet(XSSFWorkbook workbook, Sheet sheet, Collection<HumanData> dataList) {
        createHeaderRow(sheet, workbook);
        CellStyle style = getMainStyle(workbook);
        int pos = 0;
        for (HumanData d: dataList) {
            pos+=1;
            convertToRow(sheet, style, d, pos);
        }
    }

    private CellStyle getMainStyle(XSSFWorkbook workbook) {
        CellStyle mainStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 8);
        font.setBold(false);
        mainStyle.setFont(font);
        return mainStyle;
    }

    private void convertToRow(Sheet sheet, CellStyle style, HumanData data, int pos) {
        Row row = sheet.createRow(pos);
        List<String> dataList = data.getData();
        IntStream.range(0, 14).forEach(i -> cell(row, style, i, dataList.get(i)));
    }

    private void cell(Row row, CellStyle style, int pos, String val) {
        Cell cell = row.createCell(pos);
        cell.setCellValue(val);
        cell.setCellStyle(style);
    }

    private Sheet createSheet(XSSFWorkbook workbook) {
        Sheet sheet = workbook.createSheet("Persons");
        List<Integer> widths = List.of(1775, 2470, 2170, 3580, 3580, 3750, 5650, 6090, 4500, 3310, 5210, 2920, 5480, 3310);
        for (int i = 0; i < widths.size(); i++) {
            sheet.setColumnWidth(i, widths.get(i));
        }

        return sheet;
    }

    private void createHeaderRow(Sheet sheet, XSSFWorkbook workbook) {
        Row header = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 10);
        font.setBold(true);
        headerStyle.setFont(font);
        List<String> headers = List.of("ід", "остання видача", "лічильник", "імʼя", "прізвище", "розмір родини", "імена членів родини", "вік членів родини", "контактний тел", "регіон", "адреса", "статус", "адреса до 24.02", "частина регіону");

        for (int i = 0; i < headers.size(); i++) {
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(headers.get(i));
            headerCell.setCellStyle(headerStyle);
        }
    }
}
