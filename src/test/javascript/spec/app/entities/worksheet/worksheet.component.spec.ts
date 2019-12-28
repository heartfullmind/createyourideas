import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HomeTestModule } from '../../../test.module';
import { WorksheetComponent } from 'app/entities/worksheet/worksheet.component';
import { WorksheetService } from 'app/entities/worksheet/worksheet.service';
import { Worksheet } from 'app/shared/model/worksheet.model';

describe('Component Tests', () => {
  describe('Worksheet Management Component', () => {
    let comp: WorksheetComponent;
    let fixture: ComponentFixture<WorksheetComponent>;
    let service: WorksheetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeTestModule],
        declarations: [WorksheetComponent],
        providers: []
      })
        .overrideTemplate(WorksheetComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorksheetComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorksheetService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Worksheet(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.worksheets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
