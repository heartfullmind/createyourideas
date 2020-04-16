import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HomeTestModule } from '../../../test.module';
import { ProfitBalanceDetailComponent } from 'app/entities/profit-balance/profit-balance-detail.component';
import { ProfitBalance } from 'app/shared/model/profit-balance.model';

describe('Component Tests', () => {
  describe('ProfitBalance Management Detail Component', () => {
    let comp: ProfitBalanceDetailComponent;
    let fixture: ComponentFixture<ProfitBalanceDetailComponent>;
    const route = ({ data: of({ profitBalance: new ProfitBalance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeTestModule],
        declarations: [ProfitBalanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProfitBalanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfitBalanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.profitBalance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
