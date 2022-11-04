package com.example.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record CardRequest(@JsonProperty("type") String type,
                          @JsonProperty ("value") String value)  implements Serializable {
}
