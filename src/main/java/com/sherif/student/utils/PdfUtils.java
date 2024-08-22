package com.sherif.student.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

public class PdfUtils {

    public static final String THERE_ARE_NO_COURSE_REGISTRATIONS_TO_REPORT = "There are no course registrations to report";

    public static ByteArrayOutputStream generatePdfStream(List<Map<String, String>> queryResults) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        // Write column names
        if (queryResults != null && !queryResults.isEmpty()) {
            Map<String, String> firstRow = queryResults.get(0);
            for (String column : firstRow.keySet()) {
                Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                Paragraph paragraph = new Paragraph(column, boldFont);
                document.add(paragraph);
            }
            document.add(new Paragraph("\n"));
            // Write data rows
            for (Map<String, String> row : queryResults) {
                for (Object value : row.values()) {
                    Paragraph paragraph = new Paragraph(value.toString());
                    document.add(paragraph);
                }
                document.add(new Paragraph("\n"));
            }
            document.close();
            return outputStream;
        } else {
            throw new IllegalStateException(THERE_ARE_NO_COURSE_REGISTRATIONS_TO_REPORT);
        }
    }
}
