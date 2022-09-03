package com.example.publicapp.service;

import com.example.shared.dto.CardDTO;
import com.example.shared.dto.RequestDTO;
import com.example.shared.dto.ResponseDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${cards.rabbitmq.exchange}")
    private String exchange;

    @Value("${cards.rabbitmq.newCardRoute}")
    private String newCardRoutingKey;
    @Value("${cards.rabbitmq.requestRoute}")
    private String requestRoutingKey;
    @Value("${cards.rabbitmq.deleteRoute}")
    private String deleteRoutingKey;


    public ResponseDTO sendRequest(RequestDTO request) {
        Object response = rabbitTemplate.convertSendAndReceive(exchange, requestRoutingKey, request);
        if(response instanceof ResponseDTO){
            return (ResponseDTO)response;
        }
        return ResponseDTO.builder().error("404 ERROR").build();
    }


    public Object createOrUpdateCard(CardDTO card) {
        System.out.println(card);
        Object response = rabbitTemplate.convertSendAndReceive(exchange, newCardRoutingKey, card);
        System.out.println(response);
        if(response instanceof ResponseDTO){
            return response;
        }
        return ResponseDTO.builder().error("404 ERROR").build();
    }


    public void deleteCard(Long candNumber) {
        Object response = rabbitTemplate.convertSendAndReceive(exchange, deleteRoutingKey, candNumber);
        if(response != null && response.toString().contains("successfully deleted")){
            return;
        }
        ResponseDTO.builder().error("404 ERROR").build();
    }

}