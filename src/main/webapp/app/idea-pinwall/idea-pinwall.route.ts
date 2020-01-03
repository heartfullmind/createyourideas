import { Route } from '@angular/router';

import { IdeaPinwallComponent } from './idea-pinwall.component';

export const IDEA_PINWALL_ROUTE: Route = {
  path: 'idea-pinwall',
  component: IdeaPinwallComponent,
  data: {
    authorities: [],
    pageTitle: 'ideapinwall.title'
  }
};
