package guru.springframework.jdbc.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoHibernateTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    AuthorDao authorDao;

    @BeforeEach
    void setUp() {
        authorDao = new AuthorDaoHibernate(entityManagerFactory);
    }

    @Test
    void findAllAuthorsByLastNameSortByFirstName_noSort() {
        var authors = authorDao.findAllAuthorsByLastNameSortByFirstName("Smith",
            PageRequest.of(0, 10));

        assertThat(authors)
            .isNotNull()
            .hasSize(10);
    }

    @Test
    void findAllAuthorsByLastNameSortByFirstName_sorted() {
        var authors = authorDao.findAllAuthorsByLastNameSortByFirstName("Smith",
            PageRequest.of(1, 10, Sort.by(Sort.Order.desc("first_name"))));

        assertThat(authors)
            .isNotNull()
            .hasSize(10);
    }
}