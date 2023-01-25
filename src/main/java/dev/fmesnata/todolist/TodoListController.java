package dev.fmesnata.todolist;

import dev.fmesnata.todolist.service.TodoListService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
public class TodoListController {

    private final TodoListService todoListService;

    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @GetMapping(path = {"/", "/todolist"})
    public ModelAndView todoList() {
        ModelAndView view = new ModelAndView("todolist");
        view.addObject("todos", todoListService.getTodos());
        return view;
    }

    @PostMapping(path = {"/todolist/todo"})
    public ModelAndView addTodo(String text) {
        Todo newTodo = todoListService.addTodo(text);
        return new ModelAndView("redirect:/todolist");
    }

    @DeleteMapping(path = {"/todolist/todo/{todoId}"})
    @ResponseBody
    public void deleteTodo(@PathVariable UUID todoId) {
        todoListService.deleteTodo(todoId);
    }
}
