package com.example.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record ErrorDTO (@JsonProperty("code") Long code,
                        @JsonProperty ("message") String message)  implements Serializable {
}
