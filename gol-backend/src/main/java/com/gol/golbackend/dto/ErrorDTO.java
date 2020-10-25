package com.gol.golbackend.dto;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class ErrorDTO implements Serializable {
	private String errorCode;

	private String errorMessage;
}
