package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class AuthorDaoHibernate implements AuthorDao {

    private final EntityManagerFactory entityManagerFactory;

    public AuthorDaoHibernate(EntityManagerFactory emf) {
        this.entityManagerFactory = emf;
    }

    @Override
    public List<Author> findAllAuthorsByLastNameSortByFirstName(String lastName, Pageable pageable) {
        var entityManager = getEntityManager();

        try {
            var hqlStringBuilder = new StringBuilder("FROM Author a ");
            hqlStringBuilder.append("where a.lastName = :lastName");

            if (pageable.getSort().getOrderFor("first_name") != null) {
                hqlStringBuilder.append(" ORDER BY a.firstName ")
                    .append(pageable.getSort().getOrderFor("first_name").getDirection().name());
            }

            var query = entityManager.createQuery(hqlStringBuilder.toString(), Author.class);
            query.setParameter("lastName", lastName);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Author getById(Long id) {
        return null;
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }

    private EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
}
