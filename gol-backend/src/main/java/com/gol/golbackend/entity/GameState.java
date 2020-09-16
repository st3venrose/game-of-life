package com.gol.golbackend.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
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

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name = "game_state_id")
	@NotEmpty
	private List<Row> rows = new ArrayList<>();

	@Builder
	public GameState(Long id, Long previousGameStateId, Long nextGameStateId, Integer index, @NotEmpty List<Row> rows) {
		super(id);
		this.previousGameStateId = previousGameStateId;
		this.nextGameStateId = nextGameStateId;
		this.index = index;
		this.rows = rows;
	}
}
