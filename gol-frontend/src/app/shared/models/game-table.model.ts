import { IRow } from '../types/index';

export interface IStartGame {
  rows: IRow[];
}

export interface IGameState {
  id: number;
  previousGameStateId?: number;
  nextGameStateId?: number;
  rows: IRow[];
}
