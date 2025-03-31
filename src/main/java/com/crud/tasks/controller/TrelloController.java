package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.facade.TrelloFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/trello")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TrelloController {

    private final TrelloFacade trelloFacade;

    @GetMapping("boards")
    public ResponseEntity<List<TrelloBoardDto>> getTrelloBoards() {
        return ResponseEntity.ok(trelloFacade.fetchTrelloBoards());

//        trelloBoards.stream()
//                .filter(b -> b.getId() != null && b.getName() != null)
//                .filter(board -> board.getName().contains("Kodilla"))
//                .forEach(board -> {
//                    System.out.println(board.getId() + " - " + board.getName());
//                    System.out.println("This board contains lists:");
//                    board.getLists().forEach(l -> System.out.println(l.getName() + " - " + l.getId() + " - " + l.isClosed()));
//                });
    }

    @PostMapping("cards")
    public ResponseEntity<CreatedTrelloCardDto> createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return ResponseEntity.ok(trelloFacade.createCard(trelloCardDto));
    }
}
