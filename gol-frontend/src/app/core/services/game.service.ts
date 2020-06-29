import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ApiService } from './api.service';

interface IGameTable {}

@Injectable({
  providedIn: 'root',
})
export class GameService {
  public constructor(public apiService: ApiService) {}

  startGame(gameTable: IGameTable): Observable<any> {
    return this.apiService
      .post<IGameTable>('start-game', gameTable)
      .pipe(catchError(() => of({ data: [], errors: ['Unexpected error!'] })));
  }
}
