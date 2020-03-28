import { IIdea } from 'app/shared/model/idea.model';
import { MindMapMind } from './mind-map-mind';
import { MindMapMain } from './mind-map-main';
import { IdeaService } from 'app/entities/idea/idea.service';
import { IncomeService } from 'app/entities/income/income.service';
import { OutgoingsService } from 'app/entities/outgoings/outgoings.service';
import { HttpResponse } from '@angular/common/http';

export class CalcProvider {
  mindmap: any;
  mindmapmind: any;
  incomeService: IncomeService;
  outgoingsService: OutgoingsService;
  ideaService: IdeaService;

  constructor(mindmap: MindMapMain, mindmapmind: MindMapMind) {
    this.mindmap = mindmap;
    this.mindmapmind = mindmapmind;
  }

  init() {}

  getNodeProfit(nodeid) {
    let profit = 0;
    let idea;
    this.ideaService.queryByNodeId(nodeid).subscribe((res: HttpResponse<IIdea>) => {
      idea = res.body;
      profit = idea;
    });
  }

  getAllIdeas(): IIdea[] {
    this.ideaService.findAll().subscribe((res: HttpResponse<IIdea[]>) => {
      return res.body;
    });
    return null;
  }

  getIdeaFromNodeId(nodeid): IIdea {
    this.ideaService.queryByNodeId(nodeid).subscribe((idea: HttpResponse<IIdea>) => {
      return idea.body;
    });
    return null;
  }

  getTotalInvestmentForRoot() {
    let children = this.getChildren(this.mindmapmind.root);
    let total_investment = 0;
    children.forEach(child => {
      total_investment += child.investment;
    });
    return total_investment;
  }

  getTotalInvestmentForSelectedNode(selected_node) {
    let children = this.getChildren(selected_node);
    let total_investment = 0;
    children.forEach(child => {
      total_investment += child.investment;
    });
    return total_investment;
  }

  getLastChildren() {
    const root = this.mindmap.getRoot();
    const childrenFromRoot = this.getChildren(root);
    let lastChildren = [];
    childrenFromRoot.forEach(child => {
      if (child.children.length == 0) {
        lastChildren.push(child);
      }
    });
    return lastChildren;
  }

  getChildren(selected_node) {
    let children = selected_node.children;
    let nodes = [];
    children.forEach(child => {
      nodes.push(child);
    });
    nodes.concat(children);
    children.forEach(child => {
      nodes.concat(this._getChildren(child.children, nodes));
    });
    return nodes;
  }

  _getChildren(children, nodes?) {
    children.forEach(child => {
      if (child.children != null) {
        this._getChildren(child.children, nodes);
        nodes.push(child);
      }
    });
  }

  hasChildren(node) {
    if (node.children != null && node.children.length >= 1) {
      return true;
    } else {
      return false;
    }
  }
}
