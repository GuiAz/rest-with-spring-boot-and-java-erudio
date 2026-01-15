package br.com.erudio.unitetests.mapper.mocks;

import br.com.erudio.data.dto.BookDTO;
import br.com.erudio.model.Book;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MockBook {

    public Book mockEntity() {
        return mockEntity(0);
    }

    public BookDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<BookDTO>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }

    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setAuthor("Author-" + number);
        book.setTitle("Title-" + number);
        book.setPrice(100.0 + number);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.JANUARY, 1);
        calendar.add(Calendar.DAY_OF_MONTH, number);
        book.setLaunchDate(calendar.getTime());
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO book = new BookDTO();
        book.setAuthor("Author-" + number);
        book.setTitle("Title-" + number);
        book.setPrice(100.0 + number);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.JANUARY, 1);
        calendar.add(Calendar.DAY_OF_MONTH, number);
        book.setLaunchDate(calendar.getTime());
        return book;
    }

}