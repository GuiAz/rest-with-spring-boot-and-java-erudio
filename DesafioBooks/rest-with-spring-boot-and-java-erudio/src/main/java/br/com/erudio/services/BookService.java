package br.com.erudio.services;

import br.com.erudio.exception.RequiredObjectIsNullException;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.model.Book;
import br.com.erudio.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private Logger logger = LoggerFactory.getLogger(BookService.class.getName());

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAllBooks() {
        logger.warn("Find All Books");


        return bookRepository.findAll();
    }

    public Book findBookById(Long id) {
        logger.warn("Find a single Book by Id");

        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book createBook(Book book) {
        logger.warn("Create a new Book");

        if (book == null) throw new RequiredObjectIsNullException();

        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        logger.warn("Update a book by Id");

        Book bookEntity = bookRepository.findById(book.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setLaunchDate(book.getLaunchDate());
        bookEntity.setPrice(book.getPrice());
        bookEntity.setTitle(book.getTitle());

        return bookRepository.save(bookEntity);
    }

    public void deleteBook(Long id) {
        logger.warn("Deleting a existing book");

        Book bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book nout found"));

        bookRepository.delete(bookEntity);
    }

}
