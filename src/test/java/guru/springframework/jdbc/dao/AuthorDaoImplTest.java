package guru.springframework.jdbc.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
class AuthorDaoImplTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    void findAllAuthorsByLastNameSortByFirstName_page1() {
        var authors = authorDao.findAllAuthorsByLastNameSortByFirstName("Smith",
            PageRequest.of(0, 10, Sort.by(Sort.Order.desc("firstName"))));

        assertThat(authors)
            .isNotNull()
            .hasSize(10);
    }

    @Test
    void findAllAuthorsByLastNameSortByFirstName_page2() {
        var authors = authorDao.findAllAuthorsByLastNameSortByFirstName("Smith",
            PageRequest.of(1, 10, Sort.by(Sort.Order.desc("firstName"))));

        assertThat(authors)
            .isNotNull()
            .hasSize(10);
    }
}