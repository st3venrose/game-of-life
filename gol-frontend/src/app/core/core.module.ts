import { NgModule } from '@angular/core';

import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { ApiService, GameService, LocalStorageService } from './services';
import { components } from './components/index';

@NgModule({
  declarations: [...components],
  imports: [CommonModule, RouterModule],
  exports: [...components],
  providers: [ApiService, GameService, LocalStorageService],
})
export class CoreModule {}
