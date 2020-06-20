package com.gol.golbackend.controller;

import com.gol.golbackend.dto.GameTableDto;
import com.gol.golbackend.entity.GameTable;
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
	public GameTable startGame(@Validated @RequestBody final GameTableDto gameTableDto) {
		return golServiceImpl.startGame(gameTableDto);
	}

	@GetMapping("/new-game-state/{gameStateId}")
	public GameTable processGameState(@PathVariable final Long gameStateId) {
		return golServiceImpl.calculateNextGameState(gameStateId);
	}

	@GetMapping("/game-state/{gameStateId}")
	public GameTable getGameState(@PathVariable final Long gameStateId) {
		return golServiceImpl.getGameState(gameStateId);
	}
}
