import { Route } from '@angular/router';
import { IdeaFunnelComponent } from './idea-funnel.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';


export const IDEA_FUNNEL_ROUTE: Route = {
  path: 'idea-funnel',
  component: IdeaFunnelComponent,
  data: {
    authorities: ['ROLE_USER'],
    pageTitle: 'home.title'
  },
  canActivate: [UserRouteAccessService]
};
