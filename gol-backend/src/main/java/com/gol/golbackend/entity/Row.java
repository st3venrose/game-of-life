package com.gol.golbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "row")
public class Row extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "game_state_id")
	@JsonIgnore
	private GameState gameState;

	@NotEmpty
	@OneToMany(mappedBy = "row", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Field> fields = new ArrayList<>();

	public Row(List<Field> fields) {
		this.fields = fields;
	}
}
