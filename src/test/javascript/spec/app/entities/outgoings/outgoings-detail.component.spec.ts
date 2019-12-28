import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HomeTestModule } from '../../../test.module';
import { OutgoingsDetailComponent } from 'app/entities/outgoings/outgoings-detail.component';
import { Outgoings } from 'app/shared/model/outgoings.model';

describe('Component Tests', () => {
  describe('Outgoings Management Detail Component', () => {
    let comp: OutgoingsDetailComponent;
    let fixture: ComponentFixture<OutgoingsDetailComponent>;
    const route = ({ data: of({ outgoings: new Outgoings(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeTestModule],
        declarations: [OutgoingsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OutgoingsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OutgoingsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.outgoings).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
