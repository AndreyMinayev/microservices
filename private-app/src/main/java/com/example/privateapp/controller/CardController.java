package com.example.privateapp.controller;



import com.example.privateapp.entity.Card;
import com.example.privateapp.exceptions.CardNotFoundException;
import com.example.privateapp.facade.CardFacade;
import com.example.privateapp.services.CardService;
import com.example.shared.dto.CardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private CardFacade cardFacade;


    @GetMapping(value = "/cards/{cardNumber}")
    public ResponseEntity getCardByNumber(@PathVariable("cardNumber") Long cardNumber) {
        Card card = cardService.getCardByNumber(cardNumber);
        CardDTO  cardDTO = cardFacade.cardToCardDTO(card);
        return new ResponseEntity<>(cardDTO , HttpStatus.OK);
    }

    @GetMapping(value = "/cards")
    public ResponseEntity<List<CardDTO>> getAllCards() {
        List<CardDTO> listOfCardDTO = cardService.getAllCards().stream().map(cardFacade::cardToCardDTO).collect(Collectors.toList());
        return new ResponseEntity<>(listOfCardDTO , HttpStatus.OK);
    }

    @PostMapping(value = "/cards")
    public ResponseEntity<Card> createOrUpdateCard( @RequestBody  CardDTO cardDTO) {
        Card card = cardService.createOrUpdateCard(cardDTO);
        return new ResponseEntity<>(card , HttpStatus.OK);
    }

    @PutMapping(value = "/cards")
    public ResponseEntity<List<CardDTO>> createOrUpdateCardMultiple( @RequestBody  List<CardDTO> cardDTOList) {
        List<CardDTO> cardDTOListToReturn =cardService.createOrUpdateMultipleCards(cardDTOList).stream()
                .map(cardFacade::cardToCardDTO).collect(Collectors.toList());
        return new ResponseEntity<>(cardDTOListToReturn , HttpStatus.OK);
    }

    @DeleteMapping(value = "/cards/{cardNumber}")
    public ResponseEntity<String> deleteCard(@PathVariable("cardNumber") Long cardNumber) {
        cardService.deleteCardByNumber(cardNumber);
        return new ResponseEntity<>("Card was deleted" , HttpStatus.OK);
    }






}
