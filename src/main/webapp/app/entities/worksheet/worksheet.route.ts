import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Worksheet } from 'app/shared/model/worksheet.model';
import { WorksheetService } from './worksheet.service';
import { WorksheetComponent } from './worksheet.component';
import { WorksheetDetailComponent } from './worksheet-detail.component';
import { WorksheetUpdateComponent } from './worksheet-update.component';
import { IWorksheet } from 'app/shared/model/worksheet.model';

@Injectable({ providedIn: 'root' })
export class WorksheetResolve implements Resolve<IWorksheet> {
  constructor(private service: WorksheetService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorksheet> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((worksheet: HttpResponse<Worksheet>) => worksheet.body));
    }
    return of(new Worksheet());
  }
}

export const worksheetRoute: Routes = [
  {
    path: '',
    component: WorksheetComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.worksheet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: WorksheetDetailComponent,
    resolve: {
      worksheet: WorksheetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.worksheet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: WorksheetUpdateComponent,
    resolve: {
      worksheet: WorksheetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.worksheet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: WorksheetUpdateComponent,
    resolve: {
      worksheet: WorksheetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.worksheet.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
