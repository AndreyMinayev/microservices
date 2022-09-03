package com.example.privateapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card implements Serializable {

    @Id
    private Long cardNumber;

    @Column (nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

}
