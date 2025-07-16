
package Util;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import jakarta.servlet.ServletContext;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class PDFGenerator {

    public static byte[] generarPDFReporte(ServletContext context,
                                           String estudiante, String salon, String periodo,
                                           Map<String, Double> promediosPorMateria,
                                           double promedioGeneral, String estado) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont fontNormal = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            // Título
            Paragraph titulo = new Paragraph("Newton College - Reporte Académico")
                    .setFont(fontBold)
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(15);
            document.add(titulo);

            // Tabla de información general
            float[] columnWidths = {150F, 300F};
            Table tableInfo = new Table(columnWidths);

            tableInfo.addCell(new Cell().add(new Paragraph("Estudiante:").setFont(fontBold).setFontColor(ColorConstants.WHITE))
                    .setBackgroundColor(new DeviceRgb(0, 102, 204)));
            tableInfo.addCell(new Cell().add(new Paragraph(estudiante).setFont(fontNormal)));
            tableInfo.addCell(new Cell().add(new Paragraph("Salón:").setFont(fontBold).setFontColor(ColorConstants.WHITE))
                    .setBackgroundColor(new DeviceRgb(0, 102, 204)));
            tableInfo.addCell(new Cell().add(new Paragraph(salon).setFont(fontNormal)));
            tableInfo.addCell(new Cell().add(new Paragraph("Período:").setFont(fontBold).setFontColor(ColorConstants.WHITE))
                    .setBackgroundColor(new DeviceRgb(0, 102, 204)));
            tableInfo.addCell(new Cell().add(new Paragraph(periodo).setFont(fontNormal)));
            document.add(tableInfo.setMarginBottom(15));

            // Subtítulo
            Paragraph subtitulo = new Paragraph("Promedios por Materia")
                    .setFont(fontBold)
                    .setFontSize(14)
                    .setMarginBottom(5);
            document.add(subtitulo);

            // Tabla de materias
            Table tableMaterias = new Table(new float[]{300F, 150F});
            tableMaterias.addHeaderCell(new Cell().add(new Paragraph("Materia").setFont(fontBold).setFontColor(ColorConstants.WHITE))
                    .setBackgroundColor(new DeviceRgb(0, 153, 76))); // Verde oscuro
            tableMaterias.addHeaderCell(new Cell().add(new Paragraph("Promedio").setFont(fontBold).setFontColor(ColorConstants.WHITE))
                    .setBackgroundColor(new DeviceRgb(0, 153, 76)));

            for (Map.Entry<String, Double> entry : promediosPorMateria.entrySet()) {
                tableMaterias.addCell(new Cell().add(new Paragraph(entry.getKey()).setFont(fontNormal)));
                tableMaterias.addCell(new Cell().add(new Paragraph(String.format("%.2f", entry.getValue())).setFont(fontNormal)));
            }
            document.add(tableMaterias.setMarginBottom(15));

            // Promedio general
            document.add(new Paragraph("Promedio General: " + String.format("%.2f", promedioGeneral))
                    .setFont(fontBold)
                    .setFontSize(13)
                    .setMarginBottom(5));

            // Estado académico con color
            DeviceRgb estadoColor = estado.equalsIgnoreCase("Aprobado") ? new DeviceRgb(0, 153, 0) : new DeviceRgb(204, 0, 0);
            Paragraph estadoParagraph = new Paragraph("Estado Académico: " + estado)
                    .setFont(fontBold)
                    .setFontSize(13)
                    .setFontColor(estadoColor);
            document.add(estadoParagraph);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
