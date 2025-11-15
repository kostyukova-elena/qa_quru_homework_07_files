import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ZipParsingTest {

    private static final Logger log = LoggerFactory.getLogger(ZipParsingTest.class);
    private final String zipPath = "src/test/resources/HW7.zip";


    @Test
    void testPdfContentInZip() throws IOException {
        String pdfFileName = "Instructions.pdf";

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals(pdfFileName)) {
                    // Читаем PDF из ZIP
                    byte[] pdfBytes = zis.readAllBytes();

                    // Проверяем содержимое
                    try (PDDocument document = PDDocument.load(pdfBytes)) {
                        PDFTextStripper stripper = new PDFTextStripper();
                        String pdfText = stripper.getText(document);

                        assertNotNull(pdfText); // Проверяем, что файл не Null
                        assertTrue(pdfText.contains("Общая информация о курсе")); // Проверяем, что в файле содержится текст
                        assertEquals(13, document.getNumberOfPages()); // Проверяем количество страниц
                    }
                    break;
                }
            }
        }
    }

    @Test
    void testXlsxContentInZip() throws IOException {
        String xlsxFileName = "Books.xlsx";

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals(xlsxFileName)) {
                    // Читаем XLSX из ZIP
                    byte[] xlsxBytes = zis.readAllBytes();

                    // Анализируем XLSX содержимое
                    try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(xlsxBytes))) {

                        assertEquals(1, workbook.getNumberOfSheets());// Проверяем количество листов

                        Sheet firstSheet = workbook.getSheetAt(0);
                        assertEquals("Лист1", firstSheet.getSheetName());// Проверяем название первого листа

                        // Проверяем данные в ячейках первой строки
                        Row firstRow = firstSheet.getRow(0);
                        assertNotNull(firstRow);

                        Cell cellA1 = firstRow.getCell(0);
                        assertEquals("Ф. М. Достоевский", cellA1.getStringCellValue());// Проверяем содержимое ячейки А1

                        Cell cellB1 = firstRow.getCell(1);
                        assertEquals("“Идиот”", cellB1.getStringCellValue());// Проверяем содержимое ячейки B1

                        // Проверяем данные в ячейках второй строки
                        Row secondRow = firstSheet.getRow(1);
                        if (secondRow != null) {
                            Cell cellA2 = secondRow.getCell(0);
                            assertEquals("Л. Н. Толстой", cellA2.getStringCellValue());// Проверяем содержимое ячейки А2

                            Cell cellB2 = secondRow.getCell(1);
                            assertEquals("“Война и мир”", cellB2.getStringCellValue());// Проверяем содержимое ячейки B2
                        }
                    }
                    break;
                }
            }
        }
    }

    @Test
    void testCsvContentInZip() throws IOException {
        String csvFileName = "Users.csv";

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals(csvFileName)) {
                    // Читаем CSV построчно
                    List<String> lines = new ArrayList<>();

                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(zis, StandardCharsets.UTF_8))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            lines.add(line);
                        }
                    }

                    assertFalse(lines.isEmpty());
                    assertEquals("name,email,age,city", lines.get(0)); // Проверяем заголовок
                    assertTrue(lines.get(1).contains("Ivan Petrov")); // Проверяем поле "name" из первой строки данных
                    assertTrue(lines.get(1).contains("ivan@example.com")); // Проверяем поле "email" из первой строки данных
                    assertTrue(lines.get(1).contains("28")); // Проверяем поле "age" из первой строки данных
                    assertTrue(lines.get(1).contains("Moscow")); // Проверяем поле "city" из первой строки данных
                    assertEquals(5, lines.size()); // Проверяем общее количество строк

                    break;
                }
            }
        }
    }
}
