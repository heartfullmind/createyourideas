import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PledgeRoutingModule } from './pledge-routing.module';
import { PledgeComponent } from './pledge.component';
import { HomeSharedModule } from 'app/shared/shared.module';


@NgModule({
  declarations: [PledgeComponent],
  imports: [
    HomeSharedModule,
    CommonModule,
    PledgeRoutingModule
  ]
})
export class PledgeModule { }
