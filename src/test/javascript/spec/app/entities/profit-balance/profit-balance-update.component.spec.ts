import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HomeTestModule } from '../../../test.module';
import { ProfitBalanceUpdateComponent } from 'app/entities/profit-balance/profit-balance-update.component';
import { ProfitBalanceService } from 'app/entities/profit-balance/profit-balance.service';
import { ProfitBalance } from 'app/shared/model/profit-balance.model';

describe('Component Tests', () => {
  describe('ProfitBalance Management Update Component', () => {
    let comp: ProfitBalanceUpdateComponent;
    let fixture: ComponentFixture<ProfitBalanceUpdateComponent>;
    let service: ProfitBalanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeTestModule],
        declarations: [ProfitBalanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProfitBalanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfitBalanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfitBalanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProfitBalance(123);
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
        const entity = new ProfitBalance();
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
