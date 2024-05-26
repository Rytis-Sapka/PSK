package persistence;

import entities.Book;
import services.LogInterceptor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
@LogInterceptor
public class BookDAO {

    @Inject
    private EntityManager em;

    public List<Book> findAll() {
        return em.createNamedQuery("Book.findAll", Book.class).getResultList();
    }

    public Book findById(long id) {
        return em.find(Book.class, id);
    }

    public void save(Book book) { em.persist(book);}

    public Book update(Book book) {
        return em.merge(book);
    }

    public void delete(Book book) { em.remove(book); }
}