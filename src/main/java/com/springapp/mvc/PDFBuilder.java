package com.springapp.mvc;


import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Utilities.AbstractITextPdf;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 * @author www.codejava.net
 *
 */
public class PDFBuilder extends AbstractITextPdf {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
                                    PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
        List<Receipt> listBooks = (List<Receipt>) model.get("receipts");

        Font font = FontFactory.getFont(FontFactory.HELVETICA, 28, Font.BOLD);
        doc.add(new Paragraph("Recent Receipts", font));

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {3.0f, 2.0f, 1.0f, 2.0f, 2.0f});
        table.setSpacingBefore(10);

        // define font for table header row
        font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(BaseColor.WHITE);

        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(5);

        // write table header
        cell.setPhrase(new Phrase("Store Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Sub-Total", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Total", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Purchased Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Return Date", font));
        table.addCell(cell);

        // write table row data

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        Double total = 0.0;
        for (Receipt receipt : listBooks) {
            table.addCell(receipt.getStore().getCompany().getName());
            table.addCell(formatter.format(receipt.getSubTotal()));
            table.addCell(formatter.format(receipt.getTotal()));
            table.addCell(receipt.getPurchaseDateString());
            table.addCell(receipt.getReturnDateString());
            total += receipt.getTotal();
        }

        doc.add(table);

        table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {5.0f, 5.0f});
        table.setSpacingBefore(10);

        cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(5);

        cell.setPhrase(new Phrase("Total", font));
        table.addCell(cell);

        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPhrase(new Phrase(formatter.format(total)));
        table.addCell(cell);

        doc.add(table);
    }

}