import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HomeTestModule } from '../../../test.module';
import { WorksheetDetailComponent } from 'app/entities/worksheet/worksheet-detail.component';
import { Worksheet } from 'app/shared/model/worksheet.model';

describe('Component Tests', () => {
  describe('Worksheet Management Detail Component', () => {
    let comp: WorksheetDetailComponent;
    let fixture: ComponentFixture<WorksheetDetailComponent>;
    const route = ({ data: of({ worksheet: new Worksheet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeTestModule],
        declarations: [WorksheetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(WorksheetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorksheetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.worksheet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
