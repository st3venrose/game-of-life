import { Component, Input } from '@angular/core';

@Component({
  selector: 'gol-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.scss'],
})
export class ButtonComponent {
  @Input() text: string = '';
}
