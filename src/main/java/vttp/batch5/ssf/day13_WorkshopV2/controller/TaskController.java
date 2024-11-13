package vttp.batch5.ssf.day13_WorkshopV2.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vttp.batch5.ssf.day13_WorkshopV2.services.TaskService;
import vttp.batch5.ssf.day13_WorkshopV2.model.Task;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String showForm(Model model, HttpSession session) {
        model.addAttribute("task", new Task()); // Bind an empty Task object to the form

        // Retrieve the tasks from the session if available, else create a new list
        List<Task> tasks = (List<Task>) session.getAttribute("tasks");
        if (tasks == null) {
            tasks = new ArrayList<>();
            session.setAttribute("tasks", tasks);
        }

        model.addAttribute("tasks", tasks);

        return "index"; // Render the form page
    }

    @PostMapping("/addTask")
    public String addTask(@Valid Task task, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "index"; // Return to form if validation errors
        }

        // Retrieve tasks list from session, or initialize if it doesn’t exist
        List<Task> tasks = (List<Task>) session.getAttribute("tasks");
        if (tasks == null) {
            tasks = new ArrayList<>();
        }

        // Check for due date conflicts
        boolean isConflict = tasks.stream().anyMatch(existingTask -> existingTask.getDueDate().isEqual(task.getDueDate()));
        model.addAttribute("noConflicts", !isConflict);

        if (isConflict) {
            model.addAttribute("conflictMessage", "The due date conflicts with an existing task.");
            model.addAttribute("tasks", tasks);
            return "index";
        }

        // Add the task to the list and update session
        tasks.add(task);
        session.setAttribute("tasks", tasks);

        model.addAttribute("successMessage", "Task added successfully!");
        model.addAttribute("tasks", tasks);
        return "index";
    }

}
