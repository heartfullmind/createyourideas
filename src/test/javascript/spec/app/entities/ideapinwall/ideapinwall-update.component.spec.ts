import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HomeTestModule } from '../../../test.module';
import { IdeapinwallUpdateComponent } from 'app/entities/ideapinwall/ideapinwall-update.component';
import { IdeapinwallService } from 'app/entities/ideapinwall/ideapinwall.service';
import { Ideapinwall } from 'app/shared/model/ideapinwall.model';

describe('Component Tests', () => {
  describe('Ideapinwall Management Update Component', () => {
    let comp: IdeapinwallUpdateComponent;
    let fixture: ComponentFixture<IdeapinwallUpdateComponent>;
    let service: IdeapinwallService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeTestModule],
        declarations: [IdeapinwallUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IdeapinwallUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IdeapinwallUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IdeapinwallService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Ideapinwall(123);
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
        const entity = new Ideapinwall();
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
