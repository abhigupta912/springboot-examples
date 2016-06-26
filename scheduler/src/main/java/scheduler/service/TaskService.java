package scheduler.service;

import scheduler.domain.Task;

import java.util.List;

/**
 * @author Abhishek Gupta
 *         https://github.com/abhigupta912
 */
public interface TaskService {
    List<Task> getAllTasks();

    Task getTaskById(Long id);

    List<Task> getTasksByState(String state);

    void enqueueTask(Task task);

    boolean markTaskForExecution(Long id);

    void markTaskAsComplete(Long id);

    void deleteTaskById(Long id);
}
