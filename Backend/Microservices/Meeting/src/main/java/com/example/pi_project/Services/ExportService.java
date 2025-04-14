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

        // Create header row with all relevant fields
        Row headerRow = sheet.createRow(0);
        String[] headers = {
                "Title", "Date", "Duration (min)", "Location",
                "Street", "City", "Country", "Postal Code",
                "Frequency", "Type", "Description"
        };

        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        // Fill data rows
        int rowNum = 1;
        for (Meeting meeting : meetings) {
            Row row = sheet.createRow(rowNum++);

            // Basic meeting info
            row.createCell(0).setCellValue(meeting.getTitle());
            row.createCell(1).setCellValue(meeting.getDate().toString());
            row.createCell(2).setCellValue(meeting.getDuration());
            row.createCell(3).setCellValue(meeting.getLocation());

            // Address info
            Meeting.Address address = meeting.getAddress();
            row.createCell(4).setCellValue(address != null ? address.getStreet() : "");
            row.createCell(5).setCellValue(address != null ? address.getCity() : "");
            row.createCell(6).setCellValue(address != null ? address.getCountry() : "");
            row.createCell(7).setCellValue(address != null ? address.getPostalCode() : "");

            // Meeting details
            row.createCell(8).setCellValue(meeting.getFrequency() != null ? meeting.getFrequency().getDisplayName() : "");
            row.createCell(9).setCellValue(meeting.getType() != null ? meeting.getType().getDisplayName() : "");
            row.createCell(10).setCellValue(meeting.getDescription() != null ? meeting.getDescription() : "");
        }

        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream;
    }

    // Export to PDF
    public ByteArrayOutputStream exportMeetingsToPdf() throws DocumentException, IOException {
        List<Meeting> meetings = meetingRepository.findAll();
        if (meetings.isEmpty()) {
            throw new IllegalStateException("No meetings available for export.");
        }

        Document document = new Document(PageSize.A4.rotate()); // Use landscape for more columns
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // Create a table with all columns
        PdfPTable table = new PdfPTable(11); // Number of columns
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Set column widths (adjust as needed)
        float[] columnWidths = {2f, 2f, 1f, 2f, 2f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 3f};
        table.setWidths(columnWidths);

        // Add table headers
        table.addCell("Title");
        table.addCell("Date");
        table.addCell("Duration");
        table.addCell("Location");
        table.addCell("Street");
        table.addCell("City");
        table.addCell("Country");
        table.addCell("Postal Code");
        table.addCell("Frequency");
        table.addCell("Type");
        table.addCell("Description");

        // Add meeting data to the table
        for (Meeting meeting : meetings) {
            // Basic meeting info
            table.addCell(meeting.getTitle());
            table.addCell(meeting.getDate().toString());
            table.addCell(String.valueOf(meeting.getDuration()));
            table.addCell(meeting.getLocation());

            // Address info
            Meeting.Address address = meeting.getAddress();
            table.addCell(address != null ? address.getStreet() : "");
            table.addCell(address != null ? address.getCity() : "");
            table.addCell(address != null ? address.getCountry() : "");
            table.addCell(address != null ? address.getPostalCode() : "");

            // Meeting details
            table.addCell(meeting.getFrequency() != null ? meeting.getFrequency().getDisplayName() : "");
            table.addCell(meeting.getType() != null ? meeting.getType().getDisplayName() : "");
            table.addCell(meeting.getDescription() != null ? meeting.getDescription() : "");
        }

        document.add(table);
        document.close();

        return outputStream;
    }
}