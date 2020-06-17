package com.gol.golbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gol.golbackend.common.FieldStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "field")
public class Field implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "field_status")
	private FieldStatus fieldStatus;


	public Field(FieldStatus fieldStatus) {
		this.fieldStatus = fieldStatus;
	}
}
