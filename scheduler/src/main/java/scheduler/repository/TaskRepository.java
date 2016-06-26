package scheduler.repository;

import org.springframework.data.repository.CrudRepository;
import scheduler.domain.Task;

import java.util.List;

/**
 * @author Abhishek Gupta
 *         https://github.com/abhigupta912
 */
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByState(String state);
}
