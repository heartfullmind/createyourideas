import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IIdea } from 'app/shared/model/idea.model';

export interface IWorksheet {
  id?: number;
  jobtitle?: string;
  jobdescription?: any;
  date?: Moment;
  costHour?: number;
  hours?: number;
  total?: number;
  user?: IUser;
  idea?: IIdea;
}

export class Worksheet implements IWorksheet {
  constructor(
    public id?: number,
    public jobtitle?: string,
    public jobdescription?: any,
    public date?: Moment,
    public costHour?: number,
    public hours?: number,
    public total?: number,
    public user?: IUser,
    public idea?: IIdea
  ) {}
}
