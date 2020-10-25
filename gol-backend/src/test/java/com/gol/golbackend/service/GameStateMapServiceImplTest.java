package com.gol.golbackend.service;

import com.gol.golbackend.common.Constants;
import com.gol.golbackend.entity.Field;
import com.gol.golbackend.entity.GameState;
import com.gol.golbackend.entity.Row;
import com.gol.golbackend.service.impl.GameStateMapServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameStateMapServiceImplTest {

	private GameStateMapService subject = new GameStateMapServiceImpl();

	private GameState gameState;

	@BeforeEach
	void init() {
		Row row = new Row();
		row.getFields().add(new Field());

		gameState = GameState.builder()
				.id(2l)
				.previousGameStateId(1l)
				.nextGameStateId(3l)
				.index(Constants.DEFAULT_TABLE_STATE_INDEX)
				.rows(List.of(row)).build();
	}

	@Test
	void testConverter() {
		GameState mappedGameState = subject.mapRelationOwners(gameState);
		assertNotNull(mappedGameState.getRows());
		assertNotNull(mappedGameState.getRows().get(0).getGameState(),"Row should have owner");
		assertNotNull(mappedGameState.getRows().get(0).getFields().get(0).getRow(), "Field should have owner");
	}
}
