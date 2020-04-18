import { Moment } from 'moment';
import { IIdea } from 'app/shared/model/idea.model';

export interface IBalance {
  id?: number;
  dailyBalance?: number;
  profit?: number;
  profitToSpend?: number;
  netProfit?: number;
  date?: Moment;
  idea?: IIdea;
}

export class Balance implements IBalance {
  constructor(
    public id?: number,
    public dailyBalance?: number,
    public profit?: number,
    public profitToSpend?: number,
    public netProfit?: number,
    public date?: Moment,
    public idea?: IIdea
  ) {}
}
