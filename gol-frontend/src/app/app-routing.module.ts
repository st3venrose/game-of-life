import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { GameGuard } from './core/guard/game.guard';

import { HomeContainerComponent } from './home/home-container.component';
import { GameContainerComponent } from './game/game-container.component';

const routes: Routes = [
  {
    path: '',
    component: HomeContainerComponent,
  },
  {
    path: 'game',
    component: GameContainerComponent,
    canActivate: [GameGuard],
  },
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
