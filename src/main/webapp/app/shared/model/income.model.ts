import { Moment } from 'moment';
import { IIdea } from 'app/shared/model/idea.model';

export interface IIncome {
  id?: number;
  description?: string;
  date?: Moment;
  value?: number;
  idea?: IIdea;
}

export class Income implements IIncome {
  constructor(public id?: number, public description?: string, public date?: Moment, public value?: number, public idea?: IIdea) {}
}
