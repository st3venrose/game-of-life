package com.gol.golbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Violation {
	private String fieldName;

	private String message;
}
