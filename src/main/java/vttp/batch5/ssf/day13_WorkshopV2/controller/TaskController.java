package vttp.batch5.ssf.day13_WorkshopV2.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vttp.batch5.ssf.day13_WorkshopV2.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class TaskController {

    // Get the logger. The logger's name is the class name
    private final Logger logger = Logger.getLogger(TaskController.class.getName());

    @GetMapping("/")
    public String showTaskForm(Model model) {
        logger.info("Showing task form");
        model.addAttribute("task", new Task());
        return "index";
    }

    @PostMapping("/addTask")
    public String addTask(@Valid Task task, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            logger.warning("Error in form submitted");
            return "index";
        }

        // Retrieve the tasks list from session or create a new list if it doesn’t exist
        List<Task> tasks = (List<Task>) session.getAttribute("tasks");
        if (tasks == null) {
            tasks = new ArrayList<>();
            session.setAttribute("tasks", tasks);
        }

        // Check for conflicts
        boolean hasConflict = tasks.stream().anyMatch(t -> t.getDueDate().isEqual(task.getDueDate()));

        if (!hasConflict) {
            model.addAttribute("noConflicts", true);
            model.addAttribute("noConflictMessage", "Looks like you are free!");
            session.setAttribute("taskToSave", task); // Temporarily store the task in session
        } else {
            model.addAttribute("conflictMessage", "A task is already scheduled for this date.");
            session.setAttribute("taskToSave", task); // Temporarily store the task in session
        }

        model.addAttribute("tasks", tasks);
        return "index";
    }

    @PostMapping("/saveTask")
    public String saveTask(HttpSession session, Model model) {
        // Retrieve the task to save and the tasks list from session
        Task taskToSave = (Task) session.getAttribute("taskToSave");
        List<Task> tasks = (List<Task>) session.getAttribute("tasks");

        // Add the task and update session
        if (taskToSave != null) {
            tasks.add(taskToSave);
            session.setAttribute("tasks", tasks);
            session.removeAttribute("taskToSave"); // Remove temporary task after saving
        }

        model.addAttribute("successMessage", "Task saved successfully!");
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/clear")
    public String clearTasks(HttpSession session) {
        session.removeAttribute("tasks"); // Clear tasks list from session
        return "redirect:/";
    }

}
