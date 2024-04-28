package usecases;

import entities.Author;
import entities.Book;
import lombok.Getter;
import lombok.Setter;
import persistence.AuthorDAO;
import persistence.BookDAO;
import persistence.LibraryDAO;
import services.ReturnDateEstimationService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Model
public class Books implements Serializable {
    @Inject
    private BookDAO bookDAO;

    @Inject
    private LibraryDAO libraryDAO;

    @Inject
    private AuthorDAO authorDAO;

    @Inject
    private ReturnDateEstimationService returnDateEstimationService;

    @Getter
    @Setter
    private Book bookToCreate = new Book();

    @Getter
    @Setter
    private Long library;

    @Getter
    @Setter
    private List<Long> authors = new ArrayList<>();

    @Getter
    private List<Book> allBooks;

    @PostConstruct
    public void init() {
        loadAllBooks();
    }

    @Transactional
    public void createBook() {
        bookToCreate.setLibrary(libraryDAO.findById(library));

        List<Author> authorsEntities = new ArrayList<>();
        for(long id : authors) {
            authorsEntities.add(authorDAO.findById(id));
        }
        bookToCreate.setAuthors(authorsEntities);

        bookToCreate.setEstimatedReturnDate(returnDateEstimationService.estimateReturnDate(bookToCreate));

        this.bookDAO.save(bookToCreate);
    }

    private void loadAllBooks() {
        this.allBooks = bookDAO.findAll();
    }
}