package com.example.shared.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDTO implements Serializable {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<CardDTO> result;

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "error='" + error + '\'' +
                ", result=" + result +
                '}';
    }
}
