package com.gol.golbackend.dto;

import com.gol.golbackend.entity.TableRow;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class GameTableDto implements Serializable {
	private List<TableRow> tableRow = new ArrayList<>();
}
