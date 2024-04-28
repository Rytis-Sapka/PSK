package persistence;

import entities.Library;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class LibraryDAO {

    @Inject
    private EntityManager em;

    public List<Library> findAll() {
        return em.createNamedQuery("Library.findAll", Library.class).getResultList();
    }

    public Library findById(long id) {
        return em.find(Library.class, id);
    }

    public Library findByName(String name) {
        return em.createNamedQuery("Library.findByName", Library.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public void save(Library library) {
        em.persist(library);
    }

    public Library update(Library library) {
        return em.merge(library);
    }

    public void delete(Library library) {
        if (em.contains(library))
            em.remove(library);
    }
}