package com.gol.golbackend.dto;

import com.gol.golbackend.entity.TableRow;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class GameTableDto implements Serializable {

	@NotNull
	private List<TableRow> tableRows = new ArrayList<>();
}
