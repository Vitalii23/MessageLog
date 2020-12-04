package Catalog;

import Model.DataFile;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class XlsFile {

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

    public void writeXls(ArrayList<DataFile> list, int name) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Контроллеры");

            HSSFCellStyle style = createStyle(workbook);

            row = sheet.createRow(rowNum);

            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Номер");
            cell.setCellStyle(style);

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Код Регистра");
            cell.setCellStyle(style);

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Время");
            cell.setCellStyle(style);

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Статус");
            cell.setCellStyle(style);

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Фаза R");
            cell.setCellStyle(style);

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Фаза S");
            cell.setCellStyle(style);

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Фаза T");
            cell.setCellStyle(style);

            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue("Фаза U");
            cell.setCellStyle(style);

            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue("Фаза V");
            cell.setCellStyle(style);

            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue("Фаза W");
            cell.setCellStyle(style);

            cell = row.createCell(10, CellType.STRING);
            cell.setCellValue("Момент");
            cell.setCellStyle(style);

            cell = row.createCell(11, CellType.STRING);
            cell.setCellValue("Положение");
            cell.setCellStyle(style);

            cell = row.createCell(12, CellType.STRING);
            cell.setCellValue("Счетчик циклов");
            cell.setCellStyle(style);

            HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

            for (DataFile cList : list){
                rowNum++;
                row = sheet.createRow(rowNum);
                style.setAlignment(HorizontalAlignment.CENTER);

                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(cList.getNumber());
                cell.setCellStyle(style);

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(cList.getCode());
                cell.setCellStyle(style);

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(cList.getPeriod());
                cell.setCellStyle(style);

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(cList.getStatus());
                cell.setCellStyle(style);

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(cList.getR());
                cell.setCellStyle(style);

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(cList.getS());
                cell.setCellStyle(style);

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(cList.getT());
                cell.setCellStyle(style);

                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(cList.getU());
                cell.setCellStyle(style);

                cell = row.createCell(8, CellType.STRING);
                cell.setCellValue(cList.getV());
                cell.setCellStyle(style);

                cell = row.createCell(9, CellType.STRING);
                cell.setCellValue(cList.getW());
                cell.setCellStyle(style);

                cell = row.createCell(10, CellType.STRING);
                cell.setCellValue(cList.getMoment());
                cell.setCellStyle(style);

                cell = row.createCell(11, CellType.STRING);
                cell.setCellValue(cList.getPosition());
                cell.setCellStyle(style);

                cell = row.createCell(12, CellType.STRING);
                cell.setCellValue(cList.getCycleCount());
                cell.setCellStyle(style);

                HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

                for(int i = 0; i < 13; i++) {
                    sheet.autoSizeColumn(i);
                }
            }

            try {
                String device = "./Контроллеры/Контроллер " + name + ".xls";
                File file = new File(device);
                File directory = file.getParentFile();
                if (null != directory){
                    directory.mkdir();
                }
                FileOutputStream outFile = new FileOutputStream(file);
                workbook.write(outFile);
            } catch (IOException e) {
                System.out.print("Ошибка записи файла" + "\n");
                e.printStackTrace();
            }

            System.out.println("Файл Записан " + "\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

