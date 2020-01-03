import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HomeTestModule } from '../../../test.module';
import { IdeapinwallComponent } from 'app/entities/ideapinwall/ideapinwall.component';
import { IdeapinwallService } from 'app/entities/ideapinwall/ideapinwall.service';
import { Ideapinwall } from 'app/shared/model/ideapinwall.model';

describe('Component Tests', () => {
  describe('Ideapinwall Management Component', () => {
    let comp: IdeapinwallComponent;
    let fixture: ComponentFixture<IdeapinwallComponent>;
    let service: IdeapinwallService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HomeTestModule],
        declarations: [IdeapinwallComponent],
        providers: []
      })
        .overrideTemplate(IdeapinwallComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IdeapinwallComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IdeapinwallService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Ideapinwall(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ideapinwalls[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
