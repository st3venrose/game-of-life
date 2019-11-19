package com.gol.golbackend.service.impl;

import com.gol.golbackend.dto.StarterGameTableDto;
import com.gol.golbackend.entity.GameTable;
import com.gol.golbackend.repository.GameTableRepository;
import com.gol.golbackend.service.GolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GolServiceImpl implements GolService {

	@Autowired
	private GameTableRepository gameTableRepository;

	@Override
	public GameTable startGame(final StarterGameTableDto starterGameTableDto) {
		final GameTable gameTable = new GameTable();
		gameTable.getTableRow().addAll(starterGameTableDto.getGameTableDto().getTableRow());

		return gameTableRepository.save(gameTable);
	}

	@Override
	public GameTable calculateNextGameState(final Long gameTableId) {
		return null;
	}
}
