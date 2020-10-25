package com.gol.golbackend.service.impl;

import com.gol.golbackend.entity.GameState;
import com.gol.golbackend.service.GameStateMapService;
import org.springframework.stereotype.Service;

@Service
public class GameStateMapServiceImpl implements GameStateMapService {

    @Override
    public GameState mapRelationOwners(final GameState gameState) {
        gameState.getRows().forEach(row -> {
            row.setGameState(gameState);
            row.getFields().forEach(field -> field.setRow(row));
        });

        return gameState;
    }
}
