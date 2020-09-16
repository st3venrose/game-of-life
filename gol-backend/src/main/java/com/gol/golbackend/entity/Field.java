package com.gol.golbackend.entity;

import com.gol.golbackend.common.FieldStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "field")
public class Field extends BaseEntity {

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "field_status")
	private FieldStatus fieldStatus;

	@Builder
	public Field(Long id, FieldStatus fieldStatus) {
		super(id);
		this.fieldStatus = fieldStatus;
	}
}
