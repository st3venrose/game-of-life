package com.gol.golbackend.service;

import com.gol.golbackend.dto.GameTableDto;
import com.gol.golbackend.entity.GameTable;

public interface GolService {
	GameTable startGame(GameTableDto gameTableDto);

	GameTable calculateNextGameState(Long gameStateId);

	GameTable getGameState(Long gameStateId);
}
