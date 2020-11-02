package com.gol.golbackend.service;

import com.gol.golbackend.common.FieldStatus;
import com.gol.golbackend.entity.Field;
import com.gol.golbackend.entity.Row;
import com.gol.golbackend.service.impl.GameStateCalculatorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GameStateCalculatorServiceImplTest {

	@InjectMocks
	private GameStateCalculatorServiceImpl subject;

	private static final Field live = new Field(FieldStatus.LIVE);
	private static final Field empty = new Field(FieldStatus.EMPTY);
	private static final Field dead = new Field(FieldStatus.DEAD);

	@Test
	void changedGameStateTest() {
		final List<Row> newTableRows = subject.calculateNextGameState(this.createStarterGameSate());
		final List<Row> exceptedTableRows = this.createExceptedTableRow();

		for (int currentRow = 0; currentRow < newTableRows.size(); currentRow++) {
			for (int currentColumn = 0; currentColumn < newTableRows.get(currentRow).getFields().size(); currentColumn++) {
				final FieldStatus currentFieldStatus = newTableRows.get(currentRow).getFields().get(currentColumn).getFieldStatus();
				final FieldStatus exceptedFieldStatus = exceptedTableRows.get(currentRow).getFields().get(currentColumn).getFieldStatus();

				assertEquals(exceptedFieldStatus, currentFieldStatus);
			}
		}
	}

	private List<Row> createStarterGameSate() {
		return List.of(
				new Row(List.of(live, empty, live)),
				new Row(List.of(empty, live, empty)),
				new Row(List.of(live, live, empty)),
				new Row(List.of(empty, empty, empty))
		);
	}

	private List<Row> createExceptedTableRow() {
		return List.of(
				new Row(List.of(dead, live, dead)),
				new Row(List.of(empty, dead, live)),
				new Row(List.of(live, live, live)),
				new Row(List.of(live, live, empty))
		);
	}
}
