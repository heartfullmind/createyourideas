import { Route } from '@angular/router';


import { AboutUsComponent } from './about-us.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

export const ABOUT_US_ROUTE: Route = {
  path: 'about-us',
  component: AboutUsComponent,
  data: {
    authorities: [],
    pageTitle: 'about-us.title'
  },
  canActivate: [UserRouteAccessService]
};
