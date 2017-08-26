package com.epam.lab.web.soap;

import com.epam.lab.bo.BookBO;
import com.epam.lab.model.Book;
import com.epam.lab.web.fault.FaultMessage;
import com.epam.lab.web.fault.ServiceFaultInfo;
import com.epam.lab.web.soap.exeption.ServiceException;
import org.apache.log4j.Logger;

import javax.jws.WebService;
import java.util.List;
import java.util.Objects;

@WebService(endpointInterface = "com.epam.lab.web.soap.LibraryService")
public class LibraryServiceImpl implements LibraryService {
    private Logger LOGGER = Logger.getLogger(LibraryService.class);

    @Override
    public List<Book> getAllBooks() {
        LOGGER.info("getAllBooks method");
        BookBO bookBO = new BookBO();
        return bookBO.getAllBooks();
    }

    @Override
    public Book getBook(String name) throws ServiceException {
        LOGGER.info("getBook method");
        BookBO bookBO = new BookBO();

        Book book = bookBO.getBook(name);

        if (Objects.isNull(book)) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NO_BOOK_WITH_NAME, name);
            LOGGER.warn(faultInfo);
            throw new ServiceException(faultInfo);
        }

        LOGGER.info("getBook result:" + book);
        return book;
    }

    @Override
    public boolean addBook(Book book) throws ServiceException {
        LOGGER.info("addBook method");
        BookBO bookBO = new BookBO();
        if (!bookBO.addBook(book)) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.SUCH_BOOK_ALREADY_EXIST, book);
            LOGGER.warn(faultInfo.getMessage());
            throw new ServiceException(faultInfo);
        }

        LOGGER.info("addBook result:" + true);
        return true;
    }

    @Override
    public boolean removeBook(String name) throws ServiceException {
        LOGGER.info("removeBook method");

        BookBO bookBO = new BookBO();
        if (!bookBO.removeBook(name)) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NO_BOOK_WITH_NAME, name);

            LOGGER.warn(faultInfo.getMessage());
            throw new ServiceException(faultInfo);
        }

        LOGGER.info("removeBook result:" + true);
        return true;
    }

    @Override
    public Book exchangeBook(Book book, String requiredBookName) throws ServiceException {
        LOGGER.info("exchangeBook method");
        BookBO bookBO = new BookBO();
        Book requiredBook = bookBO.getBook(requiredBookName);

        if (Objects.isNull(requiredBook)) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NO_BOOK_WITH_NAME, requiredBookName);

            LOGGER.warn(faultInfo.getMessage());
            throw new ServiceException(faultInfo);
        } else {
            if (Objects.isNull(bookBO.getBook(book.getName()))) {
                bookBO.addBook(book);
            } else {
                ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.SUCH_BOOK_ALREADY_EXIST, book);

                LOGGER.warn(faultInfo.getMessage());
                throw new ServiceException(faultInfo);
            }
        }

        LOGGER.info("exchangeBook result:" + requiredBookName);
        return requiredBook;
    }

    @Override
    public List<Book> getAuthorBooks(String authorName, int number) throws ServiceException {
        LOGGER.info("getAuthorBooks method");
        BookBO bookBO = new BookBO();
        List<Book> authorBookList = bookBO.getBooksByAuthorName(authorName);

        if (authorBookList.size() < number) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NOT_ENOUGH_BOOKS_OF_AUTHOR, authorBookList.size(), authorName, number);
            LOGGER.warn(faultInfo.getMessage());
            throw new ServiceException(faultInfo);
        }

        return authorBookList.subList(0, number);
    }
}
