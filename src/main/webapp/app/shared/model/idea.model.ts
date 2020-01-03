import { IIncome } from 'app/shared/model/income.model';
import { IOutgoings } from 'app/shared/model/outgoings.model';
import { IWorksheet } from 'app/shared/model/worksheet.model';
import { IIdea } from 'app/shared/model/idea.model';
import { IUser } from 'app/core/user/user.model';
import { Ideatype } from 'app/shared/model/enumerations/ideatype.model';

export interface IIdea {
  id?: number;
  title?: string;
  logoContentType?: string;
  logo?: any;
  description?: any;
  ideatype?: Ideatype;
  interest?: number;
  investment?: number;
  active?: boolean;
  incomes?: IIncome[];
  outgoings?: IOutgoings[];
  worksheets?: IWorksheet[];
  parents?: IIdea[];
  user?: IUser;
  idea?: IIdea;
}

export class Idea implements IIdea {
  constructor(
    public id?: number,
    public title?: string,
    public logoContentType?: string,
    public logo?: any,
    public description?: any,
    public ideatype?: Ideatype,
    public interest?: number,
    public investment?: number,
    public active?: boolean,
    public incomes?: IIncome[],
    public outgoings?: IOutgoings[],
    public worksheets?: IWorksheet[],
    public parents?: IIdea[],
    public user?: IUser,
    public idea?: IIdea
  ) {
    this.active = this.active || false;
  }
}
