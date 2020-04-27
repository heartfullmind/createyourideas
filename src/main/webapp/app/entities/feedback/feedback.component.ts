import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFeedback } from 'app/shared/model/feedback.model';
import { FeedbackService } from './feedback.service';
import { FeedbackDeleteDialogComponent } from './feedback-delete-dialog.component';

@Component({
  selector: 'jhi-feedback',
  templateUrl: './feedback.component.html'
})
export class FeedbackComponent implements OnInit, OnDestroy {
  feedbacks: IFeedback[];
  eventSubscriber: Subscription;

  constructor(
    protected feedbackService: FeedbackService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll() {
    this.feedbackService.query().subscribe((res: HttpResponse<IFeedback[]>) => {
      this.feedbacks = res.body;
    });
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInFeedbacks();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFeedback) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInFeedbacks() {
    this.eventSubscriber = this.eventManager.subscribe('feedbackListModification', () => this.loadAll());
  }

  delete(feedback: IFeedback) {
    const modalRef = this.modalService.open(FeedbackDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.feedback = feedback;
  }
}
