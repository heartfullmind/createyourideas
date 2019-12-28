import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorksheet } from 'app/shared/model/worksheet.model';
import { WorksheetService } from './worksheet.service';

@Component({
  templateUrl: './worksheet-delete-dialog.component.html'
})
export class WorksheetDeleteDialogComponent {
  worksheet: IWorksheet;

  constructor(protected worksheetService: WorksheetService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.worksheetService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'worksheetListModification',
        content: 'Deleted an worksheet'
      });
      this.activeModal.dismiss(true);
    });
  }
}
