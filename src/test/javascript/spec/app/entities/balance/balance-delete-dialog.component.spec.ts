import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HomeTestModule } from '../../../test.module';
import { BalanceDeleteDialogComponent } from 'app/entities/balance/balance-delete-dialog.component';
import { BalanceService } from 'app/entities/balance/balance.service';

describe('Component Tests', () => {
  describe('Balance Management Delete Component', () => {
    let comp: BalanceDeleteDialogComponent;
    let fixture: ComponentFixture<BalanceDeleteDialogComponent>;
    let service: BalanceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeTestModule],
        declarations: [BalanceDeleteDialogComponent]
      })
        .overrideTemplate(BalanceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BalanceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BalanceService);
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
