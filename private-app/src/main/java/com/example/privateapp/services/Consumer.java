package com.example.privateapp.services;

import com.example.privateapp.entity.Card;
import com.example.privateapp.facade.CardFacade;
import com.example.shared.dto.CardDTO;
import com.example.shared.dto.RequestDTO;
import com.example.shared.dto.ResponseDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class Consumer {

    @Autowired
    private CardService cardService;

    @Autowired
    private CardFacade cardFacade;

    @RabbitListener(queues =  "${cards.rabbitmq.requestQueue}", concurrency = "3")
    public ResponseDTO receive(@Payload RequestDTO request){
        if(request.type().equals("cardNumber")){
            Card card = cardService.getCardByNumber(Long.parseLong(request.value()));
            return ResponseDTO.builder().result(List.of(cardFacade.cardToCardDTO(card))).build();
        }
        if(request.type().equals("all")){
            List<CardDTO> cardDTOList = cardService.getAllCards().stream().map(cardFacade::cardToCardDTO).collect(Collectors.toList());
            return ResponseDTO.builder().result(cardDTOList).build();
        }
        return ResponseDTO.builder().error("Request Type error").build();
    }

    @RabbitListener(queues =  "${cards.rabbitmq.newCardQueue}", concurrency = "3")
    public ResponseDTO receive(@Payload CardDTO cardDTO){
        System.out.println("card received !! "+ cardDTO);
        Card card = cardService.createOrUpdateCard(cardDTO);
        return ResponseDTO.builder().result(List.of(cardFacade.cardToCardDTO(card))).build();
    }

    @RabbitListener(queues =  "${cards.rabbitmq.deleteQueue}", concurrency = "3")
    public String receive(Long cardNumber){
        System.out.println("number to delete received !! "+ cardNumber);
        cardService.deleteCardByNumber(cardNumber);
        return "card " + cardNumber + " successfully deleted";
    }
}
