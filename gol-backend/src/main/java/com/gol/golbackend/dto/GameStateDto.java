package com.gol.golbackend.dto;

import com.gol.golbackend.entity.Row;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class GameStateDto implements Serializable {

	@NotEmpty
	private List<Row> rows = new ArrayList<>();
}
