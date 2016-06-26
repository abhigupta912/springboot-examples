package restapi.repository;

import org.springframework.data.repository.CrudRepository;
import restapi.domain.Book;

import java.util.List;

/**
 * @author Abhishek Gupta
 *         https://github.com/abhigupta912
 */
public interface BookRepository extends CrudRepository<Book, String> {
    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);

    Book findByTitleAndAuthor(String title, String author);
}
