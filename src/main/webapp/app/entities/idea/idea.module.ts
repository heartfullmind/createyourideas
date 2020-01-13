import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HomeSharedModule } from 'app/shared/shared.module';
import { IdeaComponent } from './idea.component';
import { IdeaDetailComponent } from './idea-detail.component';
import { IdeaUpdateComponent } from './idea-update.component';
import { IdeaDeleteDialogComponent } from './idea-delete-dialog.component';
import { ideaRoute } from './idea.route';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';

@NgModule({
  imports: [HomeSharedModule, RouterModule.forChild(ideaRoute), CKEditorModule],
  declarations: [IdeaComponent, IdeaDetailComponent, IdeaUpdateComponent, IdeaDeleteDialogComponent],
  entryComponents: [IdeaDeleteDialogComponent]
})
export class HomeIdeaModule {}
