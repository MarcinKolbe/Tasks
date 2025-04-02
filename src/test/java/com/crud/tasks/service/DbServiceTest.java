package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DbServiceTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private DbService dbService;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task(1L, "Test Task", "Test Description");
    }

    @Test
    void shouldGetAllTasks() {
        // Given
        List<Task> tasks = List.of(task);
        when(repository.findAll()).thenReturn(tasks);

        // When
        List<Task> result = dbService.getAllTasks();

        // Then
        assertEquals(1, result.size());
        assertEquals("Test Task", result.getFirst().getTitle());
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldGetTaskById() throws TaskNotFoundException {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.of(task));

        // When
        Task result = dbService.getTaskById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(TaskNotFoundException.class, () -> dbService.getTaskById(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void shouldSaveTask() {
        // Given
        when(repository.save(task)).thenReturn(task);

        // When
        Task result = dbService.saveTask(task);

        // Then
        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        verify(repository, times(1)).save(task);
    }

    @Test
    void shouldDeleteTaskById() throws TaskNotFoundException {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(repository).deleteById(1L);

        // When
        dbService.deleteTaskById(1L);

        // Then
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentTask() {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(TaskNotFoundException.class, () -> dbService.deleteTaskById(1L));
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).deleteById(anyLong());
    }
}