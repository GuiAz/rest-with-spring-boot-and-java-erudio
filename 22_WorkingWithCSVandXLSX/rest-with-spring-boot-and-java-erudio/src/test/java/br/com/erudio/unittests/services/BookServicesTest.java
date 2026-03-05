package br.com.erudio.unittests.services;

import br.com.erudio.model.Book;
import br.com.erudio.repository.BookRepository;
import br.com.erudio.services.BookService;
import br.com.erudio.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServicesTest {

    MockBook input;

    @InjectMocks
    BookService bookService;

    @Mock
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findBookById() {
        Book book = input.mockEntity(1);
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        var result = bookService.findBookById(1L);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/book/v1")
                        && link.getType().equals("POST")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/book/v1/1")
                        && link.getType().equals("DELETE")
                ));

        assertEquals("Author-1", result.getAuthor());
        assertEquals("Title-1", result.getTitle());
        assertEquals(101.0, result.getPrice());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals("2020-01-02", sdf.format(result.getLaunchDate()));
    }

}
