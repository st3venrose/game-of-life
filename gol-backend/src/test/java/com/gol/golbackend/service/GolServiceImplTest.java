package com.gol.golbackend.service;

import com.gol.golbackend.dto.GameTableDto;
import com.gol.golbackend.entity.GameTable;
import com.gol.golbackend.entity.TableRow;
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
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GolServiceImplTest {

	@Mock
	private GameTableRepository gameTableRepository;

	@Mock
	private GameStateCalculatorService gameStateCalculatorService;

	@Spy
	@InjectMocks
	private GolServiceImpl subject;

	private GameTable gameTable = new GameTable(2l, 1l, 3l, Arrays.asList(new TableRow()));

	@Before
	public void init() {
		when(gameTableRepository.save(any(GameTable.class))).then(returnsFirstArg());
	}

	@Test
	public void testStartGame() {
		when(gameStateCalculatorService.calculateNextGameState(gameTable.getTableRows())).thenReturn(gameTable.getTableRows());

		GameTableDto gameTableDto = new GameTableDto();
		gameTableDto.setTableRows(Arrays.asList(new TableRow()));
		GameTable resultGameTable = subject.startGame(gameTableDto);
		assertThat(resultGameTable.getTableRows()).isNotNull();
	}

	@Test
	public void testCalculateNextGameState() {
		doReturn(gameTable).when(subject).getGameState(1l);

		GameTable resultGameTable = subject.calculateNextGameState(1l);
		assertThat(resultGameTable.getTableRows()).isNotNull();
	}

	@Test
	public void testGetGameState() {
		when(gameTableRepository.findById(anyLong())).thenReturn(Optional.ofNullable(gameTable));
		GameTable resultGameTable = subject.getGameState(anyLong());
		assertThat(resultGameTable.getTableRows()).isNotNull();
	}

	@Test(expected = NotFoundException.class)
	public void testNotFoundGetGameState() {
		subject.getGameState(anyLong());
	}
}
