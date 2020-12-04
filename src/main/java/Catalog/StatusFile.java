package Catalog;

import Model.MetaData;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class StatusFile {
    private Cell cell;
    private Row row;
    private int rowNum = 0;

    private static HSSFCellStyle createStyle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        // font symbol
        //font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Calibri");

        BorderStyle thin  = BorderStyle.THIN;

        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        // aligh cell
        style.setBorderTop(thin);
        style.setBorderLeft(thin);
        style.setBorderRight(thin);
        style.setBorderBottom(thin);

        // Color aligh
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());

        style.setFont(font);
        return style;
    }

    public void writeStatus(ArrayList<MetaData> list, int name) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Регистер Статуса");

            HSSFCellStyle style = createStyle(workbook);

            row = sheet.createRow(rowNum);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Номер");
            cell.setCellStyle(style);

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Состояние ЭП ТПА");
            cell.setCellStyle(style);

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Калибровка конечных положений");
            cell.setCellStyle(style);

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Источник команд");
            cell.setCellStyle(style);

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Последняя команда");
            cell.setCellStyle(style);

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Причина остановки ЭП");
            cell.setCellStyle(style);

            HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

            for (MetaData mList : list) {
                rowNum++;
                row = sheet.createRow(rowNum);
                style.setAlignment(HorizontalAlignment.CENTER);

                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(rowNum);
                cell.setCellStyle(style);

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(mList.getEd());
                cell.setCellStyle(style);

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(mList.getCalibration());
                cell.setCellStyle(style);

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(mList.getSourceCommand());
                cell.setCellStyle(style);

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(mList.getLastCommand());
                cell.setCellStyle(style);

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(mList.getStopping());
                cell.setCellStyle(style);

                HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

                for (int i = 0; i < 6; i++) {
                    sheet.autoSizeColumn(i);
                }
            }

            try {
                String device = "./Контроллеры/МПУ " + name + ".xls";
                File file = new File(device);
                File directory = file.getParentFile();
                if (null != directory) {
                    directory.mkdir();
                }
                FileOutputStream outFile = new FileOutputStream(file);
                workbook.write(outFile);
            } catch (IOException e) {
                System.out.print("Ошибка записи файла" + "\n");
                e.printStackTrace();
            }

            System.out.println("Файл МПУ Записан " + name + "\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
