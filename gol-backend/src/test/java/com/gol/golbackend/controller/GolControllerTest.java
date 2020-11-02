package com.gol.golbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gol.golbackend.dto.GameStateDto;
import com.gol.golbackend.entity.GameState;
import com.gol.golbackend.entity.Row;
import com.gol.golbackend.service.GolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GolController.class)
class GolControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GolService golServiceImpl;

	static ObjectMapper objectMapper = new ObjectMapper();

	private GameState gameState;

	private GameStateDto gameStateDto;

	@BeforeEach
	void init() {
		gameState = GameState.builder()
				.id(2l)
				.previousGameStateId(1l)
				.nextGameStateId(3l)
				.index(2)
				.rows(List.of(new Row())).build();

		gameStateDto = new GameStateDto();
		gameStateDto.getRows().add(new Row());
	}

	@Test
	void testStartGame() throws Exception {
		when(golServiceImpl.startGame(any(GameStateDto.class))).thenReturn(gameState);

		mockMvc.perform(post("/api/start-game")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(gameStateDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(2)))
				.andExpect(jsonPath("$.previousGameStateId", is(1)))
				.andExpect(jsonPath("$.nextGameStateId", is(3)))
				.andExpect(jsonPath("$.index", is(2)));
	}

	@Test
	void testProcessGameState() throws Exception {
		when(golServiceImpl.getNextCalculatedGameState(anyLong())).thenReturn(gameState);

		mockMvc.perform(get("/api/new-game-state/{gameStateId}", 1)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(2)))
				.andExpect(jsonPath("$.previousGameStateId", is(1)))
				.andExpect(jsonPath("$.nextGameStateId", is(3)))
				.andExpect(jsonPath("$.index", is(2)));
	}

	@Test
	void testGetGameState() throws Exception {
		when(golServiceImpl.getGameState(anyLong())).thenReturn(gameState);

		mockMvc.perform(get("/api/game-state/{gameStateId}", 1)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(2)))
				.andExpect(jsonPath("$.previousGameStateId", is(1)))
				.andExpect(jsonPath("$.nextGameStateId", is(3)))
				.andExpect(jsonPath("$.index", is(2)));
	}

	@Test
	void testStartGameBadRequest() throws Exception {
		GameStateDto invalidGameStateDto = new GameStateDto();
		invalidGameStateDto.setRows(null);

		mockMvc.perform(post("/api/start-game")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidGameStateDto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testGetGameStateNotFound() throws Exception {
		mockMvc.perform(get("/api/get-game-state/{gameStateId}", 1)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}