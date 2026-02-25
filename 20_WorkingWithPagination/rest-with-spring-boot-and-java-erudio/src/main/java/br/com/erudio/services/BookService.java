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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import static br.com.erudio.mapper.ObjectMapper.parseObjects;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private Logger logger = LoggerFactory.getLogger(BookService.class.getName());

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    PagedResourcesAssembler<BookDTO> assembler;

    public PagedModel<EntityModel<BookDTO>> findAllBooks(Pageable pageable) {
        logger.info("Finding all Books!");

        var book = bookRepository.findAll(pageable);

        var booksWithLinks = book.map(books -> {
            var dto = parseObjects(books, BookDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        Link findAllLink = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(BookController.class)
                                .findAll(
                                        pageable.getPageNumber(),
                                        pageable.getPageSize(),
                                        String.valueOf(pageable.getSort())))
                .withSelfRel();

        return assembler.toModel(booksWithLinks, findAllLink);
    }

    public BookDTO findBookById(Long id) {
        logger.warn("Find a single Book by Id");

        var entidade = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        var dto = parseObjects(entidade, BookDTO.class);

        return dto;
    }

    public BookDTO createBook(BookDTO book) {
        logger.warn("Create a new Book");

        if (book == null) throw new RequiredObjectIsNullException();

        var entidade = parseObjects(book, Book.class);  // ← converte DTO → Entity
        var saved = bookRepository.save(entidade);
        var dto = parseObjects(saved, BookDTO.class);

        addHateoasLinks(dto);
        return dto;
    }

    public BookDTO updateBook(BookDTO book) {  // ← BookDTO
        logger.warn("Update a book by Id");

        Book bookEntity = bookRepository.findById(book.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setLaunchDate(book.getLaunchDate());
        bookEntity.setPrice(book.getPrice());
        bookEntity.setTitle(book.getTitle());

        var saved = bookRepository.save(bookEntity);
        var dto = parseObjects(saved, BookDTO.class);

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
        dto.add(linkTo(methodOn(BookController.class).findAll(1, 12, "asc")).withRel("findAll").withType("GET"));
    }

}
