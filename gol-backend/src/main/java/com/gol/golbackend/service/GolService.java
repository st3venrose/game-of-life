package com.gol.golbackend.service;

import com.gol.golbackend.dto.GameStateDto;
import com.gol.golbackend.entity.GameState;

public interface GolService {
	GameState startGame(GameStateDto gameStateDto);

	GameState getNextCalculatedGameState(Long gameStateId);

	GameState getGameState(Long gameStateId);
}
