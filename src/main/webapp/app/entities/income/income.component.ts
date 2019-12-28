import { Component, OnInit, OnDestroy, Input, OnChanges } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IIncome } from 'app/shared/model/income.model';
import { IncomeService } from './income.service';
import { IncomeDeleteDialogComponent } from './income-delete-dialog.component';
import { IIdea } from 'app/shared/model/idea.model';

@Component({
  selector: 'jhi-income',
  templateUrl: './income.component.html',
})
export class IncomeComponent implements OnInit, OnDestroy, OnChanges {
  incomes: IIncome[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  @Input() selectedIdea: IIdea;

  constructor(
    protected incomeService: IncomeService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    if(this.selectedIdea) {
    this.incomeService
      .queryByIdea(
      this.selectedIdea.id,  
      {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IIncome[]>) => this.paginateIncome(res.body, res.headers));
    }
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/income'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/income',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInIncome();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  ngOnChanges() {
    this.ngOnInit();
  }

  trackId(index: number, item: IIncome) {
    return item.id;
  } 

  registerChangeInIncome() {
    this.eventSubscriber = this.eventManager.subscribe('incomeListModification', () => this.loadAll());
  }

  delete(income: IIncome) {
    const modalRef = this.modalService.open(IncomeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.income = income;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateIncome(data: IIncome[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.incomes = data;
  }
}
