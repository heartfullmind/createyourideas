import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorksheet } from 'app/shared/model/worksheet.model';
import { WorksheetService } from './worksheet.service';
import { WorksheetDeleteDialogComponent } from './worksheet-delete-dialog.component';
import { IIdea } from 'app/shared/model/idea.model';
import { IdeaService } from '../idea/idea.service';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'jhi-worksheet',
  templateUrl: './worksheet.component.html'
})
export class WorksheetComponent implements OnInit, OnDestroy {
  worksheets: IWorksheet[];
  eventSubscriber: Subscription;
  ideas: IIdea[];
  selectedIdea: IIdea;

  selectIdeaForm = this.fb.group({
    ideaName: ['']	
  });

  constructor(
    protected worksheetService: WorksheetService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected ideaService: IdeaService,
    public fb: FormBuilder
  ) {}

  loadAll() {
    this.worksheetService.query().subscribe((res: HttpResponse<IWorksheet[]>) => {
      this.worksheets = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInWorksheets();
    this.loadSelect();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IWorksheet) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInWorksheets() {
    this.eventSubscriber = this.eventManager.subscribe('worksheetListModification', () => this.loadAll());
  }

  delete(worksheet: IWorksheet) {
    const modalRef = this.modalService.open(WorksheetDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.worksheet = worksheet;
  }

  changeIdea() {
    this.ideaService.find(parseInt(this.selectIdeaForm.get("ideaName").value, 10)).subscribe((res: HttpResponse<IIdea>) => 
      { this.selectedIdea = res.body;
      })
  }

  loadSelect() {
	  this.ideaService.queryByUser().subscribe((res: HttpResponse<IIdea[]>) => {
          this.ideas = res.body
    
    }); 
  }	
}
