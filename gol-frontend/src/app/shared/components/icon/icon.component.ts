import { Component, Input, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'gol-icon',
  templateUrl: './icon.component.html',
  styleUrls: ['./icon.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class IconComponent {
  private SPRITE_URL = 'assets/svg/sprite.symbol.svg';
  private _name = '';

  @Input()
  set name(name: string) {
    this._name = `${this.SPRITE_URL}#${name}`;
  }

  get name(): string {
    return this._name;
  }
}
