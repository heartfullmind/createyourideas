import { Route } from '@angular/router';

import { IdeaPinwallComponent } from './idea-pinwall.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

export const IDEA_PINWALL_ROUTE: Route = {
  path: 'idea-pinwall',
  component: IdeaPinwallComponent,
  data: {
    authorities: [],
    pageTitle: 'homeApp.ideapinwall.home.title'
  },
  canActivate: [UserRouteAccessService]
};
