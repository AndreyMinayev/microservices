package com.example.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record CardDTO(@JsonProperty ("cardNumber") Long cardNumber,
                      @JsonProperty ("firstName") String firstName,
                      @JsonProperty ("lastName") String lastName) implements Serializable {
}
