import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Balance } from 'app/shared/model/balance.model';
import { BalanceService } from './balance.service';
import { BalanceComponent } from './balance.component';
import { BalanceDetailComponent } from './balance-detail.component';
import { BalanceUpdateComponent } from './balance-update.component';
import { IBalance } from 'app/shared/model/balance.model';

@Injectable({ providedIn: 'root' })
export class BalanceResolve implements Resolve<IBalance> {
  constructor(private service: BalanceService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBalance> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((balance: HttpResponse<Balance>) => balance.body));
    }
    return of(new Balance());
  }
}

export const balanceRoute: Routes = [
  {
    path: '',
    component: BalanceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.balance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BalanceDetailComponent,
    resolve: {
      balance: BalanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.balance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BalanceUpdateComponent,
    resolve: {
      balance: BalanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.balance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BalanceUpdateComponent,
    resolve: {
      balance: BalanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.balance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
