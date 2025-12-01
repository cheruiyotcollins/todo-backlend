package com.fantom.todo.controller;

import com.fantom.todo.model.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/todos")
// Required to allow the frontend (running on a different port/protocol) to communicate with the backend.
@CrossOrigin(origins = "*")
public class TodoController {

    // --- In-memory Storage (Requirement 3) ---
    private final List<Todo> todos = new ArrayList<>();
    // Thread-safe counter for generating unique IDs (Requirement 1)
    private final AtomicInteger nextId = new AtomicInteger(1);

    public TodoController() {
        // Add initial data for easy testing
        todos.add(new Todo("Write Java Spring Boot code"));
        todos.get(0).setId(nextId.getAndIncrement());
        todos.get(0).setCompleted(true);

        todos.add(new Todo("Learn more about REST APIs"));
        todos.get(1).setId(nextId.getAndIncrement());
    }

    // 2. Implement an endpoint to list all todo items: GET /api/todos
    @GetMapping
    public List<Todo> listTodos() {
        // Returns Response JSON: array of todos (handled automatically by Spring)
        return todos;
    }

    // 1. Implement an endpoint to create a todo item: POST /api/todos
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Returns HTTP 201 Created
    public Todo createTodo(@RequestBody Todo newTodoRequest) {
        // Request JSON: {"title": "Buy milk"} is mandatory per requirement

        if (newTodoRequest.getTitle() == null || newTodoRequest.getTitle().trim().isEmpty()) {
            // Handle error case for missing 'title'
            throw new IllegalArgumentException("Title cannot be empty.");
        }

        // Assign unique ID and ensure default completed=false
        newTodoRequest.setId(nextId.getAndIncrement());
        newTodoRequest.setCompleted(false);

        todos.add(newTodoRequest);

        // Response JSON: todo created with id, title, completed (handled automatically)
        return newTodoRequest;
    }

    // 4. Bonus: Add an endpoint to toggle completed status: PATCH /api/todos/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<Todo> updateTodoStatus(@PathVariable int id, @RequestBody Todo statusUpdate) {
        Optional<Todo> todoOptional = todos.stream()
                .filter(t -> t.getId() == id)
                .findFirst();

        if (todoOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        Todo todo = todoOptional.get();

        // Check if the request body contains the boolean 'completed' field
        if (statusUpdate.isCompleted() == todo.isCompleted() && statusUpdate.getTitle() == null) {
            // Simple way to check if only the default value was passed, or if the status is unchanged.
            // In a real app, we'd check if the field was explicitly included in the request JSON.
        }

        // Only update the 'completed' status if it was present in the request body
        // Note: Spring automatically maps JSON to the Todo object, even if only 'completed' is provided.
        todo.setCompleted(statusUpdate.isCompleted());

        return ResponseEntity.ok(todo);
    }
}