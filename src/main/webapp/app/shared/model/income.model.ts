import { Moment } from 'moment';
import { IIdea } from 'app/shared/model/idea.model';

export interface IIncome {
  id?: number;
  title?: string;
  description?: string;
  date?: Moment;
  value?: number;
  idea?: IIdea;
}

export class Income implements IIncome {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public date?: Moment,
    public value?: number,
    public idea?: IIdea
  ) {}
}
