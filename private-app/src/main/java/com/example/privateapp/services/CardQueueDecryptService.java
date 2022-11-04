package com.example.privateapp.services;

import com.example.shared.dto.CardDTO;
import com.example.shared.dto.CardRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class CardQueueDecryptService {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private DecryptService decryptService;

	public long decryptId(final byte[] encryptedRequest) throws Exception {
		final String decrypted = decryptService.decrypt(encryptedRequest);
		Validate.notNull(decrypted, "failed to decrypt clientId");
		return Long.parseLong(decrypted);
	}

	public List<CardDTO> decryptCardData(final byte[] encryptedRequest) throws Exception {
		final String decryptedCardData = decryptService.decrypt(encryptedRequest);
		return objectMapper.readValue(decryptedCardData, new TypeReference<>() {});
	}

	public CardDTO decryptSingleCardData(final byte[] encryptedRequest) throws Exception {
		final String decryptedCardData = decryptService.decrypt(encryptedRequest);
		return objectMapper.readValue(decryptedCardData, new TypeReference<>() {});
	}

	public CardRequest decryptCardRequest(final byte[] encryptedRequest) throws Exception {
		final String decryptedCardData = decryptService.decrypt(encryptedRequest);
		return objectMapper.readValue(decryptedCardData, new TypeReference<>() {});
	}
}
