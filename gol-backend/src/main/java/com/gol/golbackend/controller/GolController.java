package com.gol.golbackend.controller;

import com.gol.golbackend.dto.StarterGameTableDto;
import com.gol.golbackend.entity.GameTable;
import com.gol.golbackend.service.GolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GolController {

	@Autowired
	private GolService golServiceImpl;

	@PostMapping("/startGame")
	public GameTable startGame(@Validated @RequestBody final StarterGameTableDto starterGameTableDto) {
		return golServiceImpl.startGame(starterGameTableDto);
	}
}
