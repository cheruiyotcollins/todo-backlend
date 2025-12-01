package com.fantom.todo.service;

import com.example.todo.model.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TodoService {

    // --- In-memory Storage ---
    private final List<Todo> todos = new ArrayList<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public TodoService() {
        // Initialize with test data
        todos.add(new Todo("Write Java Spring Boot code"));
        todos.get(0).setId(nextId.getAndIncrement());
        todos.get(0).setCompleted(true);

        todos.add(new Todo("Learn more about REST APIs"));
        todos.get(1).setId(nextId.getAndIncrement());
    }
    public List<Todo> findAll() {
        return todos;
    }

    public Todo create(Todo todoRequest) {
        if (todoRequest.getTitle() == null || todoRequest.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }

        todoRequest.setId(nextId.getAndIncrement());
        todoRequest.setCompleted(false);

        todos.add(todoRequest);
        return todoRequest;
    }
    public Optional<Todo> updateStatus(int id, boolean completed) {
        Optional<Todo> todoOptional = todos.stream()
                .filter(t -> t.getId() == id)
                .findFirst();

        if (todoOptional.isPresent()) {
            Todo todo = todoOptional.get();
            todo.setCompleted(completed);
            return Optional.of(todo);
        }

        return Optional.empty();
    }
}