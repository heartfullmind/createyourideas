import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PledgeComponent } from './pledge.component';
import { HomeSharedModule } from 'app/shared/shared.module';
import { RouterModule } from '@angular/router';
import { PLEDGE_ROUTE } from './pledge.route';

@NgModule({
  declarations: [PledgeComponent],
  imports: [HomeSharedModule, CommonModule, RouterModule.forChild([PLEDGE_ROUTE])],
  exports: [RouterModule]
})
export class PledgeModule {}
