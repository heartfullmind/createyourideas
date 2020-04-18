import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBalance } from 'app/shared/model/balance.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { BalanceService } from './balance.service';
import { BalanceDeleteDialogComponent } from './balance-delete-dialog.component';

@Component({
  selector: 'jhi-balance',
  templateUrl: './balance.component.html'
})
export class BalanceComponent implements OnInit, OnDestroy {
  balances: IBalance[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected balanceService: BalanceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.balances = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.balanceService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IBalance[]>) => this.paginateBalances(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.balances = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInBalances();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBalance) {
    return item.id;
  }

  registerChangeInBalances() {
    this.eventSubscriber = this.eventManager.subscribe('balanceListModification', () => this.reset());
  }

  delete(balance: IBalance) {
    const modalRef = this.modalService.open(BalanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.balance = balance;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateBalances(data: IBalance[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.balances.push(data[i]);
    }
  }
}
