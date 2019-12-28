import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HomeTestModule } from '../../../test.module';
import { WorksheetUpdateComponent } from 'app/entities/worksheet/worksheet-update.component';
import { WorksheetService } from 'app/entities/worksheet/worksheet.service';
import { Worksheet } from 'app/shared/model/worksheet.model';

describe('Component Tests', () => {
  describe('Worksheet Management Update Component', () => {
    let comp: WorksheetUpdateComponent;
    let fixture: ComponentFixture<WorksheetUpdateComponent>;
    let service: WorksheetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeTestModule],
        declarations: [WorksheetUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(WorksheetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorksheetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorksheetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Worksheet(123);
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
        const entity = new Worksheet();
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
