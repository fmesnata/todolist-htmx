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

    @GetMapping(path = {"/", "/todolist"}, headers = "HX-Request")
    public ModelAndView todoListFragment() {
        ModelAndView modelAndView = new ModelAndView("fragments/todolist-fragment");
        modelAndView.addObject("todos", todoListService.getTodos());
        return modelAndView;
    }

    @GetMapping(path = "/todolist-polling")
    public ModelAndView todoListPoll() {
        ModelAndView modelAndView = new ModelAndView("fragments/todolist-polling-fragment");
        modelAndView.addObject("todos", todoListService.getTodos());
        return modelAndView;
    }

    @PostMapping(path = {"/todolist/todo"})
    public ModelAndView addTodo(String text) {
        Todo newTodo = todoListService.addTodo(text);
        ModelAndView modelAndView = new ModelAndView("fragments/todo");
        modelAndView.addObject("newTodo", newTodo);
        return modelAndView;
    }

    @DeleteMapping(path = {"/todolist/todo/{todoId}"})
    @ResponseBody
    public void deleteTodo(@PathVariable UUID todoId) {
        todoListService.deleteTodo(todoId);
    }
}
