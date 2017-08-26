package com.epam.lab.web.soap;

import com.epam.lab.model.Book;
import com.epam.lab.web.soap.exeption.ServiceException;

import javax.jws.WebService;
import java.util.List;

@WebService
interface LibraryService {
    List<Book> getAllBooks();

    Book getBook(String name) throws ServiceException;

    boolean addBook(Book book) throws ServiceException;

    boolean removeBook(String name) throws ServiceException;

    Book exchangeBook(Book book, String requiredBookName) throws ServiceException;

    List<Book> getAuthorBooks(String authorName, int number) throws ServiceException;
}
