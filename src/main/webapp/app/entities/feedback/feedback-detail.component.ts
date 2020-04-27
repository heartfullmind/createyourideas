import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFeedback } from 'app/shared/model/feedback.model';

@Component({
  selector: 'jhi-feedback-detail',
  templateUrl: './feedback-detail.component.html'
})
export class FeedbackDetailComponent implements OnInit {
  feedback: IFeedback;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ feedback }) => {
      this.feedback = feedback;
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
