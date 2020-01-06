import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IWorksheet, Worksheet } from 'app/shared/model/worksheet.model';
import { WorksheetService } from './worksheet.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IIdea } from 'app/shared/model/idea.model';
import { IdeaService } from 'app/entities/idea/idea.service';

@Component({
  selector: 'jhi-worksheet-update',
  templateUrl: './worksheet-update.component.html'
})
export class WorksheetUpdateComponent implements OnInit {
  isSaving: boolean;
  ideaId: number;
  private sub: any;
  users: IUser[];

  ideas: IIdea[];
  dateDp: any;
  selectedIdea: IIdea;

  editForm = this.fb.group({
    id: [],
    jobtitle: [null, [Validators.required]],
    jobdescription: [null, [Validators.required]],
    date: [null, [Validators.required]],
    costHour: [null, [Validators.required]],
    hours: [null, [Validators.required]],
    total: [null, [Validators.required]],
    user: [],
    idea: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected worksheetService: WorksheetService,
    protected userService: UserService,
    protected ideaService: IdeaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ worksheet }) => {
      this.updateForm(worksheet);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.ideaService
      .query()
      .subscribe((res: HttpResponse<IIdea[]>) => (this.ideas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(worksheet: IWorksheet) {
    this.sub = this.activatedRoute.params.subscribe(params => {
      this.ideaId = +params['ideaId'];
      this.ideaService.find(this.ideaId).subscribe((res: HttpResponse<IIdea>) => this.selectedIdea = res.body);
   });
    this.editForm.patchValue({
      id: worksheet.id,
      jobtitle: worksheet.jobtitle,
      jobdescription: worksheet.jobdescription,
      date: worksheet.date,
      costHour: worksheet.costHour,
      hours: worksheet.hours,
      total: worksheet.total,
      user: worksheet.user,
      idea: this.selectedIdea
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
    const worksheet = this.createFromForm();
    if (worksheet.id !== undefined) {
      this.subscribeToSaveResponse(this.worksheetService.update(worksheet));
    } else {
      this.subscribeToSaveResponse(this.worksheetService.create(worksheet));
    }
  }

  private createFromForm(): IWorksheet {
    this.sub = this.activatedRoute.params.subscribe(params => {
      this.ideaId = +params['ideaId'];
      this.ideaService.find(this.ideaId).subscribe((res: HttpResponse<IIdea>) => this.selectedIdea = res.body);
   });
    return {
      ...new Worksheet(),
      id: this.editForm.get(['id']).value,
      jobtitle: this.editForm.get(['jobtitle']).value,
      jobdescription: this.editForm.get(['jobdescription']).value,
      date: this.editForm.get(['date']).value,
      costHour: this.editForm.get(['costHour']).value,
      hours: this.editForm.get(['hours']).value,
      total: this.editForm.get(['total']).value,
      user: this.editForm.get(['user']).value,
      idea: this.selectedIdea
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorksheet>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackIdeaById(index: number, item: IIdea) {
    return item.id;
  }
}
