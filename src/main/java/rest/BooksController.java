package rest;

import entities.Author;
import entities.Book;
import entities.Library;
import persistence.AuthorDAO;
import persistence.BookDAO;
import persistence.LibraryDAO;
import rest.DTO.AuthorDTO;
import rest.DTO.BookCreateDTO;
import rest.DTO.BookDTO;
import rest.DTO.LibraryDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Path("/books")
public class BooksController {
    @Inject
    private AuthorDAO authorDAO;

    @Inject
    private BookDAO bookDAO;

    @Inject
    private LibraryDAO libraryDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Book> books = bookDAO.findAll();
        return Response.ok(books.stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList())).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Long id) {
        Book book = bookDAO.findById(id);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(EntityToDTO(book)).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("id") final long id, BookCreateDTO body) {
        Book book = bookDAO.findById(id);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Library library = libraryDAO.findById(body.getLibrary());
        if (library == null) return Response.status(Response.Status.BAD_REQUEST).entity("Library not found").build();

        List<Long> authorIds = body.getAuthors();
        List<Author> authors = new ArrayList<>();
        for (Long authorId : authorIds) {
            Author author = authorDAO.findById(authorId);
            if (author == null)
                return Response.status(Response.Status.BAD_REQUEST).entity("One or more authors were not found.").build();
            authors.add(author);
        }

        book.setId(book.getId());
        book.setName(book.getName());
        book.setPages(book.getPages());
        book.setEstimatedReturnDate(book.getEstimatedReturnDate());
        book.setVersion(book.getVersion());
        book.setCheckOutDate(book.getCheckOutDate());
        book.setAuthors(authors);
        book.setLibrary(library);

        try {
            bookDAO.update(book);
        } catch (OptimisticLockException ole) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        return Response.ok(EntityToDTO(book)).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(BookCreateDTO body) {
        Book book = new Book();

        Library library = libraryDAO.findById(body.getLibrary());
        if (library == null) return Response.status(Response.Status.BAD_REQUEST).entity("Library not found").build();

        List<Long> authorIds = body.getAuthors();
        List<Author> authors = new ArrayList<>();
        for (Long authorId : authorIds) {
            Author author = authorDAO.findById(authorId);
            if (author == null)
                return Response.status(Response.Status.BAD_REQUEST).entity("One or more authors were not found.").build();
            authors.add(author);
        }

        book.setId(book.getId());
        book.setName(book.getName());
        book.setPages(book.getPages());
        book.setEstimatedReturnDate(book.getEstimatedReturnDate());
        book.setVersion(book.getVersion());
        book.setCheckOutDate(book.getCheckOutDate());
        book.setAuthors(authors);
        book.setLibrary(library);

        bookDAO.save(book);
        return Response.ok(EntityToDTO(book)).build();
    }

    @Path("/{id}")
    @DELETE
    @Transactional
    public Response delete(@PathParam("id") final long id) {
        Book book = bookDAO.findById(id);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        bookDAO.delete(book);
        return Response.noContent().build();
    }

    private BookDTO EntityToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setName(book.getName());
        dto.setPages(book.getPages());
        dto.setEstimatedReturnDate(book.getEstimatedReturnDate());
        dto.setVersion(book.getVersion());
        dto.setCheckOutDate(book.getCheckOutDate());

        List<AuthorDTO> authors = book.getAuthors().stream()
                .map(author -> new AuthorDTO(author.getId(), author.getName()))
                .collect(Collectors.toList());
        dto.setAuthors(authors);

        Library library = book.getLibrary();
        dto.setLibrary(new LibraryDTO(library.getId(), library.getName()));
        return dto;
    }
}
