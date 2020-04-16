import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProfitBalance } from 'app/shared/model/profit-balance.model';
import { ProfitBalanceService } from './profit-balance.service';
import { ProfitBalanceComponent } from './profit-balance.component';
import { ProfitBalanceDetailComponent } from './profit-balance-detail.component';
import { ProfitBalanceUpdateComponent } from './profit-balance-update.component';
import { IProfitBalance } from 'app/shared/model/profit-balance.model';

@Injectable({ providedIn: 'root' })
export class ProfitBalanceResolve implements Resolve<IProfitBalance> {
  constructor(private service: ProfitBalanceService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProfitBalance> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((profitBalance: HttpResponse<ProfitBalance>) => profitBalance.body));
    }
    return of(new ProfitBalance());
  }
}

export const profitBalanceRoute: Routes = [
  {
    path: '',
    component: ProfitBalanceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.profitBalance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProfitBalanceDetailComponent,
    resolve: {
      profitBalance: ProfitBalanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.profitBalance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'newProfitBalance',
    component: ProfitBalanceUpdateComponent,
    resolve: {
      profitBalance: ProfitBalanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.profitBalance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProfitBalanceUpdateComponent,
    resolve: {
      profitBalance: ProfitBalanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.profitBalance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
