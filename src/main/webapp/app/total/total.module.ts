import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HomeSharedModule } from 'app/shared/shared.module';
import { TotalComponent } from './total.component';
import { TOTAL_ROUTE } from './total.route';


@NgModule({
  imports: [HomeSharedModule, RouterModule.forChild([TOTAL_ROUTE])], 
  declarations: [TotalComponent]
})
export class HomeTotalModule {}
