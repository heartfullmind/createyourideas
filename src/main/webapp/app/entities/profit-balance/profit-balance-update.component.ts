import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProfitBalance, ProfitBalance } from 'app/shared/model/profit-balance.model';
import { ProfitBalanceService } from './profit-balance.service';
import { IIdea } from 'app/shared/model/idea.model';
import { IdeaService } from 'app/entities/idea/idea.service';

@Component({
  selector: 'jhi-profit-balance-update',
  templateUrl: './profit-balance-update.component.html'
})
export class ProfitBalanceUpdateComponent implements OnInit {
  isSaving: boolean;

  ideas: IIdea[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    profit: [],
    profitToSpend: [],
    netProfit: [],
    date: [],
    idea: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected profitBalanceService: ProfitBalanceService,
    protected ideaService: IdeaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ profitBalance }) => {
      this.updateForm(profitBalance);
    });
    this.ideaService
      .query()
      .subscribe((res: HttpResponse<IIdea[]>) => (this.ideas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(profitBalance: IProfitBalance) {
    this.editForm.patchValue({
      id: profitBalance.id,
      profit: profitBalance.profit,
      profitToSpend: profitBalance.profitToSpend,
      netProfit: profitBalance.netProfit,
      date: profitBalance.date,
      idea: profitBalance.idea
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const profitBalance = this.createFromForm();
    if (profitBalance.id !== undefined) {
      this.subscribeToSaveResponse(this.profitBalanceService.update(profitBalance));
    } else {
      this.subscribeToSaveResponse(this.profitBalanceService.create(profitBalance));
    }
  }

  private createFromForm(): IProfitBalance {
    return {
      ...new ProfitBalance(),
      id: this.editForm.get(['id']).value,
      profit: this.editForm.get(['profit']).value,
      profitToSpend: this.editForm.get(['profitToSpend']).value,
      netProfit: this.editForm.get(['netProfit']).value,
      date: this.editForm.get(['date']).value,
      idea: this.editForm.get(['idea']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfitBalance>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackIdeaById(index: number, item: IIdea) {
    return item.id;
  }
}
