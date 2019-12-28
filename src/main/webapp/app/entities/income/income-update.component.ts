import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IIncome, Income } from 'app/shared/model/income.model';
import { IncomeService } from './income.service';
import { IIdea } from 'app/shared/model/idea.model';
import { IdeaService } from 'app/entities/idea/idea.service';

@Component({
  selector: 'jhi-income-update',
  templateUrl: './income-update.component.html'
})
export class IncomeUpdateComponent implements OnInit {
  isSaving: boolean;

  ideas: IIdea[];
  dateDp: any;

  editForm = this.fb.group({
    description: [],
    date: [],
    value: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected incomeService: IncomeService,
    protected ideaService: IdeaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ income }) => {
      this.updateForm(income);
    });
    this.ideaService
      .query()
      .subscribe((res: HttpResponse<IIdea[]>) => (this.ideas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(income: IIncome) {
    this.editForm.patchValue({
      description: income.description,
      date: income.date,
      value: income.value,
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const income = this.createFromForm();
    if (income.id !== undefined) {
      this.subscribeToSaveResponse(this.incomeService.update(income));
    } else {
      this.subscribeToSaveResponse(this.incomeService.create(income));
    }
  }

  private createFromForm(): IIncome {
    return {
      ...new Income(),
      description: this.editForm.get(['description']).value,
      date: this.editForm.get(['date']).value,
      value: this.editForm.get(['value']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncome>>) {
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
