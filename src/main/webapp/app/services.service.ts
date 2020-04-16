import { ProfitBalanceService } from 'app/entities/profit-balance/profit-balance.service';
import { IdeaService } from 'app/entities/idea/idea.service';
import { IncomeService } from './entities/income/income.service';
import { OutgoingsService } from './entities/outgoings/outgoings.service';
import { FinanceService } from './finance.service';
import { Injectable } from '@angular/core';
import { ServiceLocator } from './locale.service';

@Injectable()
export class ServiceProvider {

  incomeService: IncomeService;
  outgoingsService: OutgoingsService;
  financeService: FinanceService;
  ideaService: IdeaService;
  profitBalanceService: ProfitBalanceService;

    constructor() {
    }

    fetchAll() {
      const promises = [];
      promises.push(this.fetchFinanceService());
      promises.push(this.fetchIdeaService());
      promises.push(this.fetchIncomeService());
      promises.push(this.fetchOutgoingsService());
      promises.push(this.fetchProfitBalanceService());

      Promise.all(promises).then(response => {
        this.financeService = response[0];
        this.ideaService = response[1];
        this.incomeService = response[2];
        this.outgoingsService = response[3];
        this.profitBalanceService = response[4];
      });
    }

    fetchFinanceService(): Promise<FinanceService> {
      return new Promise<FinanceService>(function(resolve) {
        const os: FinanceService = ServiceLocator.injector.get(FinanceService);
        resolve(os);
      });
    }

    fetchIdeaService(): Promise<IdeaService> {
      return new Promise<IdeaService>(function(resolve) {
        const os: IdeaService = ServiceLocator.injector.get(IdeaService);
        resolve(os);
      });
    }

    fetchOutgoingsService(): Promise<OutgoingsService> {
      return new Promise<OutgoingsService>(function(resolve) {
        const os: OutgoingsService = ServiceLocator.injector.get(OutgoingsService);
        resolve(os);
      });
    }

    fetchIncomeService(): Promise<IncomeService> {
      return new Promise<IncomeService>(function(resolve) {
        const os: IncomeService = ServiceLocator.injector.get(IncomeService);
        resolve(os);
      });
    }

    fetchProfitBalanceService(): Promise<ProfitBalanceService> {
      return new Promise<ProfitBalanceService>(function(resolve) {
        const os: ProfitBalanceService = ServiceLocator.injector.get(ProfitBalanceService);
        resolve(os);
      });
    }

    public getFinanceService(): FinanceService {
      return this.financeService;
    }

    public getIdeaService(): IdeaService {
      return this.ideaService;
    }

    public getIncomeService(): IncomeService {
      return this.incomeService;
    }

    public getOutgoingsService() {
      return this.outgoingsService;
    }
}
