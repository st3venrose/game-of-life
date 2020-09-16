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
@Table(name = "row")
public class Row extends BaseEntity {

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "game_state_id", referencedColumnName = "id", nullable = false)
	@NotEmpty
	private List<Field> fields = new ArrayList<>();

	@Builder
	public Row(Long id, @NotEmpty List<Field> fields) {
		super(id);
		this.fields = fields;
	}
}
