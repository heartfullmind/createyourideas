import { Component, OnInit, OnDestroy } from '@angular/core';
import { JhiEventManager } from 'ng-jhipster';

import { IdeaService } from 'app/entities/idea/idea.service';
import { IIdea } from 'app/shared/model/idea.model';
import { HttpResponse } from '@angular/common/http';
import { CalculationIdeaFunnelService } from './calculation-idea-funnel.service';

@Component({
  selector: 'jhi-calculation-idea-funnel',
  templateUrl: './calculation-idea-funnel.component.html',
  styleUrls: ['calculation-idea-funnel.scss']
})
export class CalculationIdeaFunnelComponent implements OnInit, OnDestroy {

  ideas: IIdea[];
  ideasCalculated: any[] = [];
  ideasTotal: any[] = [];
  all: number;

  constructor(
    private ideaService: IdeaService,
    private calculationIdeaFunnelService: CalculationIdeaFunnelService
  ) {
  }

  ngOnInit() {
    this.ideaService.query().subscribe((res: HttpResponse<IIdea[]>) => {this.ideas = res.body; this.calculateAll();});
    
  } 

  ngOnDestroy() {
  } 

  calculateAll() { 
    this.ideas.forEach(idea => {
      let total = 0;
      this.calculationIdeaFunnelService.calculateProfit(idea.id).subscribe((res: HttpResponse<number>) => {total = res.body;this.ideasCalculated.push([idea.title]);this.ideasTotal.push([total]);});
      
   });
 }
}
 