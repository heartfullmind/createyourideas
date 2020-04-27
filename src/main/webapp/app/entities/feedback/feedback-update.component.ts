import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IFeedback, Feedback } from 'app/shared/model/feedback.model';
import { FeedbackService } from './feedback.service';

@Component({
  selector: 'jhi-feedback-update',
  templateUrl: './feedback-update.component.html'
})
export class FeedbackUpdateComponent implements OnInit {
  isSaving: boolean;
  creationDateDp: any;

  editForm = this.fb.group({
    id: [],
    creationDate: [null, [Validators.required]],
    name: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
    email: [null, [Validators.required]],
    feedback: [null, [Validators.required]]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected feedbackService: FeedbackService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ feedback }) => {
      this.updateForm(feedback);
    });
  }

  updateForm(feedback: IFeedback) {
    feedback.creationDate = moment();
    this.editForm.patchValue({
      id: feedback.id,
      creationDate: feedback.creationDate,
      name: feedback.name,
      email: feedback.email,
      feedback: feedback.feedback
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const feedback = this.createFromForm();
    if (feedback.id !== undefined) {
      this.subscribeToSaveResponse(this.feedbackService.update(feedback));
    } else {
      this.subscribeToSaveResponse(this.feedbackService.create(feedback));
    }
  }

  private createFromForm(): IFeedback {
    return {
      ...new Feedback(),
      id: this.editForm.get(['id']).value,
      creationDate: this.editForm.get(['creationDate']).value,
      name: this.editForm.get(['name']).value,
      email: this.editForm.get(['email']).value,
      feedback: this.editForm.get(['feedback']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeedback>>) {
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
}
