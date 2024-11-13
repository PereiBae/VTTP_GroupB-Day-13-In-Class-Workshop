package vttp.batch5.ssf.day13_WorkshopV2.model;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Task {

    @NotNull(message = "Task name is required")
    @Size(min = 3, max = 64, message = "Name must be between 3 and 64 characters")
    private String taskName;

    @NotNull(message = "Priority is required")
    @Size(min = 3, max = 6, message = "Priority must be 'low', 'medium', or 'high'")
    private String priority;

    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date must be today or in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDate;

    public Task() {}

    public Task(String taskName, String priority, LocalDate dueDate) {
        this.taskName = taskName;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public @NotNull(message = "Date of birth is required") @FutureOrPresent(message = "Due date must be today or in the future") LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(@NotNull(message = "Date of birth is required") @FutureOrPresent(message = "Due date must be today or in the future") LocalDate dueDate) {
        this.dueDate = dueDate;
    }

}


