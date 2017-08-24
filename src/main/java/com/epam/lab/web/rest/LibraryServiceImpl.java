package com.epam.lab.web.rest;

import com.epam.lab.bo.BookBO;
import com.epam.lab.model.Book;
import com.epam.lab.web.fault.FaultMessage;
import com.epam.lab.web.fault.ServiceFaultInfo;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

public class LibraryServiceImpl implements LibraryService {
    private Logger LOGGER = Logger.getLogger(LibraryServiceImpl.class);

    @Override
    public Response getAllBooks() {
        BookBO bookBO = new BookBO();

        return Response.ok().entity(bookBO.getAllBooks()).build();
    }

    @Override
    public Response getBook(String name) {
        Response response;
        BookBO bookBO = new BookBO();
        Book book = bookBO.getBook(name);

        if (Objects.isNull(book)) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NO_BOOK_WITH_NAME, name);

            response = Response.status(Response.Status.NOT_FOUND).entity(faultInfo).build();
        } else {
            response = Response.ok().entity(book).build();
        }

        return response;
    }

    @Override
    public Response turnBackBook(Book book) {
        BookBO bookBO = new BookBO();

        return Response.ok().entity(bookBO.addBook(book)).build();
    }

    @Override
    public Response exchangeBook(Book book, String requiredBookName){
        Response response;
        BookBO bookBO = new BookBO();
        Book requiredBook = bookBO.getBook(requiredBookName);

        if (Objects.isNull(requiredBook)) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NO_BOOK_WITH_NAME, requiredBookName);

            response = Response.status(Response.Status.NOT_FOUND).entity(faultInfo).build();
        } else {
            bookBO.addBook(book);
            response = Response.ok().entity(requiredBook).build();
        }

        return response;
    }

    @Override
    public Response getAuthorBooks(String authorName, int number) {
        Response response;
        BookBO bookBO = new BookBO();
        List<Book> authorBookList = bookBO.getBooksByAuthorName(authorName);

        if (authorBookList.size() < number) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NOT_ENOUGH_BOOKS_OF_AUTHOR, authorBookList.size() + 1, authorName, number);

            response = Response.status(Response.Status.NOT_FOUND).entity(faultInfo).build();
        } else {
            response = Response.ok().entity(authorBookList.subList(0, number)).build();
        }

        return response;
    }
}
