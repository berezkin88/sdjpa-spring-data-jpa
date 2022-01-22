package guru.springframework.jdbc.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
class AuthorDaoJDBCTemplateTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    AuthorDao authorDao;

    @BeforeEach
    void setUp() {
        authorDao = new AuthorDaoJDBCTemplate(jdbcTemplate);
    }

    @Test
    void findAllAuthorsByLastNameSortByFirstName_page1() {
        var authors = authorDao.findAllAuthorsByLastNameSortByFirstName("Smith",
            PageRequest.of(0, 10, Sort.by(Sort.Order.desc("first_name"))));

        assertThat(authors)
            .isNotNull()
            .hasSize(10);
    }

    @Test
    void findAllAuthorsByLastNameSortByFirstName_page2() {
        var authors = authorDao.findAllAuthorsByLastNameSortByFirstName("Smith",
            PageRequest.of(1, 10, Sort.by(Sort.Order.desc("first_name"))));

        assertThat(authors)
            .isNotNull()
            .hasSize(10);
    }
}