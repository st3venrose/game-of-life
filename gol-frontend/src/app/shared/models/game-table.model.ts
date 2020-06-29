import { ITableRow } from '../types/index';

export interface IStartGame {
  tableRows: ITableRow[];
}

export interface IGameTable {
  id: number;
  previousGameTableId?: number;
  nextGameTableId?: number;
  tableRows: ITableRow[];
}
