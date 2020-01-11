import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HomeSharedModule } from 'app/shared/shared.module';
import { CALCULATION_IDEA_FUNNEL_ROUTE } from './calculation-idea-funnel.route';
import { CalculationIdeaFunnelComponent } from './calculation-idea-funnel.component';

@NgModule({
  imports: [HomeSharedModule, RouterModule.forChild([CALCULATION_IDEA_FUNNEL_ROUTE])],
  declarations: [CalculationIdeaFunnelComponent]
})
export class CalculationIdeaFunnelModule {}
