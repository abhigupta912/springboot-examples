package restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import restapi.domain.Book;
import restapi.service.BookService;

import java.util.List;

/**
 * @author Abhishek Gupta
 *         https://github.com/abhigupta912
 */
@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public Book getBookById(@PathVariable("id") String id) {
        return bookService.findById(id);
    }

    @RequestMapping(value = "/title/{title}", method = RequestMethod.GET)
    public List<Book> getBooksByTitle(@PathVariable("title") String title) {
        return bookService.findByTitle(title);
    }

    @RequestMapping(value = "/author/{author}", method = RequestMethod.GET)
    public List<Book> getBooksByAuthor(@PathVariable("author") String author) {
        return bookService.findByAuthor(author);
    }

    @RequestMapping(value = "/title/{title}/author/{author}", method = RequestMethod.GET)
    public Book getBooksByTitleAndAuthor(@PathVariable("title") String title, @PathVariable("author") String author) {
        return bookService.findByTitleAndAuthor(title, author);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveBook(@RequestBody Book book) {
        bookService.addBook(book);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void saveBook(@PathVariable String id) {
        bookService.deleteBookById(id);
    }
}
