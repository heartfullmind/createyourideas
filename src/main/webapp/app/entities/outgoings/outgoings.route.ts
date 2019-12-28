import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Outgoings } from 'app/shared/model/outgoings.model';
import { OutgoingsService } from './outgoings.service';
import { OutgoingsComponent } from './outgoings.component';
import { OutgoingsDetailComponent } from './outgoings-detail.component';
import { OutgoingsUpdateComponent } from './outgoings-update.component';
import { IOutgoings } from 'app/shared/model/outgoings.model';

@Injectable({ providedIn: 'root' })
export class OutgoingsResolve implements Resolve<IOutgoings> {
  constructor(private service: OutgoingsService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOutgoings> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((outgoings: HttpResponse<Outgoings>) => outgoings.body));
    }
    return of(new Outgoings());
  }
}

export const outgoingsRoute: Routes = [
  {
    path: '',
    component: OutgoingsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'homeApp.outgoings.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OutgoingsDetailComponent,
    resolve: {
      outgoings: OutgoingsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.outgoings.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OutgoingsUpdateComponent,
    resolve: {
      outgoings: OutgoingsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.outgoings.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OutgoingsUpdateComponent,
    resolve: {
      outgoings: OutgoingsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.outgoings.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
