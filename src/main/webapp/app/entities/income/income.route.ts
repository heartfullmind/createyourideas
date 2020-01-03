import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Income } from 'app/shared/model/income.model';
import { IncomeService } from './income.service';
import { IncomeComponent } from './income.component';
import { IncomeDetailComponent } from './income-detail.component';
import { IncomeUpdateComponent } from './income-update.component';
import { IIncome } from 'app/shared/model/income.model';

@Injectable({ providedIn: 'root' })
export class IncomeResolve implements Resolve<IIncome> {
  constructor(private service: IncomeService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIncome> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((income: HttpResponse<Income>) => income.body));
    }
    return of(new Income());
  }
}

export const incomeRoute: Routes = [
  {
    path: '',
    component: IncomeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.income.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IncomeDetailComponent,
    resolve: {
      income: IncomeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.income.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IncomeUpdateComponent,
    resolve: {
      income: IncomeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.income.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IncomeUpdateComponent,
    resolve: {
      income: IncomeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.income.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
