import { Injectable } from '@angular/core';
import { IRow, IField } from '../../shared/types/index';
import { Field } from '../../shared/enums/field.enum';

@Injectable()
export class GameTableGeneratorService {
  generateTable(tableSize: number): IRow[] {
    const tableRows = [];

    for (let i = 0; i < tableSize; i++) {
      tableRows.push({ fields: this.generateRows(tableSize) });
    }

    return tableRows;
  }

  private generateRows(tableSize: number): IField[] {
    const fields = [];

    for (let i = 0; i < tableSize; i++) {
      fields.push({ fieldStatus: Field.EMPTY });
    }

    return fields;
  }
}
