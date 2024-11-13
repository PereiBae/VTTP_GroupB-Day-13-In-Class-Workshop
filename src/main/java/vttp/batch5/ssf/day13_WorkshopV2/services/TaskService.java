package vttp.batch5.ssf.day13_WorkshopV2.services;

import org.springframework.stereotype.Service;
import vttp.batch5.ssf.day13_WorkshopV2.model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();

    // Check if any existing task has the same due date
    public boolean isConflict(LocalDate dueDate) {
        for (Task task : tasks) {
            if (task.getDueDate().isEqual(dueDate)) {
                return true; // Conflict found
            }
        }
        return false; // No conflicts
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }
}
