import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IWorksheet } from 'app/shared/model/worksheet.model';

@Component({
  selector: 'jhi-worksheet-detail',
  templateUrl: './worksheet-detail.component.html'
})
export class WorksheetDetailComponent implements OnInit {
  worksheet: IWorksheet;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ worksheet }) => {
      this.worksheet = worksheet;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
