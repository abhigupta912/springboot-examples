package scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import scheduler.domain.Task;
import scheduler.service.TaskService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Abhishek Gupta
 *         https://github.com/abhigupta912
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public Task getTaskById(@PathVariable("id") final Long id) {
        return taskService.getTaskById(id);
    }

    @RequestMapping(value = "/state/{state}", method = RequestMethod.GET)
    public List<Task> getTaskById(@PathVariable("state") final String state) {
        final List<Task> tasks = new ArrayList<>();
        if (state != null) {
            tasks.addAll(taskService.getTasksByState(state));
        }
        return tasks;
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void enqueueTask(@PathVariable("name") String name) {
        taskService.enqueueTask(new Task(name));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void cancelTask(@PathVariable("id") String id) {
        taskService.deleteTaskById(Long.valueOf(id));
    }
}
