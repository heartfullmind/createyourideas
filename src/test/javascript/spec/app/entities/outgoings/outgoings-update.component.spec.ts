import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HomeTestModule } from '../../../test.module';
import { OutgoingsUpdateComponent } from 'app/entities/outgoings/outgoings-update.component';
import { OutgoingsService } from 'app/entities/outgoings/outgoings.service';
import { Outgoings } from 'app/shared/model/outgoings.model';

describe('Component Tests', () => {
  describe('Outgoings Management Update Component', () => {
    let comp: OutgoingsUpdateComponent;
    let fixture: ComponentFixture<OutgoingsUpdateComponent>;
    let service: OutgoingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeTestModule],
        declarations: [OutgoingsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OutgoingsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OutgoingsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OutgoingsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Outgoings(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Outgoings();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
