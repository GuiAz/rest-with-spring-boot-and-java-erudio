package br.com.erudio.services;

import br.com.erudio.controllers.BookController;
import br.com.erudio.data.dto.BookDTO;
import br.com.erudio.exception.RequiredObjectIsNullException;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.model.Book;
import br.com.erudio.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.erudio.mapper.ObjectMapper.parseListObjects;
import static br.com.erudio.mapper.ObjectMapper.parseObjects;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private Logger logger = LoggerFactory.getLogger(BookService.class.getName());

    @Autowired
    private BookRepository bookRepository;

    public List<BookDTO> findAllBooks() {
        logger.warn("Find All Books");

        var listaEntidade = bookRepository.findAll();
        var dto = parseListObjects(listaEntidade, BookDTO.class);
        dto.forEach(this::addHateoasLinks);
        return dto;
    }

    public BookDTO findBookById(Long id) {
        logger.warn("Find a single Book by Id");

        var entidade = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        var dto = parseObjects(entidade, BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public BookDTO createBook(BookDTO book) {
        logger.warn("Create a new Book");

        if (book == null) throw new RequiredObjectIsNullException();

        var entidade = parseObjects(book, Book.class);
        var dto = parseObjects(bookRepository.save(entidade), BookDTO.class);
        addHateoasLinks(dto);
        return dto;

    }

    public BookDTO updateBook(BookDTO book) {
        logger.warn("Update a book by Id");

        if (book == null) throw new RequiredObjectIsNullException();

        Book bookEntity = bookRepository.findById(book.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setLaunchDate(book.getLaunchDate());
        bookEntity.setPrice(book.getPrice());
        bookEntity.setTitle(book.getTitle());

        var dto = parseObjects(bookRepository.save(bookEntity), BookDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void deleteBook(Long id) {
        logger.warn("Deleting a existing book");

        Book bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book nout found"));

        bookRepository.delete(bookEntity);
    }

    private void addHateoasLinks(BookDTO dto) {
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("update").withType("UPDATE"));
        dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }

}
