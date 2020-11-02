package com.gol.golbackend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "game_state")
public class GameState extends BaseEntity {

	@Column(name = "previous_state_id")
	private Long previousGameStateId;

	@Column(name = "next_game_state_id")
	private Long nextGameStateId;

	@Column(name = "index")
	private Integer index;

	@NotEmpty
	@OneToMany(mappedBy = "gameState", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Row> rows = new ArrayList<>();

	@Builder
	public GameState(Long id, Long previousGameStateId, Long nextGameStateId, Integer index, @NotEmpty List<Row> rows) {
		super(id);
		this.previousGameStateId = previousGameStateId;
		this.nextGameStateId = nextGameStateId;
		this.index = index;
		this.rows = rows;
	}

	public void addRow(Row row) {
		this.getRows().add(row);
		row.setGameState(this);
	}
}
