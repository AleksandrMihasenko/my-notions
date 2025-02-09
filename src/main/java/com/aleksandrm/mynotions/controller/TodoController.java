package com.aleksandrm.mynotions.controller;

import model.Todo;
import org.springframework.web.bind.annotation.*;
import repository.TodoRepository;

import java.util.List;

@RestController
@RequestMapping("/")
public class TodoController {
    private final TodoRepository todoRepository;

    public TodoController() {
        todoRepository = new TodoRepository();
    }

    @GetMapping
    public List<Todo> getTodos() {
        return todoRepository.getTodos();
    }
}
