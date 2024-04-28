package persistence;

import entities.Author;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class AuthorDAO {

    @Inject
    private EntityManager em;

    public List<Author> findAll() {
        return em.createNamedQuery("Author.findAll", Author.class).getResultList();
    }

    public Author findById(long id) {
        return em.find(Author.class, id);
    }

    public void save(Author author) {
        em.persist(author);
    }

    public Author update(Author author) {
        return em.merge(author);
    }

    public void delete(Author author) {
        if (em.contains(author))
            em.remove(author);
    }
}