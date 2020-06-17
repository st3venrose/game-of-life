package com.gol.golbackend.service.impl;

import com.gol.golbackend.common.FieldStatus;
import com.gol.golbackend.entity.Field;
import com.gol.golbackend.entity.TableRow;
import com.gol.golbackend.service.GameStateCalculatorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameStateCalculatorServiceImpl implements GameStateCalculatorService {
    private static final int MIN_ALIVE_NEIGHBORS = 2;
    private static final int MAX_ALIVE_NEIGHBORS = 3;

    @Override
    public List<TableRow> calculateNextGameState(final List<TableRow> tableRows) {
        final int rowCount = tableRows.size();
        final int columnCount = tableRows.get(0).getRowFields().size();
        final List<TableRow> newTableRows = new ArrayList<>();

        for (int currentRow = 0; currentRow < rowCount; currentRow++) {
            newTableRows.add(new TableRow());
            for (int currentColumn = 0; currentColumn < columnCount; currentColumn++) {
                final int aliveNeighborsStateCount = countNeighborAliveFields(tableRows, rowCount, columnCount, currentRow, currentColumn);
                final Field field = new Field();
                final FieldStatus currentFieldStatus = tableRows.get(currentRow).getRowFields().get(currentColumn).getFieldStatus();
                final FieldStatus newFieldStatus = calculateFieldState(aliveNeighborsStateCount, currentFieldStatus);
                field.setFieldStatus(newFieldStatus);
                newTableRows.get(currentRow).getRowFields().add(field);
            }
        }

        return newTableRows;
    }

    private int countNeighborAliveFields(final List<TableRow> tableRows, final int rowCount, final int columnCount,
                                              final int originCellRow, final int originCellColumn) {
        final int neighborColumnLimit = Math.min(columnCount, originCellColumn + 2);
        final int neighborRowLimit = Math.min(rowCount, originCellRow + 2);
        int aliveNeighborsStateCount = 0;

        for (int neighborRow = Math.max(0, originCellRow - 1); neighborRow < neighborRowLimit; neighborRow++) {
            for (int neighborColumn = Math.max(0, originCellColumn - 1); neighborColumn < neighborColumnLimit; neighborColumn++) {
                if (neighborRow != originCellRow || neighborColumn != originCellColumn) {
                    final FieldStatus fieldState = tableRows.get(neighborRow).getRowFields().get(neighborColumn).getFieldStatus();

                    if (fieldState == FieldStatus.LIVE) {
                        aliveNeighborsStateCount++;
                    }
                }
            }
        }

        return aliveNeighborsStateCount;
    }

    private FieldStatus calculateFieldState(final int aliveNeighborsStateCount, final FieldStatus currentFieldState) {
        FieldStatus calculatedState = currentFieldState;
        final boolean isNotEmptyField = currentFieldState != FieldStatus.EMPTY;
        final boolean isLivableField = aliveNeighborsStateCount == MIN_ALIVE_NEIGHBORS || aliveNeighborsStateCount == MAX_ALIVE_NEIGHBORS;

        if (isLivableField) {
            calculatedState = FieldStatus.LIVE;
        } else if (isNotEmptyField) {
            calculatedState = FieldStatus.DEAD;
        }

        return calculatedState;
    }
}
