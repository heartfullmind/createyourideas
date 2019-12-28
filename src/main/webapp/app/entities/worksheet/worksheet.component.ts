import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorksheet } from 'app/shared/model/worksheet.model';
import { WorksheetService } from './worksheet.service';
import { WorksheetDeleteDialogComponent } from './worksheet-delete-dialog.component';

@Component({
  selector: 'jhi-worksheet',
  templateUrl: './worksheet.component.html'
})
export class WorksheetComponent implements OnInit, OnDestroy {
  worksheets: IWorksheet[];
  eventSubscriber: Subscription;

  constructor(
    protected worksheetService: WorksheetService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.worksheetService.query().subscribe((res: HttpResponse<IWorksheet[]>) => {
      this.worksheets = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInWorksheets();
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
}
