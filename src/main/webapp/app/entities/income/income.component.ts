import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIncome } from 'app/shared/model/income.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IncomeService } from './income.service';
import { IncomeDeleteDialogComponent } from './income-delete-dialog.component';
import { IIdea } from 'app/shared/model/idea.model';
import { IdeaService } from '../idea/idea.service';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-income',
  templateUrl: './income.component.html'
})
export class IncomeComponent implements OnInit, OnDestroy {
  incomes: IIncome[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  ideas: IIdea[];
  selectedIdea: IIdea;
  paramId: number;

  selectIdeaForm = this.fb.group({
    ideaName: ['']
  });

  constructor(
    protected incomeService: IncomeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected ideaService: IdeaService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    public fb: FormBuilder
  ) {
    this.incomes = [];

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
      this.incomeService
        .queryByIdeaId(
          this.selectedIdea.id,
        {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<IIncome[]>) => this.paginateIncomes(res.body, res.headers));
    }
  }

  reset() {
    this.page = 0;
    this.incomes = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    if(!this.activatedRoute.snapshot.params.id) {
      this.paramId = 0;
    } else {
      this.paramId = this.activatedRoute.snapshot.params.id;
    }
    this.loadAll();
    this.registerChangeInIncomes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IIncome) {
    return item.id;
  }

  registerChangeInIncomes() {
    this.eventSubscriber = this.eventManager.subscribe('incomeListModification', () => this.reset());
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

  changeIdea() {
    this.ideaService.find(parseInt(this.selectIdeaForm.get("ideaName").value, 10)).subscribe((res: HttpResponse<IIdea>) =>
      { this.selectedIdea = res.body;
        this.reset();
      })

  }

  loadSelect() {
	  this.ideaService.queryByUser().subscribe((res: HttpResponse<IIdea[]>) => {
          this.ideas = res.body;
    });
  }

  protected paginateIncomes(data: IIncome[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.incomes.push(data[i]);
    }
  }
}
