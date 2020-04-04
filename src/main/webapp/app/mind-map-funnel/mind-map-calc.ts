import { getTestBed } from '@angular/core/testing';
import { IOutgoings } from './../shared/model/outgoings.model';
import { HttpResponse } from '@angular/common/http';
import { IIncome } from './../shared/model/income.model';
import { MindMapMain } from './mind-map-main';
import { IncomeService } from 'app/entities/income/income.service';
import { OutgoingsService } from 'app/entities/outgoings/outgoings.service';
import { ServiceLocator } from 'app/locale.service';
import { FinanceService } from 'app/finance.service';

export class CalcProvider {
  mindMap: MindMapMain;
  incomeService: IncomeService = ServiceLocator.injector.get(IncomeService);
  outgoingsService: OutgoingsService = ServiceLocator.injector.get(OutgoingsService);
  financeService: FinanceService;

  incomes: IIncome[];
  outgoings: IOutgoings[];

  constructor(mindMap: MindMapMain) {
    this.mindMap = mindMap;
  }

  fetchFinanceService(): Promise<FinanceService> {
    return new Promise<FinanceService>(function(resolve) {
      let os;
      os = ServiceLocator.injector.get(FinanceService);
      resolve(os);
    });
  }

  init() {
    this.fetchFinanceService().then(value => {
      this.financeService = value;
    });
  }

  calculateProfit() {
    let childrenRoot = this.getChildren(this.mindMap.mind.root);
    let totalDailyBalance = 0;
    childrenRoot.forEach(child => {
      totalDailyBalance += child.getDailyBalance();
    });
    this.mindMap.mind.root.setProfit(totalDailyBalance * this.mindMap.mind.root.interest);
    this.mindMap.view.updateNode(this.mindMap.mind.root);
  }

  calculateAllLevels() {
    let level = 1;
    for (let level = 1; level < 10; level++){
      this.calculateProfitFromLevel(level);
    }
  }

  calculateProfitFromLevel(level) {
    let levelNodes = [];
    levelNodes = this.getAllChildrenWithLevel(level, this.getChildren(this.mindMap.mind.root));
    levelNodes.forEach(node => {
      let children = this.getChildren(node);
      let totalProfit = 0;
      children.forEach(child => {
        totalProfit += child.getDailyBalance();
      });
      node.setProfit(node.interest * totalProfit);
      this.mindMap.view.updateNode(node);
    });
  }

  calculateDailyBalance() {
    this.financeService.getDailyBalance(Number(this.mindMap.mind.root.id)).subscribe(db => {
      this.mindMap.mind.root.setDailyBalance(db.body);
      this.mindMap.view.updateNode(this.mindMap.mind.root);
    });
    let nodes = this.getChildren(this.mindMap.mind.root);
    nodes.forEach(node => {
      this.financeService.getDailyBalance(Number(node.id)).subscribe(db => {
        node.setDailyBalance(db.body);
        this.mindMap.view.updateNode(node);
      });
    });
  }

  calculateDistribution() {
    let lastChildren = this.getLastChildren();
    lastChildren.forEach(child => {
      child.profit += this.mindMap.mind.root.profit * this.mindMap.mind.root.distribution;
      this.mindMap.view.updateNode(child);
    });
  }

  getLastChildren() {
    const root = this.mindMap.getRoot();
    const childrenFromRoot = this.getChildren(root);
    let lastChildren = [];
    childrenFromRoot.forEach(child => {
      if (child.children.length == 0) {
        lastChildren.push(child);
      }
    });
    return lastChildren;
  }

  getAllChildrenWithLevel(level, children) {
    let childrenWithLevel = [];
    children.forEach(child => {
      if(child.level === level) {
        childrenWithLevel.push(child);
      }
    });
    return childrenWithLevel;
  }

  getAllParents(node) {
    let parents = [];
    if(parent != null) {
      do {
        let parent = node.parent;
        parents.push(parent);
        node=parent;
        if(node.parent == null){
          break;
        }
      } while (node != null);
    }
    return parents;
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
