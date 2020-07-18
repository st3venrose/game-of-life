import { Component, Input, Output, EventEmitter, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'gol-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ButtonComponent {
  @Input() text: string = '';

  @Input() icon: string = '';

  @Input() isDisabled: boolean = false;

  @Output() click: EventEmitter<MouseEvent> = new EventEmitter();

  onButtonClick(event: MouseEvent) {
    event.stopPropagation();

    if (!this.isDisabled) {
      this.click.emit(event);
    }
  }

  onChildElementClick(event: MouseEvent) {
    event.stopPropagation();
    this.onButtonClick(event);
  }
}
