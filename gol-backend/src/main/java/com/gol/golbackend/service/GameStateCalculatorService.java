package com.gol.golbackend.service;

import com.gol.golbackend.entity.Row;

import java.util.List;

public interface GameStateCalculatorService {
	List<Row> calculateNextGameState(List<Row> tableRows);
}
