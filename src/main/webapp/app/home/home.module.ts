import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HomeSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [HomeSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class HomeHomeModule {}
