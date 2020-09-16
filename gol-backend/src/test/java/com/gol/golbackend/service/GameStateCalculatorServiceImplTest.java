package com.gol.golbackend.service;

import com.gol.golbackend.common.FieldStatus;
import com.gol.golbackend.entity.Field;
import com.gol.golbackend.entity.Row;
import com.gol.golbackend.service.impl.GameStateCalculatorServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GameStateCalculatorServiceImplTest {

	@InjectMocks
	private GameStateCalculatorServiceImpl subject;

	private static final Field live = new Field(FieldStatus.LIVE);
	private static final Field empty = new Field(FieldStatus.EMPTY);
	private static final Field dead = new Field(FieldStatus.DEAD);

	@Test
	public void changedGameStateTest() {
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
		return Arrays.asList(
				new Row(Arrays.asList(live, empty, live)),
				new Row(Arrays.asList(empty, live, empty)),
				new Row(Arrays.asList(live, live, empty)),
				new Row(Arrays.asList(empty, empty, empty))
		);
	}

	private List<Row> createExceptedTableRow() {
		return Arrays.asList(
				new Row(Arrays.asList(dead, live, dead)),
				new Row(Arrays.asList(empty, dead, live)),
				new Row(Arrays.asList(live, live, live)),
				new Row(Arrays.asList(live, live, empty))
		);
	}
}
