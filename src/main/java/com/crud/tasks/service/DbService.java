package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DbService {
    private final TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTaskById(long id) throws TaskNotFoundException {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
    }

    public Task saveTask(final Task task) {
        return repository.save(task);
    }

    public void deleteTaskById(Long id) throws TaskNotFoundException {
        Task task = repository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
        repository.deleteById(task.getId());
    }
}
