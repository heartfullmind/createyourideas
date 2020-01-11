import { Injectable } from '@angular/core';
import { HttpResponse, HttpClient } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';

type EntityResponseType = HttpResponse<number>;
type EntityArrayResponseType = HttpResponse<number[]>;

@Injectable({
  providedIn: 'root'
})
export class CalculationIdeaFunnelService {
  public resourceUrl = SERVER_API_URL + 'api/calculation';
  constructor(protected http: HttpClient) { }

  calculateProfit(id: number): Observable<EntityResponseType> {
    return this.http.get<number>(`${this.resourceUrl}/${id}/interest`, { observe: 'response' });
  }
}
