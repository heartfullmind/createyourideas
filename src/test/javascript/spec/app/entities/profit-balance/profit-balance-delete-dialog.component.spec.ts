import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HomeTestModule } from '../../../test.module';
import { ProfitBalanceDeleteDialogComponent } from 'app/entities/profit-balance/profit-balance-delete-dialog.component';
import { ProfitBalanceService } from 'app/entities/profit-balance/profit-balance.service';

describe('Component Tests', () => {
  describe('ProfitBalance Management Delete Component', () => {
    let comp: ProfitBalanceDeleteDialogComponent;
    let fixture: ComponentFixture<ProfitBalanceDeleteDialogComponent>;
    let service: ProfitBalanceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeTestModule],
        declarations: [ProfitBalanceDeleteDialogComponent]
      })
        .overrideTemplate(ProfitBalanceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfitBalanceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfitBalanceService);
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
