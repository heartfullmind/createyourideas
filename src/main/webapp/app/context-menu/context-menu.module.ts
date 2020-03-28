import { NgModule } from '@angular/core';

import { HomeSharedModule } from 'app/shared/shared.module';
import { ContextMenuComponent } from './context-menu.component';

@NgModule({
  imports: [HomeSharedModule],
  declarations: [ContextMenuComponent]
})
export class ContextMenuModule {}
