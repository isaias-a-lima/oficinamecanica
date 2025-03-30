package com.ikservices.oficinamecanica.commons.pdf;

import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static com.lowagie.text.Utilities.toByteArray;

public abstract class PDFTemplateBuilder {
    private final String FOLDER_SOURCE = "/assets/images/";

    protected final static String PDF_GENERATION_ERROR = "Erro ao gerar PDF.";
    protected final static String DOCUMENT_CLOSE_ERROR = "Document close error.";

    protected final String RIGHT = "right";
    protected final String LEFT = "left";
    private final String pdfName;
    public String formattedPdfName;
    private Document document;
    private ByteArrayOutputStream outputStream;
    protected byte[] file;

    protected PDFTemplateBuilder(String pdfName) {
        this.pdfName = pdfName;
    }

    /**
     * Open the file to edit.<br>
     * Remember to always close the file when you are finished.<br>
     * Use closeDocument method to close the file.
     *
     * @throws DocumentException
     */
    protected void openDocument(String param) throws DocumentException {
        this.document = new Document(PageSize.A4, 20, 20, 20, 20);
        this.outputStream = new ByteArrayOutputStream();
        this.formattedPdfName = String.format(this.pdfName, param);

        PdfWriter.getInstance(this.document, this.outputStream);

        this.document.open();
    }

    /**
     * Close the file when finished.
     */
    protected void closeDocument() throws IOException {
        if (this.document != null && this.document.isOpen()) {
            this.document.close();
        }

        // Converter o conteúdo do OutputStream para um array de bytes
        this.file = this.outputStream.toByteArray();

        // Fechar o stream de saída
        this.outputStream.close();
    }

    public String getFormattedPdfName() {
        return  this.formattedPdfName;
    }

    protected void addChunck(String text, int family, float size, int style, Color color) {
        Chunk chunk = new Chunk(text, new Font(family, size, style, color));
        this.document.add(chunk);
    }

    /**
     * Font Helvetica 12 Normal<br>
     * Color RGB 0 0 0
     * @param text
     */
    protected void addParagraph(String text) {
        Paragraph p = this.addParagraph(text, Font.HELVETICA, 12, Font.NORMAL, Color.black);
        document.add(p);
    }

    /**
     * Font Helvetica 24 BOLD<br>
     * Color RGB 248 77 35
     * @param title
     */
    protected void addTitleH1(String title) {
        Paragraph p = this.addParagraph(title, Font.HELVETICA, 24, Font.BOLD, new Color(248, 77, 35));
        document.add(p);
    }

    /**
     * Font Helvetica 20 BOLD<br>
     * Color RGB 248 77 35
     * @param title
     */
    protected void addTitleH2(String title) {
        Paragraph p = this.addParagraph(title, Font.HELVETICA, 20, Font.BOLD, new Color(248, 77, 35));
        document.add(p);
    }

    /**
     * Font Helvetica 16 BOLD<br>
     * Color RGB 248 77 35
     * @param title
     */
    protected void addTitleH3(String title, String alignment) {
        Paragraph p = this.addParagraph(title, Font.HELVETICA, 16, Font.BOLD, new Color(248, 77, 35));
        p.setAlignment(alignment);
        document.add(p);
    }

    /**
     * Font Helvetica 12 BOLD<br>
     * Color RGB 248 77 35
     * @param title
     */
    protected void addTitleH4(String title) {
        Paragraph p = this.addParagraph(title, Font.HELVETICA, 12, Font.BOLD, new Color(248, 77, 35));
        p.setSpacingBefore(5);
        p.setSpacingAfter(0);
        document.add(p);
    }

    /**
     * Adds an image to the top of the page
     * @param imagePath
     * @throws BadElementException
     * @throws IOException
     */
    protected void addHeaderImage(String imagePath) throws BadElementException, IOException {
        Image image = getImage(imagePath);
        // Calcula a largura da página (considerando as margens)
        float pageWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
        // Define a largura da imagem
        image.scaleToFit(pageWidth, document.getPageSize().getHeight());
        document.add(image);
    }

    /**
     * Adds a table with header, body and footer to the document<br>
     * String[][] contents e.g.:  {{"content1", "style1", content2, style2}, {"content1", "style1", content2, style2}}
     * @param headerColumns
     * @param contents
     * @param columnWidths
     * @param footerColumns
     */
    protected void addFullTable(String[][] headerColumns, float[] columnWidths, String[][] footerColumns, String[][] contents) {
        PdfPTable table = createTableStructure(headerColumns.length, 100, columnWidths);
        createTableHeaderOrFooterStructure(table, headerColumns);
        createTableBodyStructure(table, contents);
        createTableHeaderOrFooterStructure(table, footerColumns);
        document.add(table);

    }

