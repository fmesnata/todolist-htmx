package dev.fmesnata.todolist.service;

import dev.fmesnata.todolist.Todo;

import java.util.List;
import java.util.UUID;

public interface TodoListService {

    List<Todo> getTodos();
    Todo addTodo(String todoText);
    void deleteTodo(UUID todoId);
}
