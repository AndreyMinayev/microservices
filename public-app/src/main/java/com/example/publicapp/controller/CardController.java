package com.example.publicapp.controller;



import com.example.publicapp.service.RabbitMQSender;
import com.example.shared.dto.CardDTO;
import com.example.shared.dto.CardRequest;
import com.example.shared.response.cards.CardCreateResponse;
import com.example.shared.response.cards.CardListResponse;
import com.example.shared.response.cards.CardUpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api/")
public class CardController {

    @Autowired
    RabbitMQSender rabbitMQSender;

    @GetMapping(value = "/cards/{cardId}")
    public CardListResponse getCardById(@PathVariable("cardId") String cardId)  throws Exception{
        return rabbitMQSender.sendRequest(CardRequest.builder().type("id").value(cardId).build());
    }

    @GetMapping(value = "/cards")
    public CardListResponse getAllCards() throws Exception{
        return rabbitMQSender.sendRequest(CardRequest.builder().type("all").build());
    }

    @PostMapping(value = "/cards")
    public CardCreateResponse createCards(@RequestBody List<CardDTO> cards) throws Exception{
        return  rabbitMQSender.createCard(cards);
    }

    @PutMapping(value = "/cards")
    public CardUpdateResponse updateCard(@RequestBody CardDTO card) throws Exception{
        return  rabbitMQSender.updateCard(card);
    }


    @DeleteMapping(value = "/cards/{cardId}")
    public CardListResponse deleteCard(@PathVariable("cardId") Long cardId) throws Exception {
        return rabbitMQSender.deleteCard(cardId);
    }




}
