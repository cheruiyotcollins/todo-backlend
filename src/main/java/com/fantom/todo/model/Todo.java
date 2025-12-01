package com.example.todo.model;
package com.fantom.todo.model;
public class Todo {
    private int id;
    private String title;
    private boolean completed;

    // Constructor used for creating new todos (ID is assigned later)
    public Todo(String title) {
        this.title = title;
        this.completed = false; // Default false is mandatory per requirement
    }

    // Default constructor for deserialization (e.g., when receiving PATCH request)
    public Todo() {
    }

    // Getters and Setters (required by Spring for JSON serialization/deserialization)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}