package com.gol.golbackend.controller;

import com.gol.golbackend.dto.GameStateDto;
import com.gol.golbackend.entity.GameState;
import com.gol.golbackend.service.GolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GolController {

	@Autowired
	private GolService golServiceImpl;

	@PostMapping("/start-game")
	public GameState startGame(@Validated @RequestBody final GameStateDto gameStateDto) {
		return golServiceImpl.startGame(gameStateDto);
	}

	@GetMapping("/new-game-state/{gameStateId}")
	public GameState processGameState(@PathVariable final Long gameStateId) {
		return golServiceImpl.getNextCalculatedGameState(gameStateId);
	}

	@GetMapping("/game-state/{gameStateId}")
	public GameState getGameState(@PathVariable final Long gameStateId) {
		return golServiceImpl.getGameState(gameStateId);
	}
}
