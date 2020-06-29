import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';

import { HomeContainerComponent } from './home-container.component';

@NgModule({
  declarations: [HomeContainerComponent],
  imports: [CommonModule, SharedModule],
})
export class HomeModule {}
