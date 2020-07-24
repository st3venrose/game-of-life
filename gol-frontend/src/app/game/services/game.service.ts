import { Injectable, OnDestroy } from '@angular/core';
import { Observable, BehaviorSubject, Subject, timer, forkJoin, of } from 'rxjs';
import { takeUntil, expand, delay, catchError } from 'rxjs/operators';
import { LocalStorageService } from '../../core/services';
import { GameTableGeneratorService } from './game-table-generator.service';
import { GameStateStoreService } from './game-state-store.service';
import { TABLE_SIZE_STOREGE_KEY, HTTP_CALLS_DELAY_IN_MS, TABLE_ID_STOREGE_KEY } from '../../shared/constants/constants';
import { IGameState } from '../../shared/models/game-table.model';
import { IFieldPosition } from '../types/index';
import { Field } from '../../shared/enums/field.enum';

export interface IGameModel {
  gameState$: BehaviorSubject<IGameState>;
  hasPreviousGameState$: BehaviorSubject<boolean>;
  hasNextGameState$: BehaviorSubject<boolean>;
  gameIsRunning$: BehaviorSubject<boolean>;
}

@Injectable()
export class GameService implements OnDestroy {
  private hasPreviousGameState$: BehaviorSubject<boolean> = new BehaviorSubject(false);

  private hasNextGameState$: BehaviorSubject<boolean> = new BehaviorSubject(false);

  private gameAlreadyStarted$: BehaviorSubject<boolean> = new BehaviorSubject(false);

  private gameIsRunning$: BehaviorSubject<boolean> = new BehaviorSubject(false);

  private runGame$: Subject<boolean>;

  private destroy$: Subject<void> = new Subject<void>();

  constructor(
    private gameStateStoreService: GameStateStoreService,
    private gameTableGeneratorService: GameTableGeneratorService,
    private localStorageService: LocalStorageService
  ) {
    this.subscribeGameStateChange();
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.unsubscribe();
    this.pauseGame();
  }

  loadGameState(): void {
    const lastStateId = this.localStorageService.get(TABLE_ID_STOREGE_KEY);

    if (lastStateId) {
      this.gameStateStoreService.getGameState(lastStateId).pipe(takeUntil(this.destroy$)).subscribe();
      this.gameAlreadyStarted$.next(true);
    } else {
      const tableSize = this.localStorageService.get(TABLE_SIZE_STOREGE_KEY);
      const gameState = this.gameStateStoreService.getGameStateModel().getValue();
      const rows = this.gameTableGeneratorService.generateTable(tableSize);

      this.gameStateStoreService.updateGameStateModel({ ...gameState, rows });
    }
  }

  getGameModel(): IGameModel {
    return {
      gameState$: this.gameStateStoreService.getGameStateModel(),
      hasPreviousGameState$: this.hasPreviousGameState$,
      hasNextGameState$: this.hasNextGameState$,
      gameIsRunning$: this.gameIsRunning$,
    };
  }

  playGame(): void {
    if (!this.gameIsRunning$.getValue()) {
      if (this.gameAlreadyStarted$.getValue()) {
        this.fetchStateWithTimer();
      } else {
        this.gameAlreadyStarted$.next(true);
        this.startNewGame();
      }
    }
  }

  nextGameState(): void {
    this.gameStateStoreService.getNextGameState().pipe(takeUntil(this.destroy$)).subscribe();
  }

  updateFieldState(fieldPosition: IFieldPosition): void {
    if (!this.gameAlreadyStarted$.getValue()) {
      const gameState = this.gameStateStoreService.getGameStateModel().getValue();
      const fieldStatus = gameState.rows[fieldPosition.i].fields[fieldPosition.j].fieldStatus;
      const newFieldStatus = fieldStatus === Field.LIVE ? Field.EMPTY : Field.LIVE;

      this.gameStateStoreService.updateFieldState(fieldPosition, newFieldStatus);
    }
  }

  previousGameState(): void {
    if (this.hasPreviousGameState$.getValue()) {
      this.gameStateStoreService.getPreviousGameState();
    }
  }

  pauseGame(): void {
    if (this.gameIsRunning$.getValue()) {
      this.runGame$.next();
      this.runGame$.unsubscribe();
    }
    this.gameIsRunning$.next(false);
    this.runGame$ = null;
  }

  private startNewGame(): void {
    this.gameStateStoreService
      .startNewGame()
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => {
        this.fetchStateWithTimer();
      });
  }

  private subscribeGameStateChange(): void {
    this.gameStateStoreService
      .getGameStateModel()
      .pipe(takeUntil(this.destroy$))
      .subscribe((gameState) => {
        this.hasPreviousGameState$.next(gameState.previousGameStateId != null);
        this.hasNextGameState$.next(gameState.nextGameStateId != null);
      });
  }

  private fetchStateWithTimer(): void {
    this.runGame$ = new Subject();
    this.gameIsRunning$.next(true);

    of(undefined)
      .pipe(
        delay(HTTP_CALLS_DELAY_IN_MS),
        expand(() => forkJoin([timer(HTTP_CALLS_DELAY_IN_MS), this.getGameState()])),
        takeUntil(this.runGame$),
        catchError((err) => {
          this.pauseGame();
          return of();
        })
      )
      .subscribe();
  }

  private getGameState(): Observable<IGameState> {
    const gameState = this.gameStateStoreService.getGameStateModel().getValue();
    const getNextGameState$ = this.hasNextGameState$.getValue()
      ? this.gameStateStoreService.getNextGameState(gameState.nextGameStateId)
      : this.gameStateStoreService.getNewGameState(gameState.id);

    return getNextGameState$;
  }
}
