import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIncome } from 'app/shared/model/income.model';
import { IncomeService } from './income.service';

@Component({
  templateUrl: './income-delete-dialog.component.html'
})
export class IncomeDeleteDialogComponent {
  income: IIncome;

  constructor(protected incomeService: IncomeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.incomeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'incomeListModification',
        content: 'Deleted an income'
      });
      this.activeModal.dismiss(true);
    });
  }
}
