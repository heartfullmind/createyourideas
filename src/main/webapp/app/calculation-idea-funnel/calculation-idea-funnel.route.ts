import { Route } from '@angular/router';
import { CalculationIdeaFunnelComponent } from './calculation-idea-funnel.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';


export const CALCULATION_IDEA_FUNNEL_ROUTE: Route = {
  path: 'calculate',
  component: CalculationIdeaFunnelComponent,
  data: {
    authorities: [],
    pageTitle: 'calculation-idea-funnel.title'
  },
  canActivate: [UserRouteAccessService]
};
