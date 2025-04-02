package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrelloServiceTest {

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @InjectMocks
    private TrelloService trelloService;

    @Test
    void shouldFetchTrelloBoards() {
        // Given
        List<TrelloBoardDto> mockBoards = List.of(new TrelloBoardDto("1", "Test Board", List.of()));
        when(trelloClient.getTrelloBoards()).thenReturn(mockBoards);

        // When
        List<TrelloBoardDto> result = trelloService.fetchTrelloBoards();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Board", result.get(0).getName());
    }

    @Test
    void shouldCreateTrelloCardAndSendEmail() {
        // Given
        TrelloCardDto cardDto = new TrelloCardDto("Test Card", "Description", "top", "1");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto("1", "Test Card", "http://test.com");

        when(trelloClient.createNewCard(cardDto)).thenReturn(createdCard);
        when(adminConfig.getAdminMail()).thenReturn("admin@test.com");

        // When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(cardDto);

        // Then
        assertNotNull(result);
        assertEquals("Test Card", result.getName());
        verify(emailService, times(1)).send(any(Mail.class));
    }

    @Test
    void shouldNotSendEmailWhenCardNotCreated() {
        // Given
        TrelloCardDto cardDto = new TrelloCardDto("Test Card", "Description", "top", "1");

        when(trelloClient.createNewCard(cardDto)).thenReturn(null);

        // When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(cardDto);

        // Then
        assertNull(result);
        verify(emailService, never()).send(any(Mail.class));
    }
}