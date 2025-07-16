
package Util;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import jakarta.servlet.ServletContext;

import java.io.ByteArrayOutputStream;
import java.io.File;
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

            // Logo
            //String logoPath = context.getRealPath("/img/logo_newton_college.png");
            //System.out.println("Ruta del logo: " + logoPath);

            //if (logoPath != null && new File(logoPath).exists()) {
            //    Image logo = new Image(ImageDataFactory.create(logoPath))
            //        .scaleToFit(80, 80)
            //        .setHorizontalAlignment(HorizontalAlignment.CENTER);
            //    document.add(logo);
            //} else {
            //    System.out.println("⚠️ Archivo de logo no encontrado o ruta incorrecta");
            //}

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
            tableInfo.addCell(new Cell().add(new Paragraph("Estudiante:").setFont(fontBold)));
            tableInfo.addCell(new Cell().add(new Paragraph(estudiante).setFont(fontNormal)));
            tableInfo.addCell(new Cell().add(new Paragraph("Salón:").setFont(fontBold)));
            tableInfo.addCell(new Cell().add(new Paragraph(salon).setFont(fontNormal)));
            tableInfo.addCell(new Cell().add(new Paragraph("Período:").setFont(fontBold)));
            tableInfo.addCell(new Cell().add(new Paragraph(periodo).setFont(fontNormal)));
            document.add(tableInfo.setMarginBottom(15));

            // Tabla de promedios por materia
            Paragraph subtitulo = new Paragraph("Promedios por Materia")
                    .setFont(fontBold)
                    .setFontSize(14)
                    .setMarginBottom(5);
            document.add(subtitulo);

            Table tableMaterias = new Table(new float[]{300F, 150F});
            tableMaterias.addHeaderCell(new Cell().add(new Paragraph("Materia").setFont(fontBold)));
            tableMaterias.addHeaderCell(new Cell().add(new Paragraph("Promedio").setFont(fontBold)));

            for (Map.Entry<String, Double> entry : promediosPorMateria.entrySet()) {
                tableMaterias.addCell(new Cell().add(new Paragraph(entry.getKey()).setFont(fontNormal)));
                tableMaterias.addCell(new Cell().add(new Paragraph(String.format("%.2f", entry.getValue())).setFont(fontNormal)));
            }
            document.add(tableMaterias.setMarginBottom(15));

            // Promedio general y estado
            document.add(new Paragraph("Promedio General: " + String.format("%.2f", promedioGeneral))
                    .setFont(fontBold)
                    .setFontSize(13)
                    .setMarginBottom(5));

            document.add(new Paragraph("Estado Académico: " + estado)
                    .setFont(fontBold)
                    .setFontSize(13));

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
