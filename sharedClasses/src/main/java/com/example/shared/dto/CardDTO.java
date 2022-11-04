package com.example.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardDTO implements Serializable {
    public CardDTO(String cardNumber, String firstName, String lastName) {
        this.cardNumber = cardNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @JsonProperty ("id")
    private Long id;
    //@NotNull(message = "cardNumber cannot be null")
    @JsonProperty ("cardNumber")
    private String cardNumber;
    //@NotNull(message = "firstName cannot be null")
    @JsonProperty ("firstName")
    private String firstName;

    @JsonProperty ("lastName")
    private String lastName;
}
