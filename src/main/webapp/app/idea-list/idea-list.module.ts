import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HomeSharedModule } from 'app/shared/shared.module';
import { IDEA_LIST_ROUTE } from './idea-list.route';

@NgModule({
  imports: [HomeSharedModule, RouterModule.forChild([IDEA_LIST_ROUTE])],
  declarations: []
})
export class HomeIdeaListModule {}
