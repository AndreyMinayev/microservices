package com.example.privateapp.facade;


import com.example.privateapp.entity.Card;
import com.example.shared.dto.CardDTO;
import org.springframework.stereotype.Component;

@Component
public class CardFacade {
    public CardDTO cardToCardDTO(Card card){
        return CardDTO.builder().cardNumber(card.getCardNumber())
                .firstName(card.getFirstName())
                .lastName(card.getLastName()).build();
    }
    public Card cardDTOToCard(CardDTO cardDTO){
        return Card.builder().cardNumber(cardDTO.cardNumber())
                .firstName(cardDTO.firstName())
                .lastName(cardDTO.lastName()).build();
    }
}
