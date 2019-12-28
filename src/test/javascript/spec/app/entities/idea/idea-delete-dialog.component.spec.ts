import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HomeTestModule } from '../../../test.module';
import { IdeaDeleteDialogComponent } from 'app/entities/idea/idea-delete-dialog.component';
import { IdeaService } from 'app/entities/idea/idea.service';

describe('Component Tests', () => {
  describe('Idea Management Delete Component', () => {
    let comp: IdeaDeleteDialogComponent;
    let fixture: ComponentFixture<IdeaDeleteDialogComponent>;
    let service: IdeaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeTestModule],
        declarations: [IdeaDeleteDialogComponent]
      })
        .overrideTemplate(IdeaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IdeaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IdeaService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
