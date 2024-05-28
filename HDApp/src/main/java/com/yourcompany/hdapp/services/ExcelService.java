package com.yourcompany.hdapp.services;

import com.yourcompany.hdapp.models.Location;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelService {

    public List<Location> importLocationsFromExcel(String filePath) throws IOException {
        List<Location> locations = new ArrayList<>();
        FileInputStream excelFile = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue; // Skip header row
            }

            Location location = new Location();
            location.setId(row.getCell(0).getStringCellValue());
            location.setName(row.getCell(1).getStringCellValue());
            location.setStatus(row.getCell(2).getStringCellValue());

            locations.add(location);
        }

        workbook.close();
        return locations;
    }

    public void exportLocationsToExcel(List<Location> locations, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Locations");

        // Header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Status");

        // Data rows
        int rowNum = 1;
        for (Location location : locations) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(location.getId());
            row.createCell(1).setCellValue(location.getName());
            row.createCell(2).setCellValue(location.getStatus());
        }

        FileOutputStream fileOut = new FileOutputStream(filePath);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }
}
