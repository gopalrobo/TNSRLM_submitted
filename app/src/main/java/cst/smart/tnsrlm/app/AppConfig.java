package cst.smart.tnsrlm.app;

import android.content.Context;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import cst.smart.tnsrlm.purchase.Purchase;
import cst.smart.tnsrlm.stockMoniter.StockMonitor;
import cst.smart.tnsrlm.stockRegister.StockRegister;

public class AppConfig {

    private static Font catFont = new Font(Font.FontFamily.HELVETICA, 18,
            Font.BOLD);

    public static final String mypreference = "mypref";
    public static final String memberIdKey = "memberIdKey";


    public static String ipcloud = "coconutfpo.smartfpo.com/tnsrlm";
    public static String URL_IMAGE_UPLOAD = "http://" + ipcloud + "/fileUpload.php";
    //student
    public static String URL_CREATE_MEMBER = "http://" + ipcloud + "/create_member.php";
    public static String URL_GET_MEMBER = "http://" + ipcloud + "/get_member.php";

    //Farmer details
    public static String URL_CREATE_DAtA = "http://" + ipcloud + "/create_data.php";
    public static String URL_GET_DATA = "http://" + ipcloud + "/get_all_data.php";


    public static void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }

    public static void addContent(Document document, ArrayList<Purchase> sheets, Context context) throws Exception {

        PdfPTable table1 = new PdfPTable(1);
        table1.setWidthPercentage(100);
        table1.setWidths(new int[]{1});
        for (int i = 0; i < sheets.size(); i++) {
            table1.addCell(createTextCell("Purchase Record " + String.valueOf(i + 1),
                    catFont));
            LinkedHashMap<String, String> tempResult = sheets.get(i).toMap();
            for (String key : tempResult.keySet()) {
                table1.addCell(createTextCell(key,
                        new Font(Font.FontFamily.HELVETICA, 15,
                                Font.BOLD)));
                table1.addCell(createTextCell(tempResult.get(key),
                        new Font(Font.FontFamily.HELVETICA, 14,
                                Font.NORMAL)));
            }

            table1.setKeepTogether(true);
        }
        document.add(table1);

    }
    public static void addContentMoniter(Document document, ArrayList<StockMonitor> sheets, Context context) throws Exception {

        PdfPTable table1 = new PdfPTable(1);
        table1.setWidthPercentage(100);
        table1.setWidths(new int[]{1});
        for (int i = 0; i < sheets.size(); i++) {
            table1.addCell(createTextCell("Purchase Record " + String.valueOf(i + 1),
                    catFont));
            LinkedHashMap<String, String> tempResult = sheets.get(i).toMap();
            for (String key : tempResult.keySet()) {
                table1.addCell(createTextCell(key,
                        new Font(Font.FontFamily.HELVETICA, 15,
                                Font.BOLD)));
                table1.addCell(createTextCell(tempResult.get(key),
                        new Font(Font.FontFamily.HELVETICA, 14,
                                Font.NORMAL)));
            }

            table1.setKeepTogether(true);
        }
        document.add(table1);

    }

    public static void addContentStock(Document document, ArrayList<StockRegister> sheets, Context context) throws Exception {

        PdfPTable table1 = new PdfPTable(1);
        table1.setWidthPercentage(100);
        table1.setWidths(new int[]{1});
        for (int i = 0; i < sheets.size(); i++) {
            table1.addCell(createTextCell("Stock Register " + String.valueOf(i + 1),
                    catFont));
            LinkedHashMap<String, String> tempResult = sheets.get(i).toMap();
            for (String key : tempResult.keySet()) {
                table1.addCell(createTextCell(key,
                        new Font(Font.FontFamily.HELVETICA, 15,
                                Font.BOLD)));
                table1.addCell(createTextCell(tempResult.get(key),
                        new Font(Font.FontFamily.HELVETICA, 14,
                                Font.NORMAL)));
            }

            table1.setKeepTogether(true);
        }
        document.add(table1);

    }

    public static PdfPCell createTextCell(String text, Font font) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text, font);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}
