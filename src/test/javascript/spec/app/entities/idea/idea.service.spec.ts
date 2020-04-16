import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { IdeaService } from 'app/entities/idea/idea.service';
import { IIdea, Idea } from 'app/shared/model/idea.model';
import { Ideatype } from 'app/shared/model/enumerations/ideatype.model';

describe('Service Tests', () => {
  describe('Idea Service', () => {
    let injector: TestBed;
    let service: IdeaService;
    let httpMock: HttpTestingController;
    let elemDefault: IIdea;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(IdeaService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Idea(0, 'AAAAAAA', 'image/png', 'AAAAAAA', 'AAAAAAA', Ideatype.LEVEL1, 0, 0, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Idea', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Idea(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Idea', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            logo: 'BBBBBB',
            description: 'BBBBBB',
            ideatype: 'BBBBBB',
            interest: 1,
            distribution: 1,
            investment: 1,
            active: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Idea', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            logo: 'BBBBBB',
            description: 'BBBBBB',
            ideatype: 'BBBBBB',
            interest: 1,
            distribution: 1,
            investment: 1,
            active: true
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Idea', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
