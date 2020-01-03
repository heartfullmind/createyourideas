import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOutgoings } from 'app/shared/model/outgoings.model';

type EntityResponseType = HttpResponse<IOutgoings>;
type EntityArrayResponseType = HttpResponse<IOutgoings[]>;

@Injectable({ providedIn: 'root' })
export class OutgoingsService {
  public resourceUrl = SERVER_API_URL + 'api/outgoings';

  constructor(protected http: HttpClient) {}

  create(outgoings: IOutgoings): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(outgoings);
    return this.http
      .post<IOutgoings>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(outgoings: IOutgoings): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(outgoings);
    return this.http
      .put<IOutgoings>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOutgoings>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOutgoings[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryByIdeaId(id: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOutgoings[]>(`${this.resourceUrl}/${id}/allByIdeaId`, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(outgoings: IOutgoings): IOutgoings {
    const copy: IOutgoings = Object.assign({}, outgoings, {
      date: outgoings.date != null && outgoings.date.isValid() ? outgoings.date.format(DATE_FORMAT) : null
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
      res.body.forEach((outgoings: IOutgoings) => {
        outgoings.date = outgoings.date != null ? moment(outgoings.date) : null;
      });
    }
    return res;
  }
}
