package com.epam.lab.web.soap;

import com.epam.lab.bo.BookBO;
import com.epam.lab.model.Book;
import com.epam.lab.web.fault.FaultMessage;
import com.epam.lab.web.fault.ServiceFaultInfo;
import com.epam.lab.web.soap.exeption.ServiceException;
import org.apache.log4j.Logger;

import javax.jws.WebService;
import javax.ws.rs.core.Response.Status;
import java.util.List;
import java.util.Objects;

@WebService(endpointInterface = "com.epam.lab.web.soap.LibraryService")
public class LibraryServiceImpl implements LibraryService {
    private Logger LOGGER = Logger.getLogger(LibraryService.class);

    @Override
    public List<Book> getAllBooks() {
        BookBO bookBO = new BookBO();
        return bookBO.getAllBooks();
    }

    @Override
    public Book getBook(String name) throws ServiceException {
        BookBO bookBO = new BookBO();

        Book book = bookBO.getBook(name);

        if (Objects.isNull(book)) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NO_BOOK_WITH_NAME, name);
            LOGGER.warn(faultInfo);
            throw new ServiceException(faultInfo);
        }

        return book;
    }

    @Override
    public boolean turnBackBook(Book book) {
        BookBO bookBO = new BookBO();

        return bookBO.addBook(book);
    }

    @Override
    public Book exchangeBook(Book book, String requiredBookName) throws ServiceException {
        BookBO bookBO = new BookBO();
        Book requiredBook = bookBO.getBook(requiredBookName);

        if (Objects.isNull(requiredBook)) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NO_BOOK_WITH_NAME, requiredBookName);

            LOGGER.warn(faultInfo.getMessage());
            throw new ServiceException(faultInfo);
        }
        bookBO.addBook(book);

        return requiredBook;
    }

    @Override
    public List<Book> getAuthorBooks(String authorName, int number) throws ServiceException {
        BookBO bookBO = new BookBO();
        List<Book> authorBookList = bookBO.getBooksByAuthorName(authorName);

        if (authorBookList.size() < number) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NOT_ENOUGH_BOOKS_OF_AUTHOR, authorBookList.size() + 1, authorName, number);
            LOGGER.warn(faultInfo.getMessage());
            throw new ServiceException(faultInfo);
        }

        return authorBookList.subList(0, number);
    }
}
