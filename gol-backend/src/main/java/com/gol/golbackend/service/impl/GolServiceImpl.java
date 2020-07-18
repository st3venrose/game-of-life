package com.gol.golbackend.service.impl;

import com.gol.golbackend.dto.GameStateDto;
import com.gol.golbackend.entity.GameState;
import com.gol.golbackend.exception.NotFoundException;
import com.gol.golbackend.repository.GameTableRepository;
import com.gol.golbackend.service.GameStateCalculatorService;
import com.gol.golbackend.service.GolService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GolServiceImpl implements GolService {

	private final static int DEFAULT_TABLE_STATE_INDEX = 1;

	private final GameTableRepository gameTableRepository;

	private final GameStateCalculatorService gameStateCalculatorService;

	@Override
	public GameState startGame(final GameStateDto gameStateDto) {
		final GameState userDefinedGameState = this.saveUserDefinedGameTable(gameStateDto);
		return this.calculateNextGameState(userDefinedGameState.getId());
	}

	@Override
	public GameState calculateNextGameState(final Long gameTableId) {
		final GameState newGameState = new GameState();
		final GameState gameState = this.getGameState(gameTableId);

		newGameState.setRows(gameStateCalculatorService.calculateNextGameState(gameState.getRows()));
		newGameState.setPreviousGameStateId(gameTableId);
		newGameState.setIndex(this.updateIndex(gameState.getIndex()));

		final GameState savedGameState = gameTableRepository.save(newGameState);
		this.updatePreviousGameTable(gameTableId, savedGameState.getId());

		return savedGameState;
	}

	@Transactional(readOnly = true)
	@Override
	public GameState getGameState(final Long gameStateId) {
		return gameTableRepository.findById(gameStateId).orElseThrow(() -> new NotFoundException("Game state is not found!"));
	}

	private void updatePreviousGameTable(final Long gameStateId, final Long nextGameStateId) {
		final GameState previousGameState = this.getGameState(gameStateId);

		previousGameState.setNextGameStateId(nextGameStateId);
		gameTableRepository.save(previousGameState);
	}

	private GameState saveUserDefinedGameTable(final GameStateDto gameStateDto) {
		final GameState gameState = new GameState();
		gameState.setRows(gameStateDto.getRows());
		gameState.setIndex(this.updateIndex(gameState.getIndex()));

		return gameTableRepository.save(gameState);
	}

	private Integer updateIndex(final Integer currentIndex) {
		int newIndex = isNull(currentIndex) ? DEFAULT_TABLE_STATE_INDEX : currentIndex + 1;

		return newIndex;
	}
}
