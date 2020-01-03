import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HomeTestModule } from '../../../test.module';
import { IdeapinwallDeleteDialogComponent } from 'app/entities/ideapinwall/ideapinwall-delete-dialog.component';
import { IdeapinwallService } from 'app/entities/ideapinwall/ideapinwall.service';

describe('Component Tests', () => {
  describe('Ideapinwall Management Delete Component', () => {
    let comp: IdeapinwallDeleteDialogComponent;
    let fixture: ComponentFixture<IdeapinwallDeleteDialogComponent>;
    let service: IdeapinwallService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeTestModule],
        declarations: [IdeapinwallDeleteDialogComponent]
      })
        .overrideTemplate(IdeapinwallDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IdeapinwallDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IdeapinwallService);
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
