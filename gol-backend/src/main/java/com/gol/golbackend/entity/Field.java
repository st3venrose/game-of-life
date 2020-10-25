package com.gol.golbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gol.golbackend.common.FieldStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "field")
public class Field extends BaseEntity {

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "field_status")
	private FieldStatus fieldStatus;


	@ManyToOne
	@JoinColumn(name = "row_id")
	@JsonIgnore
	private Row row;

	public Field(FieldStatus fieldStatus) {
		this.fieldStatus = fieldStatus;
	}
}
