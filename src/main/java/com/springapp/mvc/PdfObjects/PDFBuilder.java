package com.springapp.mvc.PdfObjects;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import Utilities.AbstractITextPdf;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.springapp.mvc.ReceiptObjects.ReceiptImageObject;
import com.springapp.mvc.ReceiptObjects.ReceiptItem;
import com.springapp.mvc.ReceiptObjects.ReceiptObject;
import Utilities.GreenReceiptUtil;

/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 * @author www.codejava.net
 *
 */
public class PDFBuilder extends AbstractITextPdf {

    @Override
    public void buildPdfDocument(Map<String, Object> model, Document doc,
                                    PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
        List<ReceiptObject> receipts = (List<ReceiptObject>) model.get("receipts");

        Font font = FontFactory.getFont(FontFactory.HELVETICA, 28, Font.BOLD);
        doc.add(new Paragraph("Receipts", font));

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        Double total = 0.0;
        PdfPTable table = null;
        PdfPCell cell = null;
        for (ReceiptObject receipt : receipts) {
            table = new PdfPTable(5);
            table.setWidthPercentage(100.0f);
            table.setWidths(new float[] {3.0f, 2.0f, 1.0f, 2.0f, 2.0f});
            table.setSpacingBefore(10);

            // define font for table header row
            font = FontFactory.getFont(FontFactory.HELVETICA);
            font.setColor(BaseColor.WHITE);

            // define table header cell
            cell = new PdfPCell();
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

            table.addCell(receipt.getStore().getCompany().getName());
            table.addCell(formatter.format(receipt.getSubTotal()));
            table.addCell(formatter.format(receipt.getTotal()));
            table.addCell(receipt.getPurchaseDateString());
            table.addCell(receipt.getReturnDateString());
            doc.add(table);


            // Add receipt Items
            table = new PdfPTable(2);
            table.setWidthPercentage(100.0f);
            table.setWidths(new float[] {5.0f, 5.0f});
            table.setSpacingBefore(10);

            cell = new PdfPCell();
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setPadding(5);

            cell.setPhrase(new Phrase("Item", font));
            table.addCell(cell);

            cell.setPhrase(new Phrase("Amount", font));
            table.addCell(cell);

            for(ReceiptItem item: receipt.getReceiptItems()) {
                table.addCell(item.getItemName());
                table.addCell(item.getPrice());
            }
            doc.add(table);


            table = new PdfPTable(3);
            table.setWidthPercentage(100.0f);
            table.setWidths(new float[] {3.0f, 3.0f, 3.0f});
            table.setSpacingBefore(10);

            // define font for table header row
            font = FontFactory.getFont(FontFactory.HELVETICA);
            font.setColor(BaseColor.WHITE);

            // define table header cell
            cell = new PdfPCell();
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setPadding(5);

            // write table header
            cell.setPhrase(new Phrase("Card Type", font));
            table.addCell(cell);

            cell.setPhrase(new Phrase("Last Four Digits", font));
            table.addCell(cell);

            cell.setPhrase(new Phrase("Barcode", font));
            table.addCell(cell);


            table.addCell(receipt.getCardType());
            table.addCell(receipt.getLastFourCardNumber());
            table.addCell(receipt.getBarcode());
            doc.add(table);

            List<ReceiptImageObject> images = GreenReceiptUtil.getReceiptImages(receipt.getId());
            if(images != null) {
                for(ReceiptImageObject imageObject : images) {

                    byte[] data = DatatypeConverter.parseBase64Binary(imageObject.getBase64Image());
                    try  {
                        OutputStream stream = new FileOutputStream(imageObject.getFileName());
                        stream.write(data);
                        stream.close();
                    } catch (Exception e){

                    }
                    Image img = Image.getInstance(imageObject.getFileName());
                    if (img.getScaledWidth() > 300 || img.getScaledHeight() > 300) {
                        img.scaleToFit(300, 300);
                    }
                    doc.add(img);

                    try {
                        Files.delete(Paths.get(imageObject.getFileName()));
                    } catch (NoSuchFileException x) {
                        // File doesn't exist, something went wrong with the write
                    } catch (IOException x) {
                        // File permission problems are caught here.
                    }
                }
            }

            doc.newPage();
            total += receipt.getTotal();
        }

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