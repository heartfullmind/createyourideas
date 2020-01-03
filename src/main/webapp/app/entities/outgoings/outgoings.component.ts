import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOutgoings } from 'app/shared/model/outgoings.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { OutgoingsService } from './outgoings.service';
import { OutgoingsDeleteDialogComponent } from './outgoings-delete-dialog.component';
import { FormBuilder } from '@angular/forms';
import { IdeaService } from '../idea/idea.service';
import { IIdea } from 'app/shared/model/idea.model';

@Component({
  selector: 'jhi-outgoings',
  templateUrl: './outgoings.component.html'
})
export class OutgoingsComponent implements OnInit, OnDestroy {
  outgoings: IOutgoings[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  ideas: IIdea[];
  selectedIdea: IIdea;

  selectIdeaForm = this.fb.group({
    ideaName: ['']	
  });

  constructor(
    protected outgoingsService: OutgoingsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected ideaService: IdeaService,
    public fb: FormBuilder
  ) {
    this.outgoings = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
    this.loadSelect();
  }

  loadAll() {
    if(this.selectedIdea) {
    this.outgoingsService
      .queryByIdeaId(
      this.selectedIdea.id, 
      {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IOutgoings[]>) => this.paginateOutgoings(res.body, res.headers));
    }
  }

  reset() {
    this.page = 0;
    this.outgoings = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInOutgoings();
    this.loadSelect();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOutgoings) {
    return item.id;
  }

  registerChangeInOutgoings() {
    this.eventSubscriber = this.eventManager.subscribe('outgoingsListModification', () => this.reset());
  }

  delete(outgoings: IOutgoings) {
    const modalRef = this.modalService.open(OutgoingsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.outgoings = outgoings;
  }

  changeIdea() {
    this.ideaService.find(parseInt(this.selectIdeaForm.get("ideaName").value, 10)).subscribe((res: HttpResponse<IIdea>) => 
      { 
        this.selectedIdea = res.body;
        this.ngOnInit();
      })
  }

  loadSelect() {
	  this.ideaService.queryByUser().subscribe((res: HttpResponse<IIdea[]>) => {
          this.ideas = res.body
    
    }); 
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
    for (let i = 0; i < data.length; i++) {
      this.outgoings.push(data[i]);
    }
  }
}
