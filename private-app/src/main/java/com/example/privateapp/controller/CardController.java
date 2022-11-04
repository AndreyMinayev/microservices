package com.example.privateapp.controller;

import com.example.privateapp.services.CardService;
import com.example.privateapp.services.Consumer;
import com.example.shared.dto.CardDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger log = LoggerFactory.getLogger(CardController.class);

    @GetMapping(value = "/cards/{cardId}")
    public ResponseEntity<CardDTO>  getCardById(@PathVariable("cardId") Long cardId) {
        CardDTO  cardDTO = cardService.getCardById(cardId);
        return new ResponseEntity<>(cardDTO , HttpStatus.OK);
    }

    @GetMapping(value = "/cards")
    public ResponseEntity<List<CardDTO>> getAllCards() {
        List<CardDTO> listOfCardDTO = cardService.getAllCards().stream().map(card -> modelMapper.map(card, CardDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(listOfCardDTO , HttpStatus.OK);
    }

    @PutMapping(value = "/cards")
    public ResponseEntity<CardDTO> updateCard( @RequestBody final  CardDTO cardDTO) {
        CardDTO responseCardDTO = cardService.updateCard(cardDTO);
        return new ResponseEntity<>(responseCardDTO , HttpStatus.OK);
    }

    @PostMapping(value = "/cards")
    public ResponseEntity<List<CardDTO>> createCards( @RequestBody final List<CardDTO> cardDTOList) {
        List<CardDTO> cardDTOListToReturn =cardService.createCards(cardDTOList).stream()
                .map(card -> modelMapper.map(card, CardDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(cardDTOListToReturn , HttpStatus.OK);
    }

    @DeleteMapping(value = "/cards/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable("cardId") Long cardId) {
        cardService.deleteCard(cardId);
        return new ResponseEntity<>("Card was deleted" , HttpStatus.OK);
    }






}
