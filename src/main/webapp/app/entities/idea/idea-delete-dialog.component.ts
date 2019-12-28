import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIdea } from 'app/shared/model/idea.model';
import { IdeaService } from './idea.service';

@Component({
  templateUrl: './idea-delete-dialog.component.html'
})
export class IdeaDeleteDialogComponent {
  idea: IIdea;

  constructor(protected ideaService: IdeaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.ideaService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'ideaListModification',
        content: 'Deleted an idea'
      });
      this.activeModal.dismiss(true);
    });
  }
}
