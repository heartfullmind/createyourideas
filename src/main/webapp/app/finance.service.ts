import { OutgoingsService } from 'app/entities/outgoings/outgoings.service';
import { IncomeService } from 'app/entities/income/income.service';
import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { IIncome } from './shared/model/income.model';

@Injectable({ providedIn: 'root' })
export class FinanceService {
  incomeService: IncomeService;
  outgoingsService: OutgoingsService;

  constructor(incomeService: IncomeService, outgoingsService: OutgoingsService) {}

  calculateIncomes(ideaId) {
    return new Promise<number>(value => {
      let incomes = [];
      this.incomeService.queryByIdeaId(ideaId).subscribe((resi: HttpResponse<IIncome[]>) => {
        incomes = resi.body;
        let total = 0;
        for (let i = 0; i < incomes.length; i++) {
          total += incomes[i].value;
        }
        value(total);
      });
    });
  }
}
