import { Component, Input, Output, EventEmitter, ChangeDetectionStrategy } from '@angular/core';
import { IRow, IField } from '../../../shared/types/index';
import { Field } from '../../../shared/enums/field.enum';
import { IFieldPosition } from '../../types/index';

@Component({
  selector: 'gol-game-table',
  templateUrl: './game-table.component.html',
  styleUrls: ['./game-table.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class GameTableComponent {
  @Input() gameState: IRow[];

  @Output() onSelectField = new EventEmitter<IFieldPosition>();

  selectField(i: number, j: number): void {
    this.onSelectField.emit({ j, i });
  }

  getFieldClass(fieldStatus: Field): string {
    switch (fieldStatus) {
      case Field.EMPTY:
        return 'table__field--empty';
      case Field.LIVE:
        return 'table__field--live';
      case Field.DEAD:
        return 'table__field--dead';
      default:
        return '';
    }
  }
}
