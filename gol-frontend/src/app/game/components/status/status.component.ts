import { Component, ChangeDetectionStrategy, Input } from '@angular/core';

@Component({
  selector: 'gol-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class StatusComponent {
  @Input() tableIndex: number;
}
