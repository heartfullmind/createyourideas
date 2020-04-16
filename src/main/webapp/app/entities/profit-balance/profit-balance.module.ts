import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HomeSharedModule } from 'app/shared/shared.module';
import { ProfitBalanceComponent } from './profit-balance.component';
import { ProfitBalanceDetailComponent } from './profit-balance-detail.component';
import { ProfitBalanceUpdateComponent } from './profit-balance-update.component';
import { ProfitBalanceDeleteDialogComponent } from './profit-balance-delete-dialog.component';
import { profitBalanceRoute } from './profit-balance.route';

@NgModule({
  imports: [HomeSharedModule, RouterModule.forChild(profitBalanceRoute)],
  declarations: [ProfitBalanceComponent, ProfitBalanceDetailComponent, ProfitBalanceUpdateComponent, ProfitBalanceDeleteDialogComponent],
  entryComponents: [ProfitBalanceDeleteDialogComponent]
})
export class HomeProfitBalanceModule {}
