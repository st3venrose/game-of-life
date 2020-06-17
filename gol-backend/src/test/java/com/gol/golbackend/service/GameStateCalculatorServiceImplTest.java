package com.gol.golbackend.service;

import com.gol.golbackend.common.FieldStatus;
import com.gol.golbackend.entity.Field;
import com.gol.golbackend.entity.TableRow;
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
		final List<TableRow> newTableRows = subject.calculateNextGameState(this.createStarterGameSate());
		final List<TableRow> exceptedTableRows = this.createExceptedTableRow();

		for (int currentRow = 0; currentRow < newTableRows.size(); currentRow++) {
			for (int currentColumn = 0; currentColumn < newTableRows.get(currentRow).getRowFields().size(); currentColumn++) {
				final FieldStatus currentFieldStatus = newTableRows.get(currentRow).getRowFields().get(currentColumn).getFieldStatus();
				final FieldStatus exceptedFieldStatus = exceptedTableRows.get(currentRow).getRowFields().get(currentColumn).getFieldStatus();
				assertEquals(exceptedFieldStatus, currentFieldStatus);
			}
		}
	}

	private List<TableRow> createStarterGameSate() {
		final List<TableRow> tableRows = new ArrayList<>();
		TableRow row0 = new TableRow();
		TableRow row1 = new TableRow();
		TableRow row2 = new TableRow();
		TableRow row3 = new TableRow();

		row0.setRowFields(Arrays.asList(liveField, emptyField, liveField));
		row1.setRowFields(Arrays.asList(emptyField, liveField, emptyField));
		row2.setRowFields(Arrays.asList(liveField, liveField, emptyField));
		row3.setRowFields(Arrays.asList(emptyField, emptyField, emptyField));

		tableRows.addAll(Arrays.asList(row0, row1, row2, row3));

		return tableRows;
	}

	private List<TableRow> createExceptedTableRow() {
		final List<TableRow> tableRows = new ArrayList<>();
		TableRow row0 = new TableRow();
		TableRow row1 = new TableRow();
		TableRow row2 = new TableRow();
		TableRow row3 = new TableRow();

		row0.setRowFields(Arrays.asList(deadField, liveField, deadField));
		row1.setRowFields(Arrays.asList(emptyField, deadField, liveField));
		row2.setRowFields(Arrays.asList(liveField, liveField, liveField));
		row3.setRowFields(Arrays.asList(liveField, liveField, emptyField));

		tableRows.addAll(Arrays.asList(row0, row1, row2, row3));

		return tableRows;
	}
}
