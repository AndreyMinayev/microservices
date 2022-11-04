package com.example.shared.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collections;
import java.util.List;

public class ResourceResponse<T> {
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<String> error;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private T data;

	protected ResourceResponse() {}

	protected ResourceResponse(final List<String> errors) {
		this.error = errors;
	}

	protected ResourceResponse(final List<String> errors, final T data) {
		this.error = errors;
		this.data = data;
	}

	public static <T> ResourceResponse<T> success(final T data) {
		return new ResourceResponse<>(Collections.emptyList(), data);
	}

	public static <T> ResourceResponse<T> failure(final List<String> errors) {
		return new ResourceResponse<>(errors);
	}

	public static <T> ResourceResponse<T> failure(final String error) {
		return new ResourceResponse<>(Collections.singletonList(error));
	}

	public List<String> getError() {
		return error;
	}

	public T getData() {
		return data;
	}
}
