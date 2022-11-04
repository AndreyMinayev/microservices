package com.example.publicapp.service;

import com.example.publicapp.CardResponseType;
import com.example.publicapp.encrypt.EncryptService;
import com.example.shared.dto.CardDTO;
import com.example.shared.dto.CardRequest;
import com.example.shared.enums.CardOperation;
import com.example.shared.response.ResourceResponse;
import com.example.shared.response.cards.CardCreateResponse;
import com.example.shared.response.cards.CardListResponse;
import com.example.shared.response.cards.CardUpdateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    @Autowired
    private EncryptService encryptService;

    private static final Logger log = LoggerFactory.getLogger(RabbitMQSender.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CardListResponse sendRequest(CardRequest request) throws Exception {
        CardListResponse response = rabbitTemplate.convertSendAndReceiveAsType(directExchange.getName(), CardOperation.LIST.getId(), encryptRequest(request), CardResponseType.list());
        return validateResponse(response);
    }

    public CardUpdateResponse updateCard(CardDTO card) throws Exception{
        CardUpdateResponse response = rabbitTemplate.convertSendAndReceiveAsType(directExchange.getName(), CardOperation.UPDATE.getId(), encryptRequest(card), CardResponseType.update());
        return validateResponse(response);
    }

    public CardCreateResponse createCard(List<CardDTO> cards) throws Exception{
        CardCreateResponse response = rabbitTemplate.convertSendAndReceiveAsType(directExchange.getName(), CardOperation.CREATE.getId(), encryptRequest(cards), CardResponseType.create());
        return validateResponse(response);
    }

    public CardListResponse deleteCard(Long cardId) throws Exception {
        CardListResponse response = rabbitTemplate.convertSendAndReceiveAsType(directExchange.getName(), CardOperation.DELETE.getId(), encryptRequest(cardId), CardResponseType.list());
        return validateResponse(response);
    }

    private  byte[] encryptRequest(Object requestObject) throws Exception {
        log.info("Object for sending {}", requestObject);
        final String message = objectMapper.writeValueAsString(requestObject);
        return encryptService.encrypt(message);
    }

    private <T> T validateResponse(T response){
        log.info("Response received {}", ((ResourceResponse<List<CardDTO>>)response).getData());
        return Validate.notNull(response, "received empty response from message broker");
    }

}