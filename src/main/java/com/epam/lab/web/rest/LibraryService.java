package com.epam.lab.web.rest;




import com.epam.lab.model.Book;
import com.epam.lab.web.soap.exeption.ServiceException;

import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/libraryREST")
public interface LibraryService {

    @GET
    @Path("/books")
    @Produces("application/json; charset=UTF-8")
    Response getAllBooks();

    @GET
    @Path("/book/{bookName}")
    @Consumes("text/plain; charset=UTF-8")
    @Produces("application/json; charset=UTF-8")
    Response getBook(@PathParam("bookName") String name);

    @POST
    @Path("/book")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json; charset=UTF-8")
    Response addBook(Book book);

    @POST
    @Path("/exchange/{bookName}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json; charset=UTF-8")
    Response exchangeBook(Book book, @PathParam("bookName") String requiredBookName);

    @GET
    @Path("/books/authors/{author}/{number}")
    @Produces("application/json; charset=UTF-8")
    Response getAuthorBooks(@PathParam("author") String authorName, @PathParam("number") int number);

    @DELETE
    @Path("/book")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json; charset=UTF-8")
    Response removeBook(String name) throws ServiceException;

}
