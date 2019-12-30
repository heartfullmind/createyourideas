import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IOutgoings, Outgoings } from 'app/shared/model/outgoings.model';
import { OutgoingsService } from './outgoings.service';
import { IIdea } from 'app/shared/model/idea.model';
import { IdeaService } from 'app/entities/idea/idea.service';

@Component({
  selector: 'jhi-outgoings-update',
  templateUrl: './outgoings-update.component.html'
})
export class OutgoingsUpdateComponent implements OnInit {
  isSaving: boolean;

  ideas: IIdea[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    date: [null, [Validators.required]],
    value: [null, [Validators.required]],
    idea: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected outgoingsService: OutgoingsService,
    protected ideaService: IdeaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ outgoings }) => {
      this.updateForm(outgoings);
    });
    this.ideaService
      .query()
      .subscribe((res: HttpResponse<IIdea[]>) => (this.ideas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(outgoings: IOutgoings) {
    this.editForm.patchValue({
      id: outgoings.id,
      description: outgoings.description,
      date: outgoings.date,
      value: outgoings.value,
      idea: outgoings.idea
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const outgoings = this.createFromForm();
    if (outgoings.id !== undefined) {
      this.subscribeToSaveResponse(this.outgoingsService.update(outgoings));
    } else {
      this.subscribeToSaveResponse(this.outgoingsService.create(outgoings));
    }
  }

  private createFromForm(): IOutgoings {
    return {
      ...new Outgoings(),
      id: this.editForm.get(['id']).value,
      description: this.editForm.get(['description']).value,
      date: this.editForm.get(['date']).value,
      value: this.editForm.get(['value']).value,
      idea: this.editForm.get(['idea']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOutgoings>>) {
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
