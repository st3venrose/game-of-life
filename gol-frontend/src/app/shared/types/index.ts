import { Field } from '../enums/field.enum';

export interface IField {
  id?: string;
  fieldStatus: Field;
}

export interface ITableRow {
  id?: string;
  rowFieldsg: Field[];
}
