import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Idea } from 'app/shared/model/idea.model';
import { IdeaService } from './idea.service';
import { IdeaComponent } from './idea.component';
import { IdeaDetailComponent } from './idea-detail.component';
import { IdeaUpdateComponent } from './idea-update.component';
import { IIdea } from 'app/shared/model/idea.model';

@Injectable({ providedIn: 'root' })
export class IdeaResolve implements Resolve<IIdea> {
  constructor(private service: IdeaService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIdea> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((idea: HttpResponse<Idea>) => idea.body));
    }
    return of(new Idea());
  }
}

export const ideaRoute: Routes = [
  {
    path: '',
    component: IdeaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'homeApp.idea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IdeaDetailComponent,
    resolve: {
      idea: IdeaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.idea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'newIdea',
    component: IdeaUpdateComponent,
    resolve: {
      idea: IdeaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.idea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IdeaUpdateComponent,
    resolve: {
      idea: IdeaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.idea.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
