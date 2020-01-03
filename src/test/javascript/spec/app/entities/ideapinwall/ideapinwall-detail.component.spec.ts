import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HomeTestModule } from '../../../test.module';
import { IdeapinwallDetailComponent } from 'app/entities/ideapinwall/ideapinwall-detail.component';
import { Ideapinwall } from 'app/shared/model/ideapinwall.model';

describe('Component Tests', () => {
  describe('Ideapinwall Management Detail Component', () => {
    let comp: IdeapinwallDetailComponent;
    let fixture: ComponentFixture<IdeapinwallDetailComponent>;
    const route = ({ data: of({ ideapinwall: new Ideapinwall(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeTestModule],
        declarations: [IdeapinwallDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IdeapinwallDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IdeapinwallDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ideapinwall).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
