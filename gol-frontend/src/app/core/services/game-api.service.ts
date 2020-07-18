import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ApiService } from './api.service';
import { IRow } from '../../shared/types/index';
import { IGameState } from '../../shared/models/game-table.model';

@Injectable({
  providedIn: 'root',
})
export class GameApiService {
  public constructor(private apiService: ApiService) {}

  public startGame(rows: IRow[]): Observable<IGameState> {
    return this.apiService.post<IGameState>('start-game', { rows: rows });
  }

  public fetchGameState(id: number): Observable<IGameState> {
    return this.apiService.get<IGameState>(`game-state/${id}`);
  }

  public fetchNewGameState(id: number): Observable<IGameState> {
    return this.apiService.get<IGameState>(`new-game-state/${id}`);
  }
}
