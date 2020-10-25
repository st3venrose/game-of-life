package com.gol.golbackend.dto;

import com.gol.golbackend.entity.Row;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class GameStateDto implements Serializable {

	@NotEmpty
	private List<Row> rows = new ArrayList<>();
}
