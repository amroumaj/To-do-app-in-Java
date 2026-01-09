package com.example.todoapp.repository;

import com.example.todoapp.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    
    Page<Todo> findByCompleted(boolean completed, Pageable pageable);
    
    @Query("SELECT t FROM Todo t ORDER BY t.createdAt DESC")
    Page<Todo> findAllOrderByCreatedAtDesc(Pageable pageable);
    
    @Query("SELECT t FROM Todo t WHERE t.completed = false ORDER BY t.createdAt DESC")
    Page<Todo> findActiveTodosOrderByCreatedAtDesc(Pageable pageable);
    
    @Query("SELECT t FROM Todo t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')) ORDER BY t.createdAt DESC")
    Page<Todo> findByTitleContaining(@Param("title") String title, Pageable pageable);
    
    @Modifying
    @Query("UPDATE Todo t SET t.completed = :completed WHERE t.id = :id")
    int updateCompletedStatus(@Param("id") Long id, @Param("completed") boolean completed);
}