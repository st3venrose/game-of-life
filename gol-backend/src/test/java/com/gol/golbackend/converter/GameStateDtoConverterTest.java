package com.gol.golbackend.converter;

import com.gol.golbackend.dto.GameStateDto;
import com.gol.golbackend.entity.GameState;
import com.gol.golbackend.entity.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameStateDtoConverterTest {

	private GameStateDtoConverter subject = new GameStateDtoConverter();

	private GameStateDto gameStateDto;

	@BeforeEach
	void init() {
		gameStateDto = new GameStateDto();
		gameStateDto.setRows(List.of(new Row()));
	}

	@Test
	void testConverter() {
		GameState gameState = subject.convert(gameStateDto);
		assertNotNull(gameState.getRows());
	}
}
