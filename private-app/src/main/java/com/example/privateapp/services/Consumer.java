package com.example.privateapp.services;

import com.example.shared.dto.CardDTO;
import com.example.shared.dto.CardRequest;
import com.example.shared.enums.CardOperation;
import com.example.shared.response.ResourceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;


import java.util.Collections;
import java.util.List;

@Component
public class Consumer {

    @Autowired
    private CardService cardService;

    @Autowired
    private CardQueueDecryptService cardQueueDecryptService;


    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues =  "${queue.name}", concurrency = "3")
    public ResourceResponse<List<CardDTO>> receive(@Payload byte[]  request, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) final String routingKey )  {
        log.info("received {} message", routingKey);
        try{
            return performCardOperation(request, CardOperation.from(routingKey));
        }catch(Exception e){
            log.error("Failed to process message {}, exception {}", routingKey, e );
            return ResourceResponse.failure(e.getMessage());

        }
    }

    private ResourceResponse<List<CardDTO>> performCardOperation(byte[]  request, CardOperation cardOperation) throws Exception {
        switch (cardOperation) {
            case CREATE:
                return ResourceResponse.success(createCards(request));
            case LIST:
                return ResourceResponse.success(listCards(request));
            case DELETE:
                return ResourceResponse.success(deleteCard(request));
            case UPDATE:
                return ResourceResponse.success(updateCard(request));
            default:
                throw new IllegalStateException("Unexpected operation '" + cardOperation + "'");
        }
    }
    private List<CardDTO> deleteCard(final byte[]  request) throws Exception {
        final Long cardId =  cardQueueDecryptService.decryptId(request);
        return Collections.singletonList(cardService.deleteCard(cardId));
    }

    private List<CardDTO> createCards(final byte[]  request) throws Exception {
        final List<CardDTO> cardDTOList = cardQueueDecryptService.decryptCardData(request);
        log.info("crate request {}", cardDTOList);
        return cardService.createCards(cardDTOList);
    }

    private List<CardDTO> updateCard(final byte[]  request) throws Exception {
        final CardDTO cardDTO = cardQueueDecryptService.decryptSingleCardData(request);
        log.info("update request {}", cardDTO);
        return Collections.singletonList(cardService.updateCard(cardDTO));
    }

    private List<CardDTO> listCards(final byte[]  request) throws Exception {
        final CardRequest cardRequest =  cardQueueDecryptService.decryptCardRequest(request);
        log.info("list request {}", cardRequest);
        if(cardRequest.type().equals("id")){
            Long cardId= Long.parseLong(cardRequest.value());
            log.info("cardService.getCardById(cardId) {}", cardService.getCardById(cardId));
            return Collections.singletonList(cardService.getCardById(cardId));
        }else {
            return cardService.getAllCards();
        }
    }

}
