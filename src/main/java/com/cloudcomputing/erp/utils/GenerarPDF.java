package com.cloudcomputing.erp.utils;

import com.cloudcomputing.erp.dto.EmpleadoDTO;
import com.cloudcomputing.erp.dto.NominaDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Alejandro
 */
public class GenerarPDF {
    public static String generarPdfNomina(NominaDTO nominaDTO, EmpleadoDTO empleadoDTO, String nombreArchivo) {
        try {
            Path ruta = Paths.get("C:/Users/Alejandro/pdfs", nombreArchivo);

            // Crear la carpeta si no existe
            if (!Files.exists(ruta.getParent())) {
                Files.createDirectories(ruta.getParent());
            }

            // Crear el documento PDF
            Document pdfDocument = new Document();
            PdfWriter.getInstance(pdfDocument, Files.newOutputStream(ruta));
            pdfDocument.open();

            // Añadir el logo de la empresa
            try {
                Image logo = Image.getInstance("C:/Users/Alejandro/logo.png");  // Cambia la ruta al logo de tu empresa
                logo.scaleToFit(150, 150);  // Ajustar el tamaño del logo
                logo.setAlignment(Element.ALIGN_CENTER);
                logo.setSpacingAfter(20);
                pdfDocument.add(logo);
            } catch (IOException e) {
                System.err.println("Error al cargar el logo de la empresa: " + e.getMessage());
            }

            // Encabezado del PDF
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLACK);
            Paragraph title = new Paragraph("Nómina de Empleado", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            pdfDocument.add(title);

            // Información del empleado
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLUE);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

            Paragraph empleadoInfo = new Paragraph("Información del Empleado", headerFont);
            empleadoInfo.setSpacingAfter(10);
            pdfDocument.add(empleadoInfo);

            PdfPTable employeeTable = new PdfPTable(2); // Tabla con 2 columnas
            employeeTable.setWidthPercentage(100);
            employeeTable.setSpacingBefore(10f);
            employeeTable.setSpacingAfter(10f);

            // Celdas de la tabla (Etiqueta y Valor)
            PdfPCell cell;

            cell = new PdfPCell(new Phrase("ID del Empleado:", headerFont));
            cell.setBorder(Rectangle.NO_BORDER);
            employeeTable.addCell(cell);

            cell = new PdfPCell(new Phrase(nominaDTO.getIdEmpleado(), normalFont));
            cell.setBorder(Rectangle.NO_BORDER);
            employeeTable.addCell(cell);

            cell = new PdfPCell(new Phrase("Nombre(s):", headerFont));
            cell.setBorder(Rectangle.NO_BORDER);
            employeeTable.addCell(cell);

            cell = new PdfPCell(new Phrase(empleadoDTO.getNombre(), normalFont));
            cell.setBorder(Rectangle.NO_BORDER);
            employeeTable.addCell(cell);

            cell = new PdfPCell(new Phrase("Apellidos:", headerFont));
            cell.setBorder(Rectangle.NO_BORDER);
            employeeTable.addCell(cell);

            cell = new PdfPCell(new Phrase(empleadoDTO.getApellidoPaterno() + " " + empleadoDTO.getApellidoMaterno(), normalFont));
            cell.setBorder(Rectangle.NO_BORDER);
            employeeTable.addCell(cell);

            cell = new PdfPCell(new Phrase("Fecha de Pago:", headerFont));
            cell.setBorder(Rectangle.NO_BORDER);
            employeeTable.addCell(cell);

            cell = new PdfPCell(new Phrase(nominaDTO.getFechaPago().toString(), normalFont));
            cell.setBorder(Rectangle.NO_BORDER);
            employeeTable.addCell(cell);

            pdfDocument.add(employeeTable);

            // Tabla para mostrar los detalles de la nómina
            Paragraph nominaInfo = new Paragraph("Detalles de la Nómina", headerFont);
            nominaInfo.setSpacingAfter(10);
            pdfDocument.add(nominaInfo);

            PdfPTable nominaTable = new PdfPTable(2);
            nominaTable.setWidthPercentage(100);
            nominaTable.setSpacingBefore(10f);
            nominaTable.setSpacingAfter(20f);

            // Añadir las celdas de encabezado
            cell = new PdfPCell(new Phrase("Descripción", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            nominaTable.addCell(cell);

            cell = new PdfPCell(new Phrase("Monto", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            nominaTable.addCell(cell);

            // Salario Bruto
            cell = new PdfPCell(new Phrase("Salario Bruto", normalFont));
            cell.setPadding(8);
            nominaTable.addCell(cell);

            cell = new PdfPCell(new Phrase("$" + String.format("%.2f", nominaDTO.getSalarioBruto()), normalFont));
            cell.setPadding(8);
            nominaTable.addCell(cell);

            // Deducciones
            cell = new PdfPCell(new Phrase("Deducciones (ISR)", normalFont));
            cell.setPadding(8);
            nominaTable.addCell(cell);

            cell = new PdfPCell(new Phrase("$" + String.format("%.2f", nominaDTO.getDeducciones()), normalFont));
            cell.setPadding(8);
            nominaTable.addCell(cell);

            // Salario Neto
            cell = new PdfPCell(new Phrase("Salario Neto", normalFont));
            cell.setPadding(8);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            nominaTable.addCell(cell);

            cell = new PdfPCell(new Phrase("$" + String.format("%.2f", nominaDTO.getSalarioNeto()), normalFont));
            cell.setPadding(8);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            nominaTable.addCell(cell);

            pdfDocument.add(nominaTable);

            // Cerrar el documento
            pdfDocument.close();

            System.out.println("PDF de nómina generado: " + ruta.toString());

            // Devolver la ruta del archivo generado
            return ruta.toString();

        } catch (IOException | DocumentException e) {
            System.err.println("Error al generar el PDF de la nómina: " + e.getMessage());
            return null;
        }
    }
}
