package mx.multivelopers.resource;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

import mx.multivelopers.entity.Book;
import mx.multivelopers.service.BookService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.net.URI;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/books")
public class BookResource {

    private static final Logger LOGGER = Logger.getLogger(BookResource.class);

    @Inject
    BookService service;

    @Operation(summary = "Returns all the books from the database")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Book.class, type = SchemaType.ARRAY)))
    @APIResponse(responseCode = "204", description = "No books")
    @GET
    public Response getAllBooks() {
        List<Book> books = service.findAllBooks();
        LOGGER.debug("Total number of Books " + books);
        return Response.ok(books).build();
    }

    @Operation(summary = "Returns a book from a given id")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Book.class)))
    @APIResponse(responseCode = "204", description = "The book is not found for a given id")
    @GET
    @Path("/{id}")
    public Response getBook(@PathParam("id") Long id) {
        Book book = service.findBookById(id);
        if (book != null) {
            LOGGER.debug("Found Book " + book);
            return Response.ok(book).build();
        } else {
            LOGGER.debug("No Book found with id " + id);
            return Response.noContent().build();
        }
    }

    @Operation(summary = "Returns a book fro a given name")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Book.class)))
    @APIResponse(responseCode = "204", description = "The book is not found for a given name")
    @GET
    @Path("/byName/{name}")
    public Response getBook(@PathParam("name") String name) {
        Book book = service.findBookByName(name);
        if (book != null) {
            LOGGER.debug("Found Book " + book);
            return Response.ok(book).build();
        } else {
            LOGGER.debug("No Book found with name " + name);
            return Response.noContent().build();
        }
    }

    @Operation(summary = "Returns all the books from the database which the publication year is between two provided values")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Book.class, type = SchemaType.ARRAY)))
    @APIResponse(responseCode = "204", description = "No books")
    @GET
    @Path("/{lowerYear}/{higherYear}")
    public Response getBookByPublicationYearBetween(@PathParam("lowerYear") Integer lowerYear,
            @PathParam("higherYear") Integer higherYear) {
        List<Book> books = service.findBookByPublicationYearBetween(lowerYear, higherYear);
        if (books != null) {
            LOGGER.debug("Found Book " + books);
            return Response.ok(books).build();
        } else {
            LOGGER.debug("No Book found with publication year betueen " + lowerYear + " and " + higherYear);
            return Response.noContent().build();
        }
    }

    @Operation(summary = "Creates a valid book")
    @APIResponse(responseCode = "201", description = "The URI of the created book", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = URI.class)))
    @POST
    public Response createBook(Book book, @Context UriInfo uriInfo) {
        book = service.persistBook(book);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(book.id));
        LOGGER.debug("New Book created with URI " + builder.build().toString());
        return Response.created(builder.build()).build();
    }

    @Operation(summary = "Updates an exiting  book")
    @APIResponse(responseCode = "200", description = "The updated book", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Book.class)))
    @PUT
    public Response updateBook(Book book) {
        book = service.updateBook(book);
        LOGGER.debug("Book updated with new valued " + book);
        return Response.ok(book).build();
    }

    @Operation(summary = "Deletes an exiting book")
    @APIResponse(responseCode = "204")
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        service.deleteBook(id);
        LOGGER.debug("Book deleted with " + id);
        return Response.noContent().build();
    }

}