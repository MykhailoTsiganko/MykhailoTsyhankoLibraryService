package com.epam.lab.web.rest;

import com.epam.lab.bo.BookBO;
import com.epam.lab.model.Book;
import com.epam.lab.web.fault.FaultMessage;
import com.epam.lab.web.fault.ServiceFaultInfo;
import com.epam.lab.web.soap.exeption.ServiceException;
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
            LOGGER.warn(faultInfo.getMessage());

            response = Response.status(Response.Status.NOT_FOUND).entity(faultInfo).build();
        } else {
            response = Response.ok().entity(book).build();
        }

        return response;
    }

    @Override
    public Response addBook(Book book) {
        LOGGER.info("addBook method");
        Response response;
        BookBO bookBO = new BookBO();
        if(bookBO.addBook(book)) {
            response = Response.ok().build();
        } else {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.SUCH_BOOK_ALREADY_EXIST,book.getName());
            LOGGER.warn(faultInfo.getMessage());

            response = Response.status(Response.Status.NOT_ACCEPTABLE).entity(faultInfo).build();
        }

        return response;
    }

    @Override
    public Response exchangeBook(Book book, String requiredBookName){
        Response response;
        BookBO bookBO = new BookBO();
        Book requiredBook = bookBO.getBook(requiredBookName);

        if (Objects.isNull(requiredBook)) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NO_BOOK_WITH_NAME, requiredBookName);
            LOGGER.warn(faultInfo.getMessage());

            response = Response.status(Response.Status.NOT_ACCEPTABLE).entity(faultInfo).build();
        } else {
            if(Objects.isNull(bookBO.getBook(book.getName()))){
                bookBO.addBook(book);
                response = Response.ok().entity(requiredBook).build();
            } else {
                ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NO_BOOK_WITH_NAME, book.getName());
                LOGGER.warn(faultInfo.getMessage());

                response = Response.status(Response.Status.NOT_FOUND).entity(faultInfo).build();
            }
        }

        return response;
    }

    @Override
    public Response getAuthorBooks(String authorName, int number) {
        Response response;
        BookBO bookBO = new BookBO();
        List<Book> authorBookList = bookBO.getBooksByAuthorName(authorName);

        if (authorBookList.size() < number) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NOT_ENOUGH_BOOKS_OF_AUTHOR, authorBookList.size(), authorName, number);
            LOGGER.warn(faultInfo.getMessage());

            response = Response.status(Response.Status.NOT_ACCEPTABLE).entity(faultInfo).build();
        } else {
            response = Response.ok().entity(authorBookList.subList(0, number)).build();
        }

        return response;
    }

    @Override
    public Response removeBook(String name){
        Response response;
        BookBO bookBO = new BookBO();
        if(!bookBO.removeBook(name)) {
            ServiceFaultInfo faultInfo = new ServiceFaultInfo(FaultMessage.NO_BOOK_WITH_NAME, name);

            LOGGER.warn(faultInfo.getMessage());
            response = Response.status(Response.Status.NOT_FOUND).entity(faultInfo).build();
        } else {
            response = Response.ok().build();
        }


        return response;
    }
}
