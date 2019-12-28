import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HomeSharedModule } from 'app/shared/shared.module';
import { WorksheetComponent } from './worksheet.component';
import { WorksheetDetailComponent } from './worksheet-detail.component';
import { WorksheetUpdateComponent } from './worksheet-update.component';
import { WorksheetDeleteDialogComponent } from './worksheet-delete-dialog.component';
import { worksheetRoute } from './worksheet.route';
export { WorksheetComponent }

@NgModule({
  imports: [HomeSharedModule, RouterModule.forChild(worksheetRoute)],
  declarations: [WorksheetComponent, WorksheetDetailComponent, WorksheetUpdateComponent, WorksheetDeleteDialogComponent],
  entryComponents: [WorksheetDeleteDialogComponent],
  exports: [WorksheetComponent]
})
export class HomeWorksheetModule {} 
