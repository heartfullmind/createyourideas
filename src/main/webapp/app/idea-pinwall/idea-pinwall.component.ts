import { Component, OnInit } from '@angular/core';
import { IIdea } from 'app/shared/model/idea.model';
import { IdeaService } from 'app/entities/idea/idea.service';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { JhiDataUtils } from 'ng-jhipster';

@Component({
  selector: 'jhi-idea-pinwall',
  templateUrl: './idea-pinwall.component.html',
  styleUrls: ['idea-pinwall.scss']
})
export class IdeaPinwallComponent implements OnInit {

  ideas: IIdea[];
  selectedIdea: IIdea;

  selectIdeaForm = this.fb.group({
    ideaName: ['']
  });

  constructor(
    protected ideaService: IdeaService,
    protected dataUtils: JhiDataUtils,
    public fb: FormBuilder) {}

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    this.ideaService
      .query()
      .subscribe((res: HttpResponse<IIdea[]>) => { this.ideas = res.body; });
  }

  changeIdea() {
    this.ideaService.find(parseInt(this.selectIdeaForm.get("ideaName").value, 10)).subscribe((res: HttpResponse<IIdea>) =>
      { this.selectedIdea = res.body; })
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

}
