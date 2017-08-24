package com.epam.lab.web.rest;




import com.epam.lab.model.Book;

import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/library")
public interface LibraryService {

    @GET
    @Path("/books")
    @Produces("application/json; charset=UTF-8")
    public Response getAllBooks();

    @GET
    @Path("/book/{bookName}")
    @Produces("application/json; charset=UTF-8")
    public Response getBook(@PathParam("bookName") String name);

    @POST
    @Path("/turnBook")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json; charset=UTF-8")
    public Response turnBackBook(Book book);

    @POST
    @Path("/exchange/{bookName}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json; charset=UTF-8")
    public Response exchangeBook(Book book, @PathParam("bookName") String requiredBookName);

    @GET
    @Path("/books/author/{author}/{number}")
    @Produces("application/json; charset=UTF-8")
    public Response getAuthorBooks(@PathParam("author") String authorName, @PathParam("number") int number);
}
