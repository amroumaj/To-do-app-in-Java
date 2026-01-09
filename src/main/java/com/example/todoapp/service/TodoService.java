package com.example.todoapp.service;

import com.example.todoapp.exception.ResourceNotFoundException;
import com.example.todoapp.model.Todo;
import com.example.todoapp.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    
    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);
    
    private final TodoRepository todoRepository;
    
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    
    public List<Todo> getAllTodos() {
        logger.info("Fetching all todos");
        return todoRepository.findAllOrderByCreatedAtDesc(null).getContent();
    }
    
    public List<Todo> getActiveTodos() {
        logger.info("Fetching active todos");
        return todoRepository.findActiveTodosOrderByCreatedAtDesc(null).getContent();
    }
    
    public List<Todo> getCompletedTodos() {
        logger.info("Fetching completed todos");
        return todoRepository.findByCompleted(true, null).getContent();
    }
    
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }
    
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }
    
    public Todo updateTodo(Long id, Todo todoDetails) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setTitle(todoDetails.getTitle());
                    todo.setDescription(todoDetails.getDescription());
                    todo.setCompleted(todoDetails.isCompleted());
                    return todoRepository.save(todo);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
    }
    
    public Todo toggleTodoCompletion(Long id) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setCompleted(!todo.isCompleted());
                    return todoRepository.save(todo);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
    }
    
    public boolean deleteTodo(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}