package com.crud.tasks.facade;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TaskFacadeTest {

    @InjectMocks
    private TaskFacade taskFacade;

    @Mock
    private DbService dbService;

    @Mock
    private TaskMapper taskMapper;

    @Test
    void shouldGetEmptyTaskList() {
        //Given
        List<Task> taskList = List.of(new Task(1L, "Test Task", "Test Description"));

        when(dbService.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(List.of());

        //When
        List<TaskDto> listTaskDtos = taskFacade.getAllTasks();

        //Then
        Assertions.assertNotNull(listTaskDtos);
        Assertions.assertEquals(0, listTaskDtos.size());
    }

    @Test
    void shouldGetAllTasks() {
        //Given
        List<Task> taskList = List.of(new Task(1L, "Test Task", "Test Description"));
        List<TaskDto> taskDtoList = List.of(new TaskDto(1L, "Test Task", "Test Description"));

        when(dbService.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(taskDtoList);

        //When
        List<TaskDto> listTaskDtos = taskFacade.getAllTasks();

        //Then
        Assertions.assertNotNull(listTaskDtos);
        Assertions.assertEquals(1, listTaskDtos.size());
    }

    @Test
    void shouldGetTaskById() throws TaskNotFoundException {
        //Given
        Task task = new Task(1L, "Test Task", "Test Description");
        TaskDto taskDto = new TaskDto(1L, "Test Task", "Test Description");

        when(dbService.getTaskById(task.getId())).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When
        TaskDto taskDtoFromDb = taskFacade.getTaskById(task.getId());

        //Then
        Assertions.assertEquals(1, taskDtoFromDb.getId());
        Assertions.assertEquals("Test Task", taskDtoFromDb.getTitle());
        Assertions.assertEquals("Test Description", taskDtoFromDb.getContent());
    }

    @Test
    void shouldDeleteTask() throws TaskNotFoundException {
        //Given
        Task task = new Task(1L, "Test Task", "Test Description");

        doNothing().when(dbService).deleteTaskById(task.getId());

        //When
        taskFacade.deleteTask(task.getId());

        //Then
        verify(dbService, times(1)).deleteTaskById(task.getId());
    }

    @Test
    void shouldUpdateTask() throws TaskNotFoundException {
        //Given
        Task task = new Task(1L, "Updated Task", "Updated Description");
        TaskDto taskDto = new TaskDto(1L, "Updated Task", "Updated Description");

        when(dbService.saveTask(any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);

        //When
        TaskDto updatedTaskDto = taskFacade.updateTask(taskDto);

        //Then
        Assertions.assertEquals(1, updatedTaskDto.getId());
        Assertions.assertEquals("Updated Task", updatedTaskDto.getTitle());
        Assertions.assertEquals("Updated Description", updatedTaskDto.getContent());
    }

    @Test
    void shouldCreateTask() throws TaskNotFoundException {
        //Given
        Task task = new Task(1L, "Test Task", "Test Description");
        TaskDto taskDto = new TaskDto(1L, "Test Task", "Test Description");

        when(dbService.saveTask(any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);

        //When
        taskFacade.createTask(taskDto);

        //Then
        verify(dbService, times(1)).saveTask(task);
    }

}