import { Moment } from 'moment';

export interface IFeedback {
  id?: number;
  creationDate?: Moment;
  name?: string;
  email?: string;
  feedback?: any;
}

export class Feedback implements IFeedback {
  constructor(public id?: number, public creationDate?: Moment, public name?: string, public email?: string, public feedback?: any) {}
}
