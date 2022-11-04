package com.example.privateapp.services;

import com.example.privateapp.entity.Card;
import com.example.privateapp.repository.CardRepository;
import com.example.shared.dto.CardDTO;
import org.apache.commons.lang3.Validate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CardService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CardRepository cardRepository;

    private static final Logger log = LoggerFactory.getLogger(CardService.class);



    public CardDTO getCardById(Long cardId) {
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        Validate.isTrue(optionalCard.isPresent(), "card with if '%s' is not present", cardId);
        return modelMapper.map(optionalCard.get() ,CardDTO.class);
    }

    public List<CardDTO> getAllCards(){
        return mapListToDto(cardRepository.findAll());
    }

    public CardDTO updateCard(CardDTO cardDTO){
        return modelMapper.map(cardRepository.save(getCardForUpdate(cardDTO)), CardDTO.class);
    }

    public List<CardDTO> createCards(List<CardDTO> cardDTOlist){
        List<Card> cardList =cardDTOlist.stream().map(this::getValidatedCard).collect(Collectors.toList());
        log.info("Cards validated for create {}", cardList);
        List<Card>  cardList1=cardRepository.saveAll(cardList);
        System.out.println(cardList1);
        return mapListToDto(cardList1);
    }
    @Transactional
    public CardDTO deleteCard(Long cardId){
        final Card card = getCardForUpdate(new CardDTO(cardId,null,null,null));
        cardRepository.delete(card);
        return modelMapper.map(card, CardDTO.class);

    }


    private List<CardDTO> mapListToDto(final List<Card> cards) {
        return cards.stream().map(item -> modelMapper.map(item, CardDTO.class)).collect(Collectors.toList());
    }

    private Card getValidatedCard(final CardDTO cardDTO) {
        final String cardNumber = Validate.notBlank(cardDTO.getCardNumber(), "cardNumber is blank");
        final String firstName = Validate.notBlank(cardDTO.getFirstName(), "firstName is blank");
        final String lastName = Validate.notBlank(cardDTO.getLastName(), "lastName is blank");
        return Card.builder().cardNumber(cardNumber).firstName(firstName).lastName(lastName).build();
    }


    private Card getCardForUpdate(final CardDTO cardDTO) {
        final long cardId = Validate.notNull(cardDTO.getId(), "card id is undefined");
        final Optional<Card> optionalCard = cardRepository.findById(cardDTO.getId());
        Validate.isTrue(optionalCard.isPresent(), "card with id '%s' is not present", cardId);
        Card card = optionalCard.get();
        card.setId(cardId);
        card.setCardNumber(cardDTO.getCardNumber()!=null? cardDTO.getCardNumber(): card.getCardNumber());
        card.setFirstName(cardDTO.getFirstName()!=null? cardDTO.getFirstName(): card.getFirstName());
        card.setLastName(cardDTO.getLastName()!=null? cardDTO.getLastName(): card.getLastName());
        return card;
    }
}
