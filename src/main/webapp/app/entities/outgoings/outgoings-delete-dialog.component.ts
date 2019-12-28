import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOutgoings } from 'app/shared/model/outgoings.model';
import { OutgoingsService } from './outgoings.service';

@Component({
  templateUrl: './outgoings-delete-dialog.component.html'
})
export class OutgoingsDeleteDialogComponent {
  outgoings: IOutgoings;

  constructor(protected outgoingsService: OutgoingsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.outgoingsService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'outgoingsListModification',
        content: 'Deleted an outgoings'
      });
      this.activeModal.dismiss(true);
    });
  }
}
