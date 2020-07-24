import { NgModule } from '@angular/core';

import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { ApiService, LocalStorageService, GameApiService, ErrorHandlerService } from './services';
import { components } from './components/index';
import { ErrorComponent } from './components/error/error.component';

@NgModule({
  declarations: [...components, ErrorComponent],
  imports: [CommonModule, RouterModule],
  exports: [...components],
  providers: [ApiService, LocalStorageService, GameApiService, ErrorHandlerService],
})
export class CoreModule {}
