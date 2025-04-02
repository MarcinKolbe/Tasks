package com.crud.tasks.facade;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskFacade {

    private final DbService service;
    private final TaskMapper taskMapper;

    public List<TaskDto> getAllTasks() {
        List<Task> tasks = service.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }

    public TaskDto getTaskById(Long taskId) throws TaskNotFoundException {
        return taskMapper.mapToTaskDto(service.getTaskById(taskId));
    }

    public void deleteTask(Long taskId) throws TaskNotFoundException {
            service.deleteTaskById(taskId);
    }

    public TaskDto updateTask(TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        Task savedTask = service.saveTask(task);
        return taskMapper.mapToTaskDto(savedTask);
    }

    public void createTask(TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        service.saveTask(task);
    }
}
