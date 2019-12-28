import { Component, OnInit, OnDestroy, OnChanges, Input } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOutgoings } from 'app/shared/model/outgoings.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { OutgoingsService } from './outgoings.service';
import { OutgoingsDeleteDialogComponent } from './outgoings-delete-dialog.component';
import { IIdea } from 'app/shared/model/idea.model';

@Component({
  selector: 'jhi-outgoings',
  templateUrl: './outgoings.component.html'
})
export class OutgoingsComponent implements OnInit, OnDestroy, OnChanges {
  outgoings: IOutgoings[];
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
    protected outgoingsService: OutgoingsService,
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
    this.outgoingsService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IOutgoings[]>) => this.paginateOutgoings(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/outgoings'], {
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
      '/outgoings',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInOutgoings();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  ngOnChanges() {
    this.ngOnInit();
  }

  trackId(index: number, item: IOutgoings) {
    return item.id;
  }

  registerChangeInOutgoings() {
    this.eventSubscriber = this.eventManager.subscribe('outgoingsListModification', () => this.loadAll());
  }

  delete(outgoings: IOutgoings) {
    const modalRef = this.modalService.open(OutgoingsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.outgoings = outgoings;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateOutgoings(data: IOutgoings[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.outgoings = data;
  }
}
