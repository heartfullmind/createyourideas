import { OutgoingsService } from 'app/entities/outgoings/outgoings.service';
import { IncomeService } from './../entities/income/income.service';

import { Component, OnInit } from '@angular/core';
import { customizeUtil, MindMapMain } from '../mind-map-funnel';
import { IdeaService } from 'app/entities/idea/idea.service';
import { HttpResponse } from '@angular/common/http';
import { IdeaFunnelService } from './idea-funnel.service';

const HIERARCHY_RULES = {
  ROOT: {
    name: 'root',
    backgroundColor: '#7EC6E1',
    getChildren: () => [HIERARCHY_RULES.LEVEL1]
  },
  LEVEL1: {
    name: 'Level 1',
    color: '#fff',
    backgroundColor: '#616161',
    getChildren: () => [HIERARCHY_RULES.LEVEL2]
  },
  LEVEL2: {
    name: 'Level 2',
    color: '#fff',
    backgroundColor: '#989898',
    getChildren: () => [HIERARCHY_RULES.LEVEL3]
  },
  LEVEL3: {
    name: 'Level 3',
    color: '#fff',
    backgroundColor: '#C6C6C6',
    getChildren: () => [HIERARCHY_RULES.LEVEL4]
  },
  LEVEL4: {
    name: 'Level 4',
    color: '#fff',
    backgroundColor: '#C6C6C6',
    getChildren: () => [HIERARCHY_RULES.LEVEL5]
  },
  LEVEL5: {
    name: 'Level 5',
    color: '#fff',
    backgroundColor: '#C6C6C6',
    getChildren: () => [HIERARCHY_RULES.LEVEL6]
  },
  LEVEL6: {
    name: 'Level 6',
    color: '#fff',
    backgroundColor: '#C6C6C6',
    getChildren: () => [HIERARCHY_RULES.LEVEL7]
  },
  LEVEL7: {
    name: 'Level 3',
    color: '#fff',
    backgroundColor: '#C6C6C6',
    getChildren: () => [HIERARCHY_RULES.LEVEL8]
  },
  LEVEL8: {
    name: 'Level 3',
    color: '#fff',
    backgroundColor: '#C6C6C6',
    getChildren: () => [HIERARCHY_RULES.LEVEL9]
  },
  LEVEL9: {
    name: 'Level 3',
    color: '#fff',
    backgroundColor: '#C6C6C6',
    getChildren: () => []
  }
};

const option = {
  container: 'jsmind_container',
  theme: 'primary',
  editable: true,
  depth: 10,
  hierarchyRule: HIERARCHY_RULES,
  enableDraggable: true
};

@Component({
  selector: 'jhi-idea-funnel',
  templateUrl: './idea-funnel.component.html',
  styleUrls: ['./idea-funnel.component.scss']
})
export class IdeaFunnelComponent implements OnInit {
  title = 'Idea Funnel';
  mindMap;
  mind2: string;

  constructor(
    protected ideaService: IdeaService,
    protected ideaFunnelService: IdeaFunnelService,
    protected incomeService: IncomeService,
    protected outgoingsService: OutgoingsService
  ) {}

  ngOnInit() {
    this.loadIdeaFunnel();
  }

  loadIdeaFunnel() {
    this.ideaFunnelService.getIdeaFunnel().subscribe((res: HttpResponse<any>) => {
      this.mind2 = res.body;
      this.mindMap = MindMapMain.show(option, this.mind2);
    });
  }

  removeNode() {
    const selectedNode = this.mindMap.getSelectedNode();
    const selectedId = selectedNode && selectedNode.id;

    if (!selectedId) {
      return;
    }
    this.mindMap.removeNode(selectedId);
  }

  addNode() {
    const selectedNode = this.mindMap.getSelectedNode();
    if (!selectedNode) {
      return;
    }

    const nodeId = customizeUtil.uuid.newid();
    this.mindMap.addNode(selectedNode, nodeId);
  }

  getParent() {
    const selectedNode = this.mindMap.getSelectedNode();
    if (!selectedNode) {
      return;
    }

    this.mindMap.getParent(selectedNode);
  }

  calculateProfit() {
    this.mindMap.calculateProfit();
  }

  calculateDailyBalance() {
    this.mindMap.calculateDailyBalance();
  }

  calculateAllLevels() {
    this.mindMap.calculateAllLevels();
  }

  calculateDistribution() {
    this.mindMap.calculateDistribution();
  }

  getMindMapData() {
    const data = this.mindMap.getData().data;
    console.log('data: ', data);
  }
}
