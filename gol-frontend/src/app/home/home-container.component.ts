import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { LocalStorageService } from '../core/services';
import { TABLE_SIZES, TABLE_SIZE_STOREGE_KEY, TABLE_ID_STOREGE_KEY } from '../shared/constants/constants';

@Component({
  selector: 'gol-home-container',
  templateUrl: './home-container.component.html',
  styleUrls: ['./home-container.component.scss'],
})
export class HomeContainerComponent {
  sizeList = TABLE_SIZES;
  selectedTableSize: number;

  constructor(private router: Router, private localStorageService: LocalStorageService) {
    this.selectedTableSize = this.localStorageService.get(TABLE_SIZE_STOREGE_KEY) || TABLE_SIZES[0];
  }

  startGame() {
    this.localStorageService.store(TABLE_SIZE_STOREGE_KEY, this.selectedTableSize);
    this.localStorageService.remove(TABLE_ID_STOREGE_KEY);
    this.router.navigate(['/game']);
  }

  tablesizeChanged(value: number) {
    this.selectedTableSize = value;
  }
}
