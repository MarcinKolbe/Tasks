package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailSchedulerTest {

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AdminConfig adminConfig;

    @InjectMocks
    private EmailScheduler emailScheduler;

    @BeforeEach
    void setUp() {
        when(adminConfig.getAdminMail()).thenReturn("admin@example.com");
    }

    @Test
    void shouldSendEmailWithCorrectMessageForMultipleTasks() {
        // Given
        when(taskRepository.count()).thenReturn(5L);

        // When
        emailScheduler.sendInformationEmail();

        // Then
        ArgumentCaptor<Mail> mailCaptor = ArgumentCaptor.forClass(Mail.class);
        verify(simpleEmailService, times(1)).send(mailCaptor.capture());

        Mail capturedMail = mailCaptor.getValue();
        assertEquals("admin@example.com", capturedMail.getMailTo());
        assertEquals("Tasks: Once a day email", capturedMail.getSubject());
        assertEquals("Currently in database you got: 5 tasks", capturedMail.getMessage());
    }

    @Test
    void shouldSendEmailWithCorrectMessageForSingleTask() {
        // Given
        when(taskRepository.count()).thenReturn(1L);

        // When
        emailScheduler.sendInformationEmail();

        // Then
        ArgumentCaptor<Mail> mailCaptor = ArgumentCaptor.forClass(Mail.class);
        verify(simpleEmailService, times(1)).send(mailCaptor.capture());

        Mail capturedMail = mailCaptor.getValue();
        assertEquals("admin@example.com", capturedMail.getMailTo());
        assertEquals("Tasks: Once a day email", capturedMail.getSubject());
        assertEquals("Currently in database you got: 1 task", capturedMail.getMessage());
    }
}