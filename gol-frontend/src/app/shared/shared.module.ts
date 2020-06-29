import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { components } from './components';
import { ButtonComponent } from './components/button/button.component';

@NgModule({
  declarations: [...components, ButtonComponent],
  exports: [...components],
  imports: [CommonModule],
})
export class SharedModule {}
