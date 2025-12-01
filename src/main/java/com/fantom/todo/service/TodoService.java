package com.fantom.todo.service;

import com.fantom.todo.model.Todo;
import com.fantom.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // GET all todos
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    // CREATE todo
    public Todo create(Todo todoRequest) {
        if (todoRequest.getTitle() == null || todoRequest.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }

        todoRequest.setId(null);       // let DB generate
        todoRequest.setCompleted(false); // default value

        return todoRepository.save(todoRequest);
    }

    // UPDATE status (toggle)
    public Optional<Todo> updateStatus(Long id, boolean completed) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);

        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            todo.setCompleted(completed);
            todoRepository.save(todo);
        }

        return optionalTodo;
    }
}
