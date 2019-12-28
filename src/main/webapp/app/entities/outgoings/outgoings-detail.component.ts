import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOutgoings } from 'app/shared/model/outgoings.model';

@Component({
  selector: 'jhi-outgoings-detail',
  templateUrl: './outgoings-detail.component.html'
})
export class OutgoingsDetailComponent implements OnInit {
  outgoings: IOutgoings;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ outgoings }) => {
      this.outgoings = outgoings;
    });
  }

  previousState() {
    window.history.back();
  }
}
