package usecases;

import entities.Library;
import lombok.Getter;
import lombok.Setter;
import persistence.LibraryDAO;
import services.BookLoader;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Model
public class Libraries implements Serializable {
    @Inject
    private LibraryDAO libraryDAO;

    @Inject
    @Getter
    private BookLoader bookLoader;

    @Getter
    @Setter
    private Library libraryToCreate = new Library();

    @Getter
    private List<Library> allLibraries;

    @PostConstruct
    public void init() {
        loadAllLibraries();
    }

    @Transactional
    public void createLibrary() {
        this.libraryDAO.save(libraryToCreate);
    }

    private void loadAllLibraries() {
        this.allLibraries = libraryDAO.findAll();
    }
}