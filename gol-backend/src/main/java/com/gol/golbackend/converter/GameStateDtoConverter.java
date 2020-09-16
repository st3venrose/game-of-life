package com.gol.golbackend.converter;

import com.gol.golbackend.dto.GameStateDto;
import com.gol.golbackend.entity.GameState;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GameStateDtoConverter implements Converter<GameStateDto, GameState> {

	@Override
	public GameState convert(GameStateDto source) {
		return GameState.builder().rows(source.getRows()).build();
	}
}
