package com.example.todoapp.repository;

import com.example.todoapp.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    
    List<Todo> findByCompleted(boolean completed);
    
    @Query("SELECT t FROM Todo t ORDER BY t.createdAt DESC")
    List<Todo> findAllOrderByCreatedAtDesc();
    
    @Query("SELECT t FROM Todo t WHERE t.completed = false ORDER BY t.createdAt DESC")
    List<Todo> findActiveTodosOrderByCreatedAtDesc();
}