    /**
     * Adds a table with header and body to the document.<br>
     * String[][] contents e.g.:  {{"content1", "style1", content2, style2}, {"content1", "style1", content2, style2}}
     * @param columns
     * @param columnWidths
     * @param contents
     */
    protected void addTableWithHeader(String[][] columns, float[] columnWidths, String[][] contents) {
        PdfPTable table = createTableStructure(columns.length, 100, columnWidths);
        createTableHeaderOrFooterStructure(table, columns);
        createTableBodyStructure(table, contents);
        document.add(table);
    }

    /**
     * Adds a table with a body, but no header or footer.<br>
     * String[][] contents e.g.:  {{"content1", "style1", content2, style2}, {"content1", "style1", content2, style2}}
     * @param columnsLength
     * @param tableWidth
     * @param columnWidths
     * @param contents
     */
    protected void addTableWithNoHeaderOrFooter(int columnsLength, Integer tableWidth, float[] columnWidths, String[][] contents) {
        PdfPTable table = createTableStructure(columnsLength, tableWidth, columnWidths);
        createTableBodyStructure(table, contents);
        document.add(table);
    }

    /**
     * Adds a table with a single row footer style
     * @param columnsLength
     * @param tableWidth
     * @param columnWidths
     * @param columns
     */
    protected void addTableFooter(int columnsLength, Integer tableWidth, float[] columnWidths, String[][] columns) {
        PdfPTable table = createTableStructure(columnsLength, tableWidth, columnWidths);
        createTableHeaderOrFooterStructure(table, columns);
        document.add(table);
    }

    private PdfPTable createTableStructure(int columnsLength, Integer tableWidth, float[] columnWidths) {
        PdfPTable table = new PdfPTable(columnsLength);
        table.setSpacingBefore(5);
        table.setSpacingAfter(10);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        if (Objects.nonNull(tableWidth)) {
            table.setWidthPercentage(tableWidth);
        }
        if (Objects.nonNull(columnWidths) && columnWidths.length > 0) {
            table.setWidths(columnWidths);
        }
        return table;
    }

    private void createTableHeaderOrFooterStructure(PdfPTable table, String[][] columns) {
        for (String[] column : columns) {
            table.addCell(this.addHeaderOrFooterCell(column[0], column[1]));
        }
    }

    private void createTableBodyStructure(PdfPTable table, String[][] contents) {
        for (int x=0; x < contents.length; x++) {
            for (int y=0; y < contents[x].length; y++) {
                if (y % 2 == 0) {
                    table.addCell(this.AddBodyCell(contents[x][y], contents[x][y+1]));
                }
            }
        }
    }

    private PdfPCell addHeaderOrFooterCell(String text, String alignment) {
        Paragraph paragraph = this.addParagraph(text, Font.HELVETICA, 12, Font.BOLD, Color.black);
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment.equals(RIGHT) ? Element.ALIGN_RIGHT : Element.ALIGN_LEFT);
        cell.setBorderColor(Color.gray);
        cell.setBackgroundColor(new Color(210, 210, 210));
        return cell;
    }

    private PdfPCell AddBodyCell(String text, String style) {
        PdfPCell cell = new PdfPCell(this.addParagraph(text, Font.HELVETICA, 12, getFontStyle(style), Color.black));
        cell.setPadding(5);
        cell.setBorderWidthTop(0);
        cell.setBorderWidthLeft(0);
        cell.setBorderWidthRight(0);
        cell.setBorderColorBottom(new Color(248, 77, 35));

        return cell;
    }

    private Paragraph addParagraph(String text, int family, float size, int style, Color color) {
        return new Paragraph(text, new Font(family, size, style, color));
    }

    private int getFontStyle(String style) {
        int test = Integer.parseInt(style);
        switch (test) {
            case 1:
                return Font.BOLD;
            case 2:
                return Font.ITALIC;
            case 4:
                return Font.UNDERLINE;
            default:
                return Font.NORMAL;

        }
    }

    private Image getImage(String imagePath) throws IOException {
        InputStream imageStream = getClass().getResourceAsStream(FOLDER_SOURCE + imagePath);
        if (imageStream == null) {
            throw new FileNotFoundException("Imagem não encontrada no classpath: " + FOLDER_SOURCE + imagePath);
        }

        byte[] byteArray = toByteArray(imageStream);

        // Criar a imagem a partir do InputStream
        return Image.getInstance(byteArray);
    }
}
