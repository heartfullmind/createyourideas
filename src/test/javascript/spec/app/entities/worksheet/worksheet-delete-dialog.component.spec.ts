import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HomeTestModule } from '../../../test.module';
import { WorksheetDeleteDialogComponent } from 'app/entities/worksheet/worksheet-delete-dialog.component';
import { WorksheetService } from 'app/entities/worksheet/worksheet.service';

describe('Component Tests', () => {
  describe('Worksheet Management Delete Component', () => {
    let comp: WorksheetDeleteDialogComponent;
    let fixture: ComponentFixture<WorksheetDeleteDialogComponent>;
    let service: WorksheetService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeTestModule],
        declarations: [WorksheetDeleteDialogComponent]
      })
        .overrideTemplate(WorksheetDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorksheetDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorksheetService);
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
