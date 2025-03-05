package com.example.pi_project.Services;

import com.example.pi_project.Entities.*;
import com.example.pi_project.Repositories.MeetingRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class ExportService {

    @Autowired
    private MeetingRepository meetingRepository;

    // Export to Excel
    public ByteArrayOutputStream exportMeetingsToExcel() throws IOException {
        List<Meeting> meetings = meetingRepository.findAll();
        if (meetings.isEmpty()) {
            throw new IllegalStateException("No meetings available for export.");
        }


        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Meetings");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Title");
        headerRow.createCell(1).setCellValue("Date");
        headerRow.createCell(2).setCellValue("Location");
        headerRow.createCell(3).setCellValue("Description");

        // Fill data rows
        int rowNum = 1;
        for (Meeting meeting : meetings) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(meeting.getTitle());
            row.createCell(1).setCellValue(meeting.getDate().toString());
            row.createCell(2).setCellValue(meeting.getLocation());
            row.createCell(3).setCellValue(meeting.getDescription());
        }

        // Auto-size columns
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream;
    }

    // Export to PDF
    public ByteArrayOutputStream exportMeetingsToPdf() throws DocumentException {
        List<Meeting> meetings = meetingRepository.findAll();
        if (meetings.isEmpty()) {
            throw new IllegalStateException("No meetings available for export.");
        }
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // Add a table with 4 columns
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add table headers
        table.addCell("Title");
        table.addCell("Date");
        table.addCell("Location");
        table.addCell("Description");

        // Add meeting data to the table
        for (Meeting meeting : meetings) {
            table.addCell(meeting.getTitle());
            table.addCell(meeting.getDate().toString());
            table.addCell(meeting.getLocation());
            table.addCell(meeting.getDescription());
        }

        document.add(table);
        document.close();

        return outputStream;
    }
}