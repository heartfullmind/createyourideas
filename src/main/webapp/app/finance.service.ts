import { OutgoingsService } from 'app/entities/outgoings/outgoings.service';
import { IncomeService } from 'app/entities/income/income.service';
import { Injectable } from '@angular/core';
import { HttpResponse, HttpClient } from '@angular/common/http';
import { SERVER_API_URL } from './app.constants';
import { Observable } from 'rxjs';
import { createRequestOption } from './shared/util/request-util';


@Injectable({ providedIn: 'root' })
export class FinanceService {

  public resourceUrl = SERVER_API_URL + 'api/calculation';

  constructor(protected http: HttpClient) {}

  getDailyBalance(id: number, req?: any): Observable<HttpResponse<number>> {
    const options = createRequestOption(req);
    return this.http
      .get<number>(`${this.resourceUrl}/${id}/dailyBalance`, { params: options, observe: 'response' });
  }
}
