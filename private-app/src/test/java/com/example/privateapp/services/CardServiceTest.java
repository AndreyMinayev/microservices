package com.example.privateapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import com.example.privateapp.entity.Card;
import com.example.privateapp.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.example.shared.dto.CardDTO;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CardServiceTest {

	@InjectMocks
	private CardService cardService;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private CardRepository cardRepository;

	@Test
	void createFailsIfCardNumberIsMissing() {
		final CardDTO cardDto = new CardDTO(1L, null, "name", "surname");
		assertThrows(NullPointerException.class, () -> cardService.createCards(Collections.singletonList(cardDto)));
	}

	@Test
	void createFailsIfNameIsMissing() {
		final CardDTO cardDto = new CardDTO(1L, "123123123", null, "surname");
		assertThrows(NullPointerException.class, () -> cardService.createCards(Collections.singletonList(cardDto)));
	}

	@Test
	void createFailsIfLastNameIsMissing() {
		final CardDTO cardDto = new CardDTO(1L, "123123123", "name", null);
		assertThrows(NullPointerException.class, () -> cardService.createCards(Collections.singletonList(cardDto)));
	}


	@Test
	void createSuccess() {
		final CardDTO cardDTO = new CardDTO(null, "123123123", "name", "surname");
		when(cardRepository.saveAll(any())).thenReturn(Collections.singletonList(new Card()));
		when(modelMapper.map(any(), any())).thenReturn(cardDTO);
		final List<CardDTO> cardDTOList = cardService.createCards(Collections.singletonList(cardDTO));
		assertEquals(1, cardDTOList.size());
	}
}
