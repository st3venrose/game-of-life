package com.gol.golbackend.service;

import com.gol.golbackend.common.Constants;
import com.gol.golbackend.converter.GameStateDtoConverter;
import com.gol.golbackend.dto.GameStateDto;
import com.gol.golbackend.entity.GameState;
import com.gol.golbackend.entity.Row;
import com.gol.golbackend.exception.NotFoundException;
import com.gol.golbackend.repository.GameTableRepository;
import com.gol.golbackend.service.impl.GolServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GolServiceImplTest {

	final static Integer EXPECTED_GAME_STATE_INDEX = Constants.DEFAULT_TABLE_STATE_INDEX + 1;

	@Mock
	private GameTableRepository gameTableRepository;

	@Mock
	private GameStateCalculatorService gameStateCalculatorService;

	@Mock
	private GameStateDtoConverter gameStateDtoConverter;

	@Mock
	private GameStateMapService gameStateMapService;

	@Spy
	@InjectMocks
	private GolServiceImpl subject;

	private GameState gameState;

	@BeforeEach
	void init() {
		gameState = GameState.builder()
				.id(2l)
				.previousGameStateId(1l)
				.nextGameStateId(3l)
				.index(Constants.DEFAULT_TABLE_STATE_INDEX)
				.rows(List.of(new Row())).build();

		lenient().when(gameTableRepository.save(any(GameState.class))).then(returnsFirstArg());
	}

	@Test
	void testStartGame() {
		gameState.setIndex(null);
		when(gameStateDtoConverter.convert(any())).thenReturn(gameState);
		when(gameStateMapService.mapRelationOwners(any())).thenReturn(gameState);

		GameStateDto gameStateDto = new GameStateDto();
		gameStateDto.setRows(gameState.getRows());
		GameState resultGameState = subject.startGame(gameStateDto);

		assertNotNull(resultGameState.getRows());
		assertEquals(EXPECTED_GAME_STATE_INDEX, resultGameState.getIndex());
	}

	@Test
	void testGetNextCalculatedGameState() {
		doReturn(gameState).when(subject).getGameState(anyLong());
		when(gameStateMapService.mapRelationOwners(any())).thenReturn(gameState);

		GameState resultGameState = subject.getNextCalculatedGameState(1l);

		assertNotNull(resultGameState.getRows());
		assertEquals(EXPECTED_GAME_STATE_INDEX, resultGameState.getIndex());
	}

	@Test
	void testGetGameState() {
		when(gameTableRepository.findById(anyLong())).thenReturn(Optional.ofNullable(gameState));
		GameState resultGameState = subject.getGameState(anyLong());

		assertNotNull(resultGameState.getRows());
	}

	@Test
	void testNotFoundGetGameState() {
		assertThrows(NotFoundException.class, () -> subject.getGameState(anyLong()));
	}
}
