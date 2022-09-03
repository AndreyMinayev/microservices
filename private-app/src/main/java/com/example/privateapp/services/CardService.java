package com.example.privateapp.services;

import com.example.privateapp.entity.Card;
import com.example.privateapp.exceptions.CardNotFoundException;
import com.example.privateapp.facade.CardFacade;
import com.example.privateapp.repository.CardRepository;
import com.example.shared.dto.CardDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card getCardByNumber(Long cardNumber) {
            Optional<Card> card = cardRepository.findCardByCardNumber(cardNumber);
            System.out.println("card returned - "+ card.isEmpty());
            System.out.println("card returned - "+ card);
            return cardRepository.findCardByCardNumber(cardNumber).orElseThrow(() ->new CardNotFoundException("Card not found"));
    }

    public List<Card> getAllCards(){
        return cardRepository.findAll();
    }

    public List<Card> getCardsByLastAndFirstName(String lastName, String firstName){
        return cardRepository.findAllByLastNameAndFirstName(lastName,firstName);
    }

    public Card createOrUpdateCard(CardDTO cardDTO){
        Card card =  Card.builder().cardNumber(cardDTO.cardNumber())
                .firstName(cardDTO.firstName())
                .lastName(cardDTO.lastName()).build();
        return cardRepository.save(card);
    }

    public List<Card> createOrUpdateMultipleCards(List<CardDTO> cardDTOlist){
        List<Card> cardList = new ArrayList<>();
        for( CardDTO cardDTO : cardDTOlist ){
            Card card =  Card.builder().cardNumber(cardDTO.cardNumber())
                    .firstName(cardDTO.firstName())
                    .lastName(cardDTO.lastName()).build();
            cardList.add(cardRepository.save(card));
        }
        return cardList;
    }
    @Transactional
    public void deleteCardByNumber(Long cardNumber){
        cardRepository.deleteCardByCardNumber(cardNumber);
    }







}
