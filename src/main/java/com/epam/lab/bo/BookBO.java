package com.epam.lab.bo;

import com.epam.lab.dao.BookDAO;
import com.epam.lab.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookBO {

    public List<Book> getAllBooks() {
        return BookDAO.findAll();
    }

    public Book getBook(String name) {
        return BookDAO.findByName(name);
    }

    public List<Book> getBooksByAuthorName(String authorName) {
        List<Book> bookList = getAllBooks();
        List<Book> authorBookList = new ArrayList<>();
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getAuthor().equals(authorName)) {
                authorBookList.add(bookList.get(i));
            }
        }

        return authorBookList;
    }

    public boolean addBook(Book book) {
        return BookDAO.insertBook(book);
    }

    public boolean removeBook(String name) {
        return BookDAO.deleteBook(name);
    }

}
