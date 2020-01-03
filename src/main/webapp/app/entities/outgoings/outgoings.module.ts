import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HomeSharedModule } from 'app/shared/shared.module';
import { OutgoingsComponent } from './outgoings.component';
import { OutgoingsDetailComponent } from './outgoings-detail.component';
import { OutgoingsUpdateComponent } from './outgoings-update.component';
import { OutgoingsDeleteDialogComponent } from './outgoings-delete-dialog.component';
import { outgoingsRoute } from './outgoings.route';
export { OutgoingsComponent } from './outgoings.component';

@NgModule({
  imports: [HomeSharedModule, RouterModule.forChild(outgoingsRoute)],
  declarations: [OutgoingsComponent, OutgoingsDetailComponent, OutgoingsUpdateComponent, OutgoingsDeleteDialogComponent],
  entryComponents: [OutgoingsDeleteDialogComponent],
  exports: [OutgoingsComponent]
})
export class HomeOutgoingsModule {}
