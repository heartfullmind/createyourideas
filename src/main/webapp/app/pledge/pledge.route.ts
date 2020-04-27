import { Route } from '@angular/router';

import { PledgeComponent } from './pledge.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

export const PLEDGE_ROUTE: Route = {
  path: 'pledge',
  component: PledgeComponent,
  data: {
    authorities: [],
    pageTitle: 'pledge.title'
  },
  canActivate: [UserRouteAccessService]
};
