package scheduler.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import scheduler.domain.Task;
import scheduler.domain.TaskState;
import scheduler.service.TaskService;

import java.util.List;

/**
 * @author Abhishek Gupta
 *         https://github.com/abhigupta912
 */
@Component
public class TaskExecutor {
    private final static Logger logger = LoggerFactory.getLogger(TaskExecutor.class);

    @Autowired
    private TaskService taskService;

    @Scheduled(fixedDelay = 30000)
    public void executeNewTasks() {
        final List<Task> newTasks = taskService.getTasksByState(TaskState.NEW);
        if (!newTasks.isEmpty()) {
            logger.info("Commencing task execution");
            newTasks.parallelStream().forEach(task -> {
                final Long taskId = task.getId();
                final boolean taskReady = taskService.markTaskForExecution(taskId);
                if (taskReady) {
                    logger.info("Executing task with id: {}", taskId);
                    try {
                        Thread.currentThread().sleep(5000);
                    } catch (InterruptedException e) {
                        logger.error("Interruption while executing task with id: {}", taskId, e);
                    } finally {
                        logger.info("Finished executing task with id: {}", taskId);
                        taskService.markTaskAsComplete(taskId);
                    }
                }
            });
        }
    }

    @Scheduled(fixedDelay = 120000)
    public void cleanupCompletedTasks() {
        final List<Task> completedTasks = taskService.getTasksByState(TaskState.COMPLETE);
        if (!completedTasks.isEmpty()) {
            logger.info("Commencing task cleanup");
            completedTasks.parallelStream().forEach(task -> taskService.deleteTaskById(task.getId()));
            logger.info("Finished task cleanup");
        }
    }
}
