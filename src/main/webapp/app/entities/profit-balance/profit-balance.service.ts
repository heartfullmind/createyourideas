import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProfitBalance } from 'app/shared/model/profit-balance.model';

type EntityResponseType = HttpResponse<IProfitBalance>;
type EntityArrayResponseType = HttpResponse<IProfitBalance[]>;

@Injectable({ providedIn: 'root' })
export class ProfitBalanceService {
  public resourceUrl = SERVER_API_URL + 'api/profit-balances';

  constructor(protected http: HttpClient) {}

  create(profitBalance: IProfitBalance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(profitBalance);
    return this.http
      .post<IProfitBalance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(profitBalance: IProfitBalance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(profitBalance);
    return this.http
      .put<IProfitBalance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProfitBalance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProfitBalance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(profitBalance: IProfitBalance): IProfitBalance {
    const copy: IProfitBalance = Object.assign({}, profitBalance, {
      date: profitBalance.date != null && profitBalance.date.isValid() ? profitBalance.date.format(DATE_FORMAT) : null
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
      res.body.forEach((profitBalance: IProfitBalance) => {
        profitBalance.date = profitBalance.date != null ? moment(profitBalance.date) : null;
      });
    }
    return res;
  }
}
