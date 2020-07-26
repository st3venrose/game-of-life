package com.gol.golbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gol.golbackend.dto.GameStateDto;
import com.gol.golbackend.entity.GameState;
import com.gol.golbackend.entity.Row;
import com.gol.golbackend.exception.NotFoundException;
import com.gol.golbackend.service.GolService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GolController.class)
public class GolControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private GolService golServiceImpl;

	public static ObjectMapper objectMapper = new ObjectMapper();

	private GameState gameState = new GameState(2l, 1l, 3l, 2, Arrays.asList(new Row()));

	@Test
	public void testStartGame() throws Exception {
		when(golServiceImpl.startGame(any(GameStateDto.class))).thenReturn(gameState);

		mvc.perform(post("/api/start-game")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(gameState)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(2)))
				.andExpect(jsonPath("$.previousGameStateId", is(1)))
				.andExpect(jsonPath("$.nextGameStateId", is(3)))
				.andExpect(jsonPath("$.index", is(2)));
	}

	@Test
	public void testStartGameBadRequest() throws Exception {
		when(golServiceImpl.startGame(any(GameStateDto.class))).thenReturn(gameState);
		GameStateDto invalidGameStateDto = new GameStateDto();
		invalidGameStateDto.setRows(null);

		mvc.perform(post("/api/start-game")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidGameStateDto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testProcessGameState() throws Exception {
		when(golServiceImpl.getNextCalculatedGameState(anyLong())).thenReturn(gameState);

		mvc.perform(get("/api/new-game-state/{gameStateId}", 1L)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(2)))
				.andExpect(jsonPath("$.previousGameStateId", is(1)))
				.andExpect(jsonPath("$.nextGameStateId", is(3)))
				.andExpect(jsonPath("$.index", is(2)));
	}

	@Test
	public void testGetGameState() throws Exception {
		when(golServiceImpl.getGameState(anyLong())).thenReturn(gameState);

		mvc.perform(get("/api/game-state/{gameStateId}", 1L)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(2)))
				.andExpect(jsonPath("$.previousGameStateId", is(1)))
				.andExpect(jsonPath("$.nextGameStateId", is(3)))
				.andExpect(jsonPath("$.index", is(2)));

	}

	@Test
	public void testGetGameStateNotFound() throws Exception {
		when(golServiceImpl.getGameState(anyLong())).thenThrow(new NotFoundException());

		mvc.perform(get("/api/get-game-state/{gameStateId}", 1L)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
