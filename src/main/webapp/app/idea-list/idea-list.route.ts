import { Route } from '@angular/router';
import { IdeaListComponent } from './idea-list.component';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

export const IDEA_LIST_ROUTE: Route = {
  path: 'ownIdea',
  component: IdeaListComponent,
  resolve: {
    pagingParams: JhiResolvePagingParams
  },
  data: {
    authorities: ['ROLE_USER'],
    pageTitle: 'idea-list.title'
  },
  canActivate: [UserRouteAccessService]
};
