import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeContainerComponent } from './home/home-container.component';
import { GameContainerComponent } from './game/game-container.component';

const routes: Routes = [
  {
    path: 'home',
    component: HomeContainerComponent,
  },
  {
    path: 'game',
    component: GameContainerComponent,
  },
  { path: '**', redirectTo: 'home' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
