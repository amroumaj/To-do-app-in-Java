package com.example.todoapp.mapper;

import com.example.todoapp.dto.TodoDto;
import com.example.todoapp.model.Todo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoMapper {
    
    public TodoDto toDto(Todo todo) {
        return new TodoDto(
            todo.getId(),
            todo.getTitle(),
            todo.getDescription(),
            todo.isCompleted(),
            todo.getCreatedAt(),
            todo.getUpdatedAt()
        );
    }
    
    public Todo toEntity(TodoDto todoDto) {
        Todo todo = new Todo();
        todo.setId(todoDto.getId());
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());
        todo.setCreatedAt(todoDto.getCreatedAt());
        todo.setUpdatedAt(todoDto.getUpdatedAt());
        return todo;
    }
    
    public List<TodoDto> toDtoList(List<Todo> todos) {
        return todos.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}