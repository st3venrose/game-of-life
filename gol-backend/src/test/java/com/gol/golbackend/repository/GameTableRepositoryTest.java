package com.gol.golbackend.repository;

import com.gol.golbackend.common.Constants;
import com.gol.golbackend.common.FieldStatus;
import com.gol.golbackend.entity.Field;
import com.gol.golbackend.entity.GameState;
import com.gol.golbackend.entity.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestPropertySource(properties = {
		"spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
class GameTableRepositoryTest {

	private static final Long ID_1 = 1l;
	private static final Long ID_2 = 2l;

	@Autowired
	private GameTableRepository gameTableRepository;

	private GameState gameState;

	@BeforeEach
	void init() {
		Field field1 = new Field(FieldStatus.EMPTY);
		Field field2 = new Field(FieldStatus.LIVE);
		Row row = new Row();

		row.addField(field1);
		row.addField(field2);

		gameState = GameState.builder()
				.previousGameStateId(1l)
				.nextGameStateId(3l)
				.rows(new ArrayList<>())
				.index(Constants.DEFAULT_TABLE_STATE_INDEX).build();
		gameState.addRow(row);
	}

	@Test
	void saveTribeTest() {
		GameState savedGameState = gameTableRepository.save(gameState);

		assertNotNull(savedGameState);
		assertEquals(1, savedGameState.getRows().size());
		assertEquals(2, savedGameState.getRows().get(0).getFields().size());
	}


	@Test
	@Sql("/test-data.sql")
	void listGameStateTest() {
		GameState foundedGameState = gameTableRepository.findById(ID_1).get();

		assertNotNull(foundedGameState);
		assertEquals(1, foundedGameState.getIndex());
		assertEquals(1, foundedGameState.getRows().size());
		assertEquals(2, foundedGameState.getRows().get(0).getFields().size());
		assertEquals(ID_1, foundedGameState.getRows().get(0).getFields().get(0).getId());
		assertEquals(ID_2, foundedGameState.getRows().get(0).getFields().get(1).getId());
	}
}
