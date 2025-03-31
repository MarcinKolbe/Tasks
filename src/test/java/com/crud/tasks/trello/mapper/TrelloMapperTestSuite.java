package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void MapToBoardsTest() {
        //Given
        List<TrelloBoardDto> trelloBoardsDto = Collections.singletonList(
                new TrelloBoardDto("1", "Test Board 1",
                        List.of(new TrelloListDto("1", "Test list", false))));

        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardsDto);

        //Then
        Assertions.assertEquals(1, trelloBoards.size());
        Assertions.assertEquals("1", trelloBoards.getFirst().getId());
        Assertions.assertEquals("Test Board 1", trelloBoards.getFirst().getName());
        Assertions.assertEquals(1, trelloBoards.getFirst().getLists().size());
        Assertions.assertEquals("1", trelloBoards.getFirst().getLists().getFirst().getId());

    }

    @Test
    public void mapToBoardsDtoTest() {
        //Given
        List<TrelloBoard> trelloBoards = Collections.singletonList(
                new TrelloBoard("1", "Test Board 1",
                        List.of(new TrelloList("1", "Test list", false))));

        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        Assertions.assertEquals(1, trelloBoardsDto.size());
        Assertions.assertEquals("1", trelloBoardsDto.getFirst().getId());
        Assertions.assertEquals("Test Board 1", trelloBoardsDto.getFirst().getName());
        Assertions.assertEquals(1, trelloBoardsDto.getFirst().getLists().size());
        Assertions.assertEquals("1", trelloBoardsDto.getFirst().getLists().getFirst().getId());
    }

    @Test
    public void mapToListTest() {
        //Given
        List<TrelloListDto> trelloListDtos = Collections.singletonList(new TrelloListDto("1", "Test list", false));

        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);

        //Then
        Assertions.assertEquals(1, trelloLists.size());
        Assertions.assertEquals("1", trelloLists.getFirst().getId());
        Assertions.assertEquals("Test list", trelloLists.getFirst().getName());
        Assertions.assertFalse(trelloLists.getFirst().isClosed());
    }

    @Test
    public void mapToListDtoTest() {
        //Given
        List<TrelloList> trelloLists = Collections.singletonList(new TrelloList("1", "Test list", false));

        //When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);

        //Then
        Assertions.assertEquals(1, trelloListDtos.size());
        Assertions.assertEquals("1", trelloListDtos.getFirst().getId());
        Assertions.assertEquals("Test list", trelloListDtos.getFirst().getName());
        Assertions.assertFalse(trelloListDtos.getFirst().isClosed());
    }

    @Test
    public void mapToCardTest() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test Card", "Test description", "top", "1");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        Assertions.assertEquals("Test Card", trelloCard.getName());
        Assertions.assertEquals("Test description", trelloCard.getDescription());
        Assertions.assertEquals("top", trelloCard.getPos());
        Assertions.assertEquals("1", trelloCard.getListId());
    }

    @Test
    public void mapToCardDtoTest() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Test Card", "Test description", "top", "1");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        Assertions.assertEquals("Test Card", trelloCardDto.getName());
        Assertions.assertEquals("Test description", trelloCardDto.getDescription());
        Assertions.assertEquals("top", trelloCardDto.getPos());
        Assertions.assertEquals("1", trelloCardDto.getListId());
    }
}
