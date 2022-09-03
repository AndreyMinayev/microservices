package com.example.publicapp.controller;



import com.example.publicapp.service.RabbitMQSender;
import com.example.shared.dto.CardDTO;
import com.example.shared.dto.RequestDTO;
import com.example.shared.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(value = "/api/")
public class CardController {

    @Autowired
    RabbitMQSender rabbitMQSender;

    @GetMapping(value = "/cards/{cardNumber}")
    public ResponseDTO getCardByNumber(@PathVariable("cardNumber") String cardNumber) {
        return rabbitMQSender.sendRequest(RequestDTO.builder().type("cardNumber").value(cardNumber).build());
    }

    @GetMapping(value = "/cards")
    public ResponseDTO getAllCards(@RequestParam("lastName") Optional<String> lastName) {
        if(lastName.isEmpty()){
            return rabbitMQSender.sendRequest(RequestDTO.builder().type("all").build());
        }
        return rabbitMQSender.sendRequest(RequestDTO.builder().type("lastName").value(lastName.get()).build());
    }

    @PostMapping(value = "/cards")
    public Object createOrUpdateCard(@RequestBody CardDTO cardDTO) {
        System.out.println(cardDTO);
        return  rabbitMQSender.createOrUpdateCard(cardDTO);
    }


    @DeleteMapping(value = "/cards/{cardNumber}")
    public ResponseEntity<String> deleteCard(@PathVariable("cardNumber") Long cardNumber) {
        rabbitMQSender.deleteCard(cardNumber);
        return new ResponseEntity<>("Card was deleted" , HttpStatus.OK);
    }




}
