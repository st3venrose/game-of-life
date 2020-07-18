package com.gol.golbackend.service;

import com.gol.golbackend.common.FieldStatus;
import com.gol.golbackend.entity.Field;
import com.gol.golbackend.entity.Row;
import com.gol.golbackend.service.impl.GameStateCalculatorServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GameStateCalculatorServiceImplTest {

	@InjectMocks
	private GameStateCalculatorServiceImpl subject;

	private static final Field liveField = new Field(FieldStatus.LIVE);
	private static final Field emptyField = new Field(FieldStatus.EMPTY);
	private static final Field deadField = new Field(FieldStatus.DEAD);

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
		final List<Row> tableRows = new ArrayList<>();
		Row row0 = new Row();
		Row row1 = new Row();
		Row row2 = new Row();
		Row row3 = new Row();

		row0.setFields(Arrays.asList(liveField, emptyField, liveField));
		row1.setFields(Arrays.asList(emptyField, liveField, emptyField));
		row2.setFields(Arrays.asList(liveField, liveField, emptyField));
		row3.setFields(Arrays.asList(emptyField, emptyField, emptyField));

		tableRows.addAll(Arrays.asList(row0, row1, row2, row3));

		return tableRows;
	}

	private List<Row> createExceptedTableRow() {
		final List<Row> tableRows = new ArrayList<>();
		Row row0 = new Row();
		Row row1 = new Row();
		Row row2 = new Row();
		Row row3 = new Row();

		row0.setFields(Arrays.asList(deadField, liveField, deadField));
		row1.setFields(Arrays.asList(emptyField, deadField, liveField));
		row2.setFields(Arrays.asList(liveField, liveField, liveField));
		row3.setFields(Arrays.asList(liveField, liveField, emptyField));

		tableRows.addAll(Arrays.asList(row0, row1, row2, row3));

		return tableRows;
	}
}
