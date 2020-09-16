package com.gol.golbackend.service;

import com.gol.golbackend.common.Constants;
import com.gol.golbackend.converter.GameStateDtoConverter;
import com.gol.golbackend.dto.GameStateDto;
import com.gol.golbackend.entity.GameState;
import com.gol.golbackend.entity.Row;
import com.gol.golbackend.exception.NotFoundException;
import com.gol.golbackend.repository.GameTableRepository;
import com.gol.golbackend.service.impl.GolServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GolServiceImplTest {

	final static Integer EXPECTED_GAME_STATE_INDEX = Constants.DEFAULT_TABLE_STATE_INDEX + 1;

	@Mock
	private GameTableRepository gameTableRepository;

	@Mock
	private GameStateCalculatorService gameStateCalculatorService;

	@Mock
	private GameStateDtoConverter gameStateDtoConverter;

	@Spy
	@InjectMocks
	private GolServiceImpl subject;

	private GameState gameState;

	@Before
	public void init() {
		gameState = GameState.builder()
				.id(2l)
				.previousGameStateId(1l)
				.nextGameStateId(3l)
				.index(Constants.DEFAULT_TABLE_STATE_INDEX)
				.rows(Arrays.asList(new Row())).build();

		when(gameTableRepository.save(any(GameState.class))).then(returnsFirstArg());
	}

	@Test
	public void testStartGame() {
		gameState.setIndex(null);
		when(gameStateDtoConverter.convert(any())).thenReturn(gameState);
		GameStateDto gameStateDto = new GameStateDto();
		gameStateDto.setRows(gameState.getRows());
		GameState resultGameState = subject.startGame(gameStateDto);

		assertThat(resultGameState.getRows()).isNotNull();
		assertEquals(EXPECTED_GAME_STATE_INDEX, resultGameState.getIndex());
	}

	@Test
	public void testGetNextCalculatedGameState() {
		doReturn(gameState).when(subject).getGameState(anyLong());
		GameState resultGameState = subject.getNextCalculatedGameState(1l);

		assertThat(resultGameState.getRows()).isNotNull();
		assertEquals(EXPECTED_GAME_STATE_INDEX, resultGameState.getIndex());
	}

	@Test
	public void testGetGameState() {
		when(gameTableRepository.findById(anyLong())).thenReturn(Optional.ofNullable(gameState));
		GameState resultGameState = subject.getGameState(anyLong());

		assertThat(resultGameState.getRows()).isNotNull();
	}

	@Test(expected = NotFoundException.class)
	public void testNotFoundGetGameState() {
		subject.getGameState(anyLong());
	}
}
