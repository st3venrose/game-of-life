import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { components } from './components';
import { GameContainerComponent } from './game-container.component';
import { SharedModule } from '../shared/shared.module';
import { GameTableGeneratorService } from './services/game-table-generator.service';

@NgModule({
  declarations: [GameContainerComponent, ...components],
  imports: [CommonModule, SharedModule],
  providers: [GameTableGeneratorService],
})
export class GameModule {}
