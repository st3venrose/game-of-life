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
public class GameTable implements Serializable {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "previous_game_table_id")
	private Long previousGameTableId;

	@Column(name = "next_game_table_id")
	private Long nextGameTableId;

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "tableId", referencedColumnName = "id", nullable = false)
	private List<TableRow> tableRows = new ArrayList<>();
}
