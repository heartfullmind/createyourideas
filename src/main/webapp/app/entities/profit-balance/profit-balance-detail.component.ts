import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProfitBalance } from 'app/shared/model/profit-balance.model';

@Component({
  selector: 'jhi-profit-balance-detail',
  templateUrl: './profit-balance-detail.component.html'
})
export class ProfitBalanceDetailComponent implements OnInit {
  profitBalance: IProfitBalance;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ profitBalance }) => {
      this.profitBalance = profitBalance;
    });
  }

  previousState() {
    window.history.back();
  }
}
