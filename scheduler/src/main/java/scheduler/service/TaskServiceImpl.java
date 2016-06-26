package scheduler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scheduler.domain.Task;
import scheduler.domain.TaskState;
import scheduler.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Abhishek Gupta
 *         https://github.com/abhigupta912
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private final static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> getAllTasks() {
        logger.info("Retrieving all tasks");
        final List<Task> tasks = new ArrayList<>();
        final Iterable<Task> taskIterable = taskRepository.findAll();
        taskIterable.forEach(task -> tasks.add(task));
        logger.info("Retrieved {} tasks", tasks.size());
        return tasks;
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findOne(id);
    }

    @Override
    public List<Task> getTasksByState(String state) {
        logger.info("Retrieving all tasks in state: {}", state);
        final List<Task> tasks = taskRepository.findByState(state);
        logger.info("Retrieved {} tasks", tasks.size());
        return tasks;
    }

    @Override
    public void enqueueTask(Task task) {
        logger.info("Saving task: {}", task);
        taskRepository.save(task);
        logger.info("Saved task: {}", task);
    }

    @Override
    public boolean markTaskForExecution(Long id) {
        logger.info("Marking task with id: {} for execution", id);
        final Task task = taskRepository.findOne(id);
        if (task != null && TaskState.NEW.equalsIgnoreCase(task.getState())) {
            task.setState(TaskState.IN_PRORGRESS);
            taskRepository.save(task);
            logger.info("Task with id: {} marked for execution", id);
            return true;
        }
        logger.error("No new task with id: {} found", id);
        return false;
    }

    @Override
    public void markTaskAsComplete(Long id) {
        logger.info("Marking task with id: {} complete", id);
        final Task task = taskRepository.findOne(id);
        if (task != null && TaskState.IN_PRORGRESS.equalsIgnoreCase(task.getState())) {
            task.setState(TaskState.COMPLETE);
            taskRepository.save(task);
            logger.info("Task with id: {} marked complete", id);
        } else {
            logger.error("No task with id: {} found in progress", id);
        }
    }

    @Override
    public void deleteTaskById(Long id) {
        logger.info("Attempting to delete task with id: {}", id);
        final Task task = taskRepository.findOne(id);
        if (!task.getState().equalsIgnoreCase(TaskState.IN_PRORGRESS)) {
            taskRepository.delete(id);
            logger.info("Task with id {} deleted", id);
        } else {
            logger.info("Unable to delete task: {}", task);
        }
    }
}
