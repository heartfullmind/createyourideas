import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIncome } from 'app/shared/model/income.model';

type EntityResponseType = HttpResponse<IIncome>;
type EntityArrayResponseType = HttpResponse<IIncome[]>;

@Injectable({ providedIn: 'root' })
export class IncomeService {
  public resourceUrl = SERVER_API_URL + 'api/incomes';

  constructor(protected http: HttpClient) {}

  create(income: IIncome): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(income);
    return this.http
      .post<IIncome>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(income: IIncome): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(income);
    return this.http
      .put<IIncome>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIncome>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIncome[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(income: IIncome): IIncome {
    const copy: IIncome = Object.assign({}, income, {
      date: income.date != null && income.date.isValid() ? income.date.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date != null ? moment(res.body.date) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((income: IIncome) => {
        income.date = income.date != null ? moment(income.date) : null;
      });
    }
    return res;
  }
}
