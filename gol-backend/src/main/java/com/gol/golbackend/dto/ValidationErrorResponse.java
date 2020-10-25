package com.gol.golbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ValidationErrorResponse {
	private List<Violation> violations = new ArrayList<>();
}
