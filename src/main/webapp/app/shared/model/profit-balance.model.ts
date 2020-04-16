import { Moment } from 'moment';
import { IIdea } from 'app/shared/model/idea.model';

export interface IProfitBalance {
  id?: number;
  profit?: number;
  profitToSpend?: number;
  netProfit?: number;
  date?: Moment;
  idea?: IIdea;
}

export class ProfitBalance implements IProfitBalance {
  constructor(
    public id?: number,
    public profit?: number,
    public profitToSpend?: number,
    public netProfit?: number,
    public date?: Moment,
    public idea?: IIdea
  ) {}
}
