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
				Row.builder().fields(Arrays.asList(live, empty, live)).build(),
				Row.builder().fields(Arrays.asList(empty, live, empty)).build(),
				Row.builder().fields(Arrays.asList(live, live, empty)).build(),
				Row.builder().fields(Arrays.asList(empty, empty, empty)).build()
		);
	}

	private List<Row> createExceptedTableRow() {
		return Arrays.asList(
				Row.builder().fields(Arrays.asList(dead, live, dead)).build(),
				Row.builder().fields(Arrays.asList(empty, dead, live)).build(),
				Row.builder().fields(Arrays.asList(live, live, live)).build(),
				Row.builder().fields(Arrays.asList(live, live, empty)).build()
		);
	}
}
