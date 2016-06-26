package restapi.service;

import restapi.domain.Book;

import java.util.List;

/**
 * @author Abhishek Gupta
 *         https://github.com/abhigupta912
 */
public interface BookService {
    Book findById(String id);

    List<Book> findAll();

    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);

    Book findByTitleAndAuthor(String title, String author);

    void addBook(Book book);

    void deleteBookById(String id);
}
