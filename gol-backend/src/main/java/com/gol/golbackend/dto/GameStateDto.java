package com.gol.golbackend.dto;

import com.gol.golbackend.entity.Row;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class GameStateDto implements Serializable {

	@NotNull
	private List<Row> rows = new ArrayList<>();
}
