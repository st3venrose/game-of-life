package com.gol.golbackend.service.impl;

import com.gol.golbackend.dto.GameTableDto;
import com.gol.golbackend.entity.GameTable;
import com.gol.golbackend.entity.TableRow;
import com.gol.golbackend.exception.NotFoundException;
import com.gol.golbackend.repository.GameTableRepository;
import com.gol.golbackend.service.GameStateCalculatorService;
import com.gol.golbackend.service.GolService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GolServiceImpl implements GolService {

	private final GameTableRepository gameTableRepository;

	private final GameStateCalculatorService gameStateCalculatorService;

	@Override
	public GameTable startGame(final GameTableDto gameTableDto) {
		final GameTable gameTable = new GameTable();
		final List<TableRow> tableRows = gameStateCalculatorService.calculateNextGameState(gameTableDto.getTableRows());

		gameTable.getTableRows().addAll(tableRows);

		return gameTableRepository.save(gameTable);
	}

	@Override
	public GameTable calculateNextGameState(final Long gameTableId) {
		final GameTable newGameTable = new GameTable();
		final GameTable gameTable = this.getGameState(gameTableId);

		newGameTable.setTableRows(gameStateCalculatorService.calculateNextGameState(gameTable.getTableRows()));
		newGameTable.setPreviousGameTableId(gameTableId);

		this.updatePreviousGameTable(gameTableId, newGameTable.getId());

		return gameTableRepository.save(newGameTable);
	}

	@Transactional(readOnly = true)
	@Override
	public GameTable getGameState(final Long gameStateId) {
		return gameTableRepository.findById(gameStateId).orElseThrow(() -> new NotFoundException("Game state is not found!"));
	}

	private void updatePreviousGameTable(final Long gameStateId, final Long nextGameStateId) {
		final GameTable previousGameState = this.getGameState(gameStateId);

		previousGameState.setNextGameTableId(nextGameStateId);
		gameTableRepository.save(previousGameState);
	}
}
