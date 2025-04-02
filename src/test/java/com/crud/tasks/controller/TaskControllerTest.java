package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.facade.TaskFacade;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskFacade taskFacade;

    @Test
    void shouldGetAllTasks() throws Exception {
        //Given
        List<TaskDto> taskDtoList = List.of(new TaskDto(1L, "Test Task", "Test Description"));

        when(taskFacade.getAllTasks()).thenReturn(taskDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));

    }

    @Test
    void shouldGetEmptyTaskList() throws Exception {
        //Given
        when(taskFacade.getAllTasks()).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldGetTaskById() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test Task", "Test Description");

        when(taskFacade.getTaskById(taskDto.getId())).thenReturn(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test Task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Test Description")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test Task", "Test Description");

        doNothing().when(taskFacade).deleteTask(taskDto.getId());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                .delete("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(taskFacade, times(1)).deleteTask(taskDto.getId());
    }

    @Test
    void shouldUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test Task", "Test Description");
        TaskDto updatedTaskDto = new TaskDto(1L, "Updated Task", "Updated Description");

        when(taskFacade.updateTask(any(TaskDto.class))).thenReturn(updatedTaskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updatedTaskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                    .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Updated Task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Updated Description")));
    }

    @Test
    void shouldCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test Task", "Test Description");

        doNothing().when(taskFacade).createTask(any(TaskDto.class));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(taskFacade, times(1)).createTask(any(TaskDto.class));
    }

}