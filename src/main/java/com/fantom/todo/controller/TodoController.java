package com.fantom.todo.controller;

import com.fantom.todo.model.Todo;
import com.fantom.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // GET /api/todos
    @GetMapping
    public List<Todo> listTodos() {
        return todoService.findAll();
    }

    // POST /api/todos
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo createTodo(@RequestBody Todo newTodo) {
        return todoService.create(newTodo);
    }

    // PATCH /api/todos/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<Todo> updateTodoStatus(
            @PathVariable Long id,
            @RequestBody Todo statusUpdate
    ) {
        Optional<Todo> updatedTodo =
                todoService.updateStatus(id, statusUpdate.isCompleted());

        return updatedTodo
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
