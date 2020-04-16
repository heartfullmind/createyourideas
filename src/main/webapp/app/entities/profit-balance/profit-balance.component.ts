import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProfitBalance } from 'app/shared/model/profit-balance.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ProfitBalanceService } from './profit-balance.service';
import { ProfitBalanceDeleteDialogComponent } from './profit-balance-delete-dialog.component';

@Component({
  selector: 'jhi-profit-balance',
  templateUrl: './profit-balance.component.html'
})
export class ProfitBalanceComponent implements OnInit, OnDestroy {
  profitBalances: IProfitBalance[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected profitBalanceService: ProfitBalanceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.profitBalances = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.profitBalanceService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IProfitBalance[]>) => this.paginateProfitBalances(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.profitBalances = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProfitBalances();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProfitBalance) {
    return item.id;
  }

  registerChangeInProfitBalances() {
    this.eventSubscriber = this.eventManager.subscribe('profitBalanceListModification', () => this.reset());
  }

  delete(profitBalance: IProfitBalance) {
    const modalRef = this.modalService.open(ProfitBalanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.profitBalance = profitBalance;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateProfitBalances(data: IProfitBalance[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.profitBalances.push(data[i]);
    }
  }
}
