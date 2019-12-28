import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HomeSharedModule } from 'app/shared/shared.module';
import { IDEA_FUNNEL_ROUTE } from './idea-funnel.route';
import { IdeaFunnelComponent } from './idea-funnel.component';

@NgModule({
  imports: [HomeSharedModule, RouterModule.forChild([IDEA_FUNNEL_ROUTE])],
  declarations: [IdeaFunnelComponent]
})
export class IdeaFunnelModule {}
