import { Injectable, OnDestroy } from '@angular/core';
import { Observable, BehaviorSubject, Subject, throwError } from 'rxjs';
import { takeUntil, tap, catchError } from 'rxjs/operators';
import { GameApiService, LocalStorageService, ErrorHandlerService } from '../../core/services';
import { TABLE_ID_STOREGE_KEY } from '../../shared/constants/constants';
import { IGameState } from '../../shared/models/game-table.model';
import { IFieldPosition } from '../types/index';
import { Field } from '../../shared/enums/field.enum';

@Injectable()
export class GameStateStoreService implements OnDestroy {
  private gameState$: BehaviorSubject<IGameState>;

  private destroy$: Subject<void> = new Subject<void>();

  constructor(
    private gameApiService: GameApiService,
    private localStorageService: LocalStorageService,
    private errorHandlerService: ErrorHandlerService
  ) {
    this.gameState$ = new BehaviorSubject({
      id: NaN,
      rows: null,
    });
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.unsubscribe();
  }

  getGameStateModel(): BehaviorSubject<IGameState> {
    return this.gameState$;
  }

  updateGameStateModel(gameState: IGameState): void {
    this.gameState$.next(gameState);
    this.localStorageService.store(TABLE_ID_STOREGE_KEY, gameState.id);
  }

  updateFieldState(fieldPosition: IFieldPosition, newfieldStatus: Field): void {
    const gameState = this.gameState$.getValue();
    const rows = gameState.rows;
    rows[fieldPosition.i].fields[fieldPosition.j].fieldStatus = newfieldStatus;
    this.gameState$.next({ ...gameState, rows });
  }

  startNewGame(): Observable<IGameState> {
    const gameState = this.gameState$.getValue();
    return this.gameApiService.startGame(gameState.rows).pipe(
      tap((gameState) => this.updateGameStateModel(gameState)),
      this.handleError()
    );
  }

  getPreviousGameState(): void {
    const gameState = this.gameState$.getValue();
    this.getGameState(gameState.previousGameStateId).pipe(takeUntil(this.destroy$)).subscribe();
  }

  getNextGameState(id?: number): Observable<IGameState> {
    return this.getGameState(id || this.gameState$.getValue().nextGameStateId);
  }

  getNewGameState(id?: number): Observable<IGameState> {
    const tableId = id || this.gameState$.getValue().id;
    return this.gameApiService.fetchNewGameState(tableId).pipe(
      tap((gameState) => this.updateGameStateModel(gameState)),
      this.handleError()
    );
  }

  getGameState(id: number): Observable<IGameState> {
    return this.gameApiService.fetchGameState(id).pipe(
      tap((gameState) => this.updateGameStateModel(gameState)),
      this.handleError()
    );
  }

  private handleError() {
    return <T>(source: Observable<T>) => {
      return source.pipe(
        catchError((err) => {
          this.errorHandlerService.handleError();
          return throwError(err);
        })
      );
    };
  }
}
