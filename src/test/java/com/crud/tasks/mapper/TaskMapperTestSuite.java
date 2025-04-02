package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TaskMapperTestSuite {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void mapToTaskTest() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test task", "Test description");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        Assertions.assertNotNull(task);
        Assertions.assertEquals(1, task.getId());
        Assertions.assertEquals("Test task", task.getTitle());
        Assertions.assertEquals("Test description", task.getContent());
    }

    @Test
    public void mapToTaskDtoTest() {
        //Given
        Task task = new Task(1L, "Test task", "Test description");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        Assertions.assertNotNull(taskDto);
        Assertions.assertEquals(1, taskDto.getId());
        Assertions.assertEquals("Test task", taskDto.getTitle());
        Assertions.assertEquals("Test description", taskDto.getContent());
    }

    @Test
    public void mapToTaskDtoListTest() {
        //Given
        List<Task> taskList = List.of(new Task(1L, "Test task", "Test description"));

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        Assertions.assertNotNull(taskDtoList);
        Assertions.assertEquals(1, taskDtoList.size());
    }
}
