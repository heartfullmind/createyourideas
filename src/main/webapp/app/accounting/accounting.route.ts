import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { AccountingComponent } from './accounting.component';
import { JhiResolvePagingParams } from 'ng-jhipster';


export const ACCOUNTING_ROUTE: Route = {
  path: 'accounting',
  component: AccountingComponent,
  resolve: {
    pagingParams: JhiResolvePagingParams
  },
  data: {
    authorities: [],
    pageTitle: 'about-us.title'
  },
  canActivate: [UserRouteAccessService]
};
