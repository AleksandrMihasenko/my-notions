package com.aleksandrm.mynotions.controller;

import com.aleksandrm.mynotions.todo.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.aleksandrm.mynotions.repository.TodoRepository;

import java.util.List;

@RestController
@RequestMapping("/")
public class TodoController {
    private final TodoRepository todoRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public List<Todo> getTodos() {
        return todoRepository.getTodos();
    }
}
