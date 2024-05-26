package usecases;

import entities.Author;
import entities.Book;
import lombok.Getter;
import lombok.Setter;
import persistence.AuthorDAO;
import persistence.BookDAO;
import persistence.LibraryDAO;
import services.BookLoader;
import services.EstimationService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Model
@ViewScoped
public class Books implements Serializable {
    @Inject
    private BookDAO bookDAO;

    @Inject
    private LibraryDAO libraryDAO;

    @Inject
    private AuthorDAO authorDAO;

    @Inject
    private EstimationService returnDateEstimationService;

    @Inject
    private BookLoader bookLoader;

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
        bookLoader.removeLibrary(library);

        List<Author> authorsEntities = new ArrayList<>();
        for(long id : authors) {
            authorsEntities.add(authorDAO.findById(id));
        }
        bookToCreate.setAuthors(authorsEntities);

        bookToCreate.setEstimatedReturnDate(returnDateEstimationService.estimateReturnDate(bookToCreate));

        this.bookDAO.save(bookToCreate);
    }

    @Transactional
    public void updateBook(Book book) {
        try {
            this.bookDAO.update(book);
        }
        catch (OptimisticLockException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Failed", "This book has been modified or deleted by another user."));
        }
    }

    private void loadAllBooks() {
        this.allBooks = bookDAO.findAll();
    }
}