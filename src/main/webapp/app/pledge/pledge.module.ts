import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PledgeRoutingModule } from './pledge-routing.module';
import { PledgeComponent } from './pledge.component';


@NgModule({
  declarations: [PledgeComponent],
  imports: [
    CommonModule,
    PledgeRoutingModule
  ]
})
export class PledgeModule { }
