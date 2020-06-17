package com.gol.golbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDTO implements Serializable {
	private String errorCode;

	private String errorMessage;
}
