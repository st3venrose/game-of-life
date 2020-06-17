package com.gol.golbackend.service;

import com.gol.golbackend.entity.TableRow;

import java.util.List;

public interface GameStateCalculatorService {
	List<TableRow> calculateNextGameState(List<TableRow> tableRows);
}
