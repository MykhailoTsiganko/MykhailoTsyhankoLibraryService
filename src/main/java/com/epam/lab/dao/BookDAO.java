package com.epam.lab.dao;

import com.epam.lab.model.Book;
import com.epam.lab.utills.CSVBookManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class BookDAO {
    public static final String BOOK_URl = "src/main/resources/books.csv";
    private static Logger LOGGER = Logger.getLogger(BookDAO.class);

    public static List<Book> findAll() {
        return CSVBookManager.readBooks(new File(BOOK_URl));
    }

    public static Book findByName(String name) {
        LOGGER.info("findByName method");
        List<Book> bookList = CSVBookManager.readBooks(new File(BOOK_URl));
        Book myBook = null;

        for (Book book : bookList) {
            if (book.getName().equals(name)) {
                myBook = book;
                break;
            }
        }

        return myBook;
    }

    public static boolean insertBook(Book book) {
        LOGGER.info("insertBook method");
        List<Book> bookList = CSVBookManager.readBooks(new File(BOOK_URl));
        boolean isAdded;

        if (!bookList.contains(book)) {
            bookList.add(book);
            CSVBookManager.writeBooks(bookList, new File(BOOK_URl));
            isAdded = true;
        } else {
            isAdded = false;
        }

        return isAdded;
    }

    public static boolean deleteBook(String name) {
        LOGGER.info("deleteBook method");
        List<Book> bookList = CSVBookManager.readBooks(new File(BOOK_URl));
        ListIterator<Book> it = bookList.listIterator();
        boolean swapped = false;

        if (!Objects.isNull(findByName(name))) {
            while (it.hasNext()) {
                Book book = it.next();
                if (book.getName().equals(name)) {
                    it.remove();
                    CSVBookManager.writeBooks(bookList, new File(BOOK_URl));
                    swapped = true;
                    break;
                }
            }
        }

        return swapped;
    }
}
