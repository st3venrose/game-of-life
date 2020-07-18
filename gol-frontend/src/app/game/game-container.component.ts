import { Component, OnInit } from '@angular/core';
import { GameService, IGameModel } from './services/game.service';
import { GameStateStoreService } from './services/game-state-store.service';
import { IFieldPosition } from './types/index';

@Component({
  selector: 'gol-game-container',
  templateUrl: './game-container.component.html',
  styleUrls: ['./game-container.component.scss'],
  providers: [GameService, GameStateStoreService],
})
export class GameContainerComponent implements OnInit {
  gameModel: IGameModel;

  constructor(private gameService: GameService) {
    this.gameModel = this.gameService.getGameModel();
  }

  ngOnInit(): void {
    this.gameService.loadGameState();
  }

  selectField(fieldPosition: IFieldPosition): void {
    this.gameService.updateFieldState(fieldPosition);
  }

  playGame(): void {
    this.gameService.playGame();
  }

  previousGameState(): void {
    this.gameService.previousGameState();
  }

  nextGameState(): void {
    this.gameService.nextGameState();
  }

  pauseGame() {
    this.gameService.pauseGame();
  }
}
