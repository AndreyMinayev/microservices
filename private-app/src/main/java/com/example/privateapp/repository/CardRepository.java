package com.example.privateapp.repository;




import com.example.privateapp.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findCardByCardNumber(Long cardNumber);

    List<Card> findAllByLastNameAndFirstName(String lastName, String firstName);

    void deleteCardByCardNumber(Long cardNumber);

}
