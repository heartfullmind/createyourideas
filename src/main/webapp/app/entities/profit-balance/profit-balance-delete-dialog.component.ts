import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfitBalance } from 'app/shared/model/profit-balance.model';
import { ProfitBalanceService } from './profit-balance.service';

@Component({
  templateUrl: './profit-balance-delete-dialog.component.html'
})
export class ProfitBalanceDeleteDialogComponent {
  profitBalance: IProfitBalance;

  constructor(
    protected profitBalanceService: ProfitBalanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.profitBalanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'profitBalanceListModification',
        content: 'Deleted an profitBalance'
      });
      this.activeModal.dismiss(true);
    });
  }
}
