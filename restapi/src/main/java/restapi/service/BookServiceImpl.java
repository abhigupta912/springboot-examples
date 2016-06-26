package restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restapi.domain.Book;
import restapi.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Abhishek Gupta
 *         https://github.com/abhigupta912
 */
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book findById(String id) {
        return bookRepository.findOne(id);
    }

    @Override
    public List<Book> findAll() {
        final Iterable<Book> booksIterator = bookRepository.findAll();
        final List<Book> books = new ArrayList<>();
        booksIterator.forEach(book -> books.add(book));
        return books;
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public Book findByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleAndAuthor(title, author);
    }

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void deleteBookById(String id) {
        bookRepository.delete(id);
    }
}
