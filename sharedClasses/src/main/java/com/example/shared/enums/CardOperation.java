package com.example.shared.enums;

import java.util.Arrays;

public enum CardOperation {

		CREATE("cards-create"),
		UPDATE("cards-update"),
		DELETE("cards-delete"),
		LIST("cards-list");

	private final String id;

	CardOperation(final String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public static CardOperation from(final String id) {
		return Arrays.stream(values())
			.filter(item -> item.getId().equals(id))
			.findFirst()
			.orElseThrow(() -> new RuntimeException(String.format("Card operation '%s' was not found", id)));
	}

	@Override
	public String toString() {
		return 	id ;
	}
}
