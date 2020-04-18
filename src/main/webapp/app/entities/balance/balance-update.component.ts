import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IBalance, Balance } from 'app/shared/model/balance.model';
import { BalanceService } from './balance.service';
import { IIdea } from 'app/shared/model/idea.model';
import { IdeaService } from 'app/entities/idea/idea.service';

@Component({
  selector: 'jhi-balance-update',
  templateUrl: './balance-update.component.html'
})
export class BalanceUpdateComponent implements OnInit {
  isSaving: boolean;

  ideas: IIdea[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    dailyBalance: [],
    profit: [],
    profitToSpend: [],
    netProfit: [],
    date: [],
    idea: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected balanceService: BalanceService,
    protected ideaService: IdeaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ balance }) => {
      this.updateForm(balance);
    });
    this.ideaService
      .query()
      .subscribe((res: HttpResponse<IIdea[]>) => (this.ideas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(balance: IBalance) {
    this.editForm.patchValue({
      id: balance.id,
      dailyBalance: balance.dailyBalance,
      profit: balance.profit,
      profitToSpend: balance.profitToSpend,
      netProfit: balance.netProfit,
      date: balance.date,
      idea: balance.idea
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const balance = this.createFromForm();
    if (balance.id !== undefined) {
      this.subscribeToSaveResponse(this.balanceService.update(balance));
    } else {
      this.subscribeToSaveResponse(this.balanceService.create(balance));
    }
  }

  private createFromForm(): IBalance {
    return {
      ...new Balance(),
      id: this.editForm.get(['id']).value,
      dailyBalance: this.editForm.get(['dailyBalance']).value,
      profit: this.editForm.get(['profit']).value,
      profitToSpend: this.editForm.get(['profitToSpend']).value,
      netProfit: this.editForm.get(['netProfit']).value,
      date: this.editForm.get(['date']).value,
      idea: this.editForm.get(['idea']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBalance>>) {
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
