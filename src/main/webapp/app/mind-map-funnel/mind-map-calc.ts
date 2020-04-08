import { IOutgoings } from './../shared/model/outgoings.model';
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
      const os: FinanceService = ServiceLocator.injector.get(FinanceService);
      resolve(os);
    });
  }

  init() {
    this.fetchFinanceService().then(value => {
      this.financeService = value;
    });
  }

  calcCollection() {
    const root = this.mindMap.mind.root;
    const rootChildren = this.getChildren(root);
    const partNode = root.profitToSpend/rootChildren.length;
    rootChildren.forEach(child => {
      child.profit += partNode;
      this.mindMap.view.updateNode(child);
    });
    root.profitToSpend = 0;
    this.mindMap.view.updateNode(root);
  }


  calculateProfit() {
    const childrenRoot = this.getChildren(this.mindMap.mind.root);
    let totalDailyBalance = 0;
    childrenRoot.forEach(child => {
      totalDailyBalance += child.getDailyBalance();
    });
    this.mindMap.mind.root.setProfit(totalDailyBalance * this.mindMap.mind.root.interest);
    this.mindMap.view.updateNode(this.mindMap.mind.root);
  }

  calculateAllLevels() {
    for (let level = 1; level < 10; level++){
      this.calculateProfitFromLevel(level);
    }
  }

  calculateProfitFromLevel(level) {
    let levelNodes = [];
    levelNodes = this.getAllChildrenWithLevel(level, this.getChildren(this.mindMap.mind.root));
    levelNodes.forEach(node => {
      const children = this.getChildren(node);
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
    const nodes = this.getChildren(this.mindMap.mind.root);
    nodes.forEach(node => {
      this.financeService.getDailyBalance(Number(node.id)).subscribe(db => {
        node.setDailyBalance(db.body);
        this.mindMap.view.updateNode(node);
      });
    });
  }

  calculateDailyBalancePerDate() {
    const date = new Date().toISOString().substring(0,10);
    this.financeService.getDailyBalancePerDate(Number(this.mindMap.mind.root.id), date).subscribe(db => {
      this.mindMap.mind.root.setDailyBalance(db.body);
      this.mindMap.view.updateNode(this.mindMap.mind.root);
    });
    const nodes = this.getChildren(this.mindMap.mind.root);
    nodes.forEach(node => {
      this.financeService.getDailyBalancePerDate(Number(node.id), date).subscribe(db => {
        node.setDailyBalance(db.body);
        this.mindMap.view.updateNode(node);
      });
    });
  }


  calculateDistribution() {
    const lastChildren = this.getLastChildren();
    lastChildren.forEach(child => {
      child.profit += this.mindMap.mind.root.profit * this.mindMap.mind.root.distribution;
      this.mindMap.view.updateNode(child);
    });
  }

  getLastChildren() {
    const root = this.mindMap.mind.root;
    const childrenFromRoot = this.getChildren(root);
    const lastChildren = [];
    childrenFromRoot.forEach(child => {
      if (child.children.length === 0) {
        lastChildren.push(child);
      }
    });
    return lastChildren;
  }

  getAllChildrenWithLevel(level, children) {
    const childrenWithLevel = [];
    children.forEach(child => {
      if(child.level === level) {
        childrenWithLevel.push(child);
      }
    });
    return childrenWithLevel;
  }

  getAllParents(node) {
    const parents = [];
    if(parent != null) {
      do {
        const parent = node.parent;
        parents.push(parent);
        node=parent;
        if(node.parent == null){
          break;
        }
      } while (node != null);
    }
    return parents;
  }

  getChildren(selectedNode) {
    const children = selectedNode.children;
    const nodes = [];
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
