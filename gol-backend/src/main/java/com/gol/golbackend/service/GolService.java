package com.gol.golbackend.service;


import com.gol.golbackend.dto.StarterGameTableDto;
import com.gol.golbackend.entity.GameTable;

public interface GolService {
	GameTable startGame(StarterGameTableDto starterGameTableDto);

	GameTable calculateNextGameState(Long gameTableId);
}
