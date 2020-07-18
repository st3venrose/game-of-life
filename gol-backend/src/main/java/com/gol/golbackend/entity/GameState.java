package com.gol.golbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "game_table")
public class GameState implements Serializable {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "previous_state_id")
	private Long previousGameStateId;

	@Column(name = "next_game_state_id")
	private Long nextGameStateId;

	@Column(name = "index")
	private Integer index;

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name = "game_state_state_id")
	private List<Row> rows = new ArrayList<>();
}
