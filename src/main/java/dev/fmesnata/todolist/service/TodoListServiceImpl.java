package dev.fmesnata.todolist.service;

import dev.fmesnata.todolist.Todo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TodoListServiceImpl implements TodoListService {

    private final Map<UUID, Todo> todos = new LinkedHashMap<>();

    @PostConstruct
    void init() {
        Todo todo1 = new Todo(UUID.randomUUID(), "Créer une application");
        todos.put(todo1.id(), todo1);
        Todo todo2 = new Todo(UUID.randomUUID(), "Faire une démo");
        todos.put(todo2.id(), todo2);
    }

    @Override
    public List<Todo> getTodos() {
        return todos.values().stream().toList();
    }

    @Override
    public Todo addTodo(String todoText) {
        UUID todoId = UUID.randomUUID();
        Todo newTodo = new Todo(todoId, todoText);
        todos.put(todoId, newTodo);
        return newTodo;
    }

    @Override
    public void deleteTodo(UUID todoId) {
        todos.remove(todoId);
    }
}
