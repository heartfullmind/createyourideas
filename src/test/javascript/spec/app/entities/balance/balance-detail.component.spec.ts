import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HomeTestModule } from '../../../test.module';
import { BalanceDetailComponent } from 'app/entities/balance/balance-detail.component';
import { Balance } from 'app/shared/model/balance.model';

describe('Component Tests', () => {
  describe('Balance Management Detail Component', () => {
    let comp: BalanceDetailComponent;
    let fixture: ComponentFixture<BalanceDetailComponent>;
    const route = ({ data: of({ balance: new Balance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeTestModule],
        declarations: [BalanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BalanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BalanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.balance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
