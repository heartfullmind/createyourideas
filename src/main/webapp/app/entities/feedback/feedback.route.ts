import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Feedback } from 'app/shared/model/feedback.model';
import { FeedbackService } from './feedback.service';
import { FeedbackComponent } from './feedback.component';
import { FeedbackDetailComponent } from './feedback-detail.component';
import { FeedbackUpdateComponent } from './feedback-update.component';
import { IFeedback } from 'app/shared/model/feedback.model';

@Injectable({ providedIn: 'root' })
export class FeedbackResolve implements Resolve<IFeedback> {
  constructor(private service: FeedbackService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFeedback> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((feedback: HttpResponse<Feedback>) => feedback.body));
    }
    return of(new Feedback());
  }
}

export const feedbackRoute: Routes = [
  {
    path: '',
    component: FeedbackComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.feedback.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FeedbackDetailComponent,
    resolve: {
      feedback: FeedbackResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.feedback.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'newFeedback',
    component: FeedbackUpdateComponent,
    resolve: {
      feedback: FeedbackResolve
    },
    data: {
      pageTitle: 'homeApp.feedback.home.title'
    }
  },
  {
    path: ':id/edit',
    component: FeedbackUpdateComponent,
    resolve: {
      feedback: FeedbackResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'homeApp.feedback.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
