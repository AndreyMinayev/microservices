package com.example.publicapp;

import com.example.shared.response.cards.CardCreateResponse;
import com.example.shared.response.cards.CardListResponse;
import com.example.shared.response.cards.CardUpdateResponse;
import org.springframework.core.ParameterizedTypeReference;

public class CardResponseType {

	public static ParameterizedTypeReference<CardCreateResponse> create() {
		return new ParameterizedTypeReference<>() {

		};
	}

	public static ParameterizedTypeReference<CardUpdateResponse> update() {
		return new ParameterizedTypeReference<>() {

		};
	}

	public static ParameterizedTypeReference<CardListResponse> list() {
		return new ParameterizedTypeReference<>() {

		};
	}
}
