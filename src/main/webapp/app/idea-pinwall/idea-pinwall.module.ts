import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HomeSharedModule } from 'app/shared/shared.module';
import { IdeaPinwallComponent } from './idea-pinwall.component';
import { IDEA_PINWALL_ROUTE } from './idea-pinwall.route';

@NgModule({
  imports: [HomeSharedModule, RouterModule.forChild([IDEA_PINWALL_ROUTE])],
  declarations: [IdeaPinwallComponent]
})
export class HomeIdeaPinwallModule {}
