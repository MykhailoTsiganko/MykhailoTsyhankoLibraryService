package com.epam.lab.utills;

import com.epam.lab.model.Book;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVBookManager {
    public static String[] HEADERS = {"Name", "Author", "Genre"};

    /**
     * Method is handel csv file encoding issue
     */
    private static InputStreamReader newReader(final InputStream inputStream) {
        return new InputStreamReader(new BOMInputStream(inputStream), StandardCharsets.UTF_8);
    }

    public static List<Book> readBooks(File file) {
        List<Book> list = new ArrayList<>();
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            Iterable<CSVRecord> parser = CSVFormat.DEFAULT.withHeader(HEADERS).withSkipHeaderRecord(true).parse(newReader(in));

            for (CSVRecord record : parser) {
                list.add(new Book(record.get("Name"), record.get("Author"), record.get("Genre")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public static void writeBooks(List<Book> bookList, File file) {
        Writer out = null;
        CSVPrinter csvFilePrinter = null;
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n").withNullString("");

        try {
             out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));

            csvFilePrinter = new CSVPrinter(out, csvFileFormat);

            csvFilePrinter.printRecord(HEADERS);

            for (Book book : bookList) {
                csvFilePrinter.printRecord(book.getName(),book.getAuthorName(),book.getGenre());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
                csvFilePrinter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
