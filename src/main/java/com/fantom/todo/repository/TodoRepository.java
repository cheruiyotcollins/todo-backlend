package com.fantom.todo.repository;

import com.fantom.todo.model.Todo;
import java.util.List;
import java.util.Optional;


public interface TodoRepository {

    /**
     * Retrieves all todo items.
     * @return A list of all todos.
     */
    List<Todo> findAll();

    /**
     * Saves a new Todo item or updates an existing one.
     * In this in-memory context, it handles assignment of a new ID if needed.
     * @param todo The todo object to save.
     * @return The saved Todo object.
     */
    Todo save(Todo todo);

    /**
     * Finds a Todo item by its unique ID.
     * @param id The ID of the todo item.
     * @return An Optional containing the Todo item, or Optional.empty() if not found.
     */
    Optional<Todo> findById(int id);
}