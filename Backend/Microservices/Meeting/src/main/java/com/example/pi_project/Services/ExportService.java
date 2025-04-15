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
import java.text.SimpleDateFormat;
import java.util.Date;
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


    public ByteArrayOutputStream exportMeetingsToPdf() throws DocumentException, IOException {
        List<Meeting> meetings = meetingRepository.findAll();
        if (meetings.isEmpty()) {
            throw new IllegalStateException("No meetings available for export.");
        }

        Document document = null;
        ByteArrayOutputStream outputStream = null;

        try {
            document = new Document(PageSize.A4.rotate());
            outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);

            document.open();

            // Ensure there's at least some content
            document.add(new Paragraph("Meeting Export"));
            document.add(new Paragraph(" ")); // Blank line

            PdfPTable table = new PdfPTable(11);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            float[] columnWidths = {2f, 2f, 1f, 2f, 2f, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f, 3f};
            table.setWidths(columnWidths);

            // Headers
            addHeaderCell(table, "Title");
            addHeaderCell(table, "Date");
            addHeaderCell(table, "Duration");
            addHeaderCell(table, "Location");
            addHeaderCell(table, "Street");
            addHeaderCell(table, "City");
            addHeaderCell(table, "Country");
            addHeaderCell(table, "Postal Code");
            addHeaderCell(table, "Frequency");
            addHeaderCell(table, "Type");
            addHeaderCell(table, "Description");

            // Data rows
            for (Meeting meeting : meetings) {
                addDataCell(table, safeString(meeting.getTitle()));
                Object dateObj = meeting.getDate();
                if (dateObj instanceof Date) {
                    String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format((Date) dateObj);
                    addDataCell(table, formattedDate);
                } else {
                    addDataCell(table, ""); // Or show raw value: safeString(String.valueOf(dateObj))
                }

                addDataCell(table, meeting.getDuration() != null ?
                        String.valueOf(meeting.getDuration()) : "");
                addDataCell(table, safeString(meeting.getLocation()));

                Meeting.Address address = meeting.getAddress();
                addDataCell(table, address != null ? safeString(address.getStreet()) : "");
                addDataCell(table, address != null ? safeString(address.getCity()) : "");
                addDataCell(table, address != null ? safeString(address.getCountry()) : "");
                addDataCell(table, address != null ? safeString(address.getPostalCode()) : "");

                addDataCell(table, meeting.getFrequency() != null ?
                        safeString(meeting.getFrequency().getDisplayName()) : "");
                addDataCell(table, meeting.getType() != null ?
                        safeString(meeting.getType().getDisplayName()) : "");
                addDataCell(table, safeString(meeting.getDescription()));
            }

            document.add(table);

            return outputStream;
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
    }

    private void addHeaderCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setBackgroundColor(new BaseColor(220, 220, 220));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    private void addDataCell(PdfPTable table, String text) {
        table.addCell(new Phrase(text != null ? text : ""));
    }

    private String safeString(String input) {
        return input != null ? input : "";
    }
}