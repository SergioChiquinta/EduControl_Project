
package Util;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class PDFGenerator {

    public static byte[] generarPDFReporte(String estudiante, String salon, String periodo,
                                           Map<String, Double> promediosPorMateria,
                                           double promedioGeneral, String estado) {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Fuentes
            PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont fontNormal = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            // Cabecera
            document.add(new Paragraph("Reporte Académico").setFont(fontBold).setFontSize(18));
            document.add(new Paragraph("Estudiante: " + estudiante).setFont(fontNormal));
            document.add(new Paragraph("Salón: " + salon).setFont(fontNormal));
            document.add(new Paragraph("Período: " + periodo).setFont(fontNormal));

            // Promedios por materia
            document.add(new Paragraph("\nPromedios por Materia:").setFont(fontBold));
            for (Map.Entry<String, Double> entry : promediosPorMateria.entrySet()) {
                String materia = entry.getKey();
                Double promedio = entry.getValue();
                document.add(new Paragraph(" - " + materia + ": " + String.format("%.2f", promedio)).setFont(fontNormal));
            }

            // Promedio general y estado
            document.add(new Paragraph("\nPromedio General: " + String.format("%.2f", promedioGeneral)).setFont(fontNormal));
            document.add(new Paragraph("Estado Académico: " + estado).setFont(fontNormal));

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
