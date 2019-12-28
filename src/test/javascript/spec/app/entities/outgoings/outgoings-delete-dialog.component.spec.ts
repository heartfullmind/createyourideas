import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HomeTestModule } from '../../../test.module';
import { OutgoingsDeleteDialogComponent } from 'app/entities/outgoings/outgoings-delete-dialog.component';
import { OutgoingsService } from 'app/entities/outgoings/outgoings.service';

describe('Component Tests', () => {
  describe('Outgoings Management Delete Component', () => {
    let comp: OutgoingsDeleteDialogComponent;
    let fixture: ComponentFixture<OutgoingsDeleteDialogComponent>;
    let service: OutgoingsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeTestModule],
        declarations: [OutgoingsDeleteDialogComponent]
      })
        .overrideTemplate(OutgoingsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OutgoingsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OutgoingsService);
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
