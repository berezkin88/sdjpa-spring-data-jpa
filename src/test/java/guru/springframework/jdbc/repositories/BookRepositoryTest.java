package guru.springframework.jdbc.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan("guru.springframework.jdbc.dao")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void bookStream() {
        var counterAtomicInteger = new AtomicInteger();

        bookRepository.findAllByTitleNotNull().forEach(book -> counterAtomicInteger.incrementAndGet());

        assertThat(counterAtomicInteger.get()).isGreaterThan(5);
    }

    @Test
    void emptyResultException() {
        assertThrows(EmptyResultDataAccessException.class, () -> bookRepository.readByTitle("foobar"));
    }

    @Test
    void nullParam() {
        assertNull(bookRepository.getByTitle(null));
    }

    @Test
    void noException() {
        assertNull(bookRepository.getByTitle("foo"));
    }
}