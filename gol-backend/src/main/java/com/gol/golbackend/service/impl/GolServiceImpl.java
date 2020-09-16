package com.gol.golbackend.service.impl;

import com.gol.golbackend.common.Constants;
import com.gol.golbackend.converter.GameStateDtoConverter;
import com.gol.golbackend.dto.GameStateDto;
import com.gol.golbackend.entity.GameState;
import com.gol.golbackend.exception.NotFoundException;
import com.gol.golbackend.repository.GameTableRepository;
import com.gol.golbackend.service.GameStateCalculatorService;
import com.gol.golbackend.service.GolService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GolServiceImpl implements GolService {

	private final GameTableRepository gameTableRepository;

	private final GameStateCalculatorService gameStateCalculatorService;

	private final GameStateDtoConverter gameStateDtoConverter;

	@Override
	public GameState startGame(final GameStateDto gameStateDto) {
		final GameState gameState = this.saveNewGameState(gameStateDtoConverter.convert(gameStateDto));
		return this.createNextGameState(gameState);
	}

	@Override
	public GameState getNextCalculatedGameState(final Long gameStateId) {
		final GameState gameState = this.getGameState(gameStateId);
		return this.createNextGameState(gameState);
	}

	@Override
	public GameState getGameState(final Long gameStateId) {
		return gameTableRepository.findById(gameStateId).orElseThrow(() -> new NotFoundException("Game state is not found!"));
	}

	private GameState createNextGameState(final GameState gameState) {
		final GameState newGameState = new GameState();
		newGameState.setRows(gameStateCalculatorService.calculateNextGameState(gameState.getRows()));
		newGameState.setPreviousGameStateId(gameState.getId());
		newGameState.setIndex(gameState.getIndex());

		final GameState savedGameState = saveNewGameState(newGameState);
		this.updatePreviousGameState(gameState, savedGameState.getId());

		return savedGameState;
	}

	private void updatePreviousGameState(final GameState gameState, final Long nextGameStateId) {
		gameState.setNextGameStateId(nextGameStateId);
		gameTableRepository.save(gameState);
	}

	private GameState saveNewGameState(final GameState gameState) {
		gameState.setIndex(this.updateIndex(gameState.getIndex()));

		return gameTableRepository.save(gameState);
	}

	private Integer updateIndex(final Integer currentIndex) {
		int newIndex = isNull(currentIndex) ? Constants.DEFAULT_TABLE_STATE_INDEX : currentIndex + 1;

		return newIndex;
	}
}
