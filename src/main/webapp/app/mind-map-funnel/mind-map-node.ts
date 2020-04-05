import { logger } from './config';
import { FinanceService } from 'app/finance.service';
import {CurrencyPipe} from '@angular/common';
import {PercentPipe} from '@angular/common';

interface NodeData {
  view?: NodeView;
}

export interface NodeView {
  absX: string;
  absY: string;
  height: string;
  width: string;
}

export class MindMapNode {
  static compare;
  static inherited;

  id: string;
  index: any;
  topic: string;
  interest: number;
  distribution: number;
  investment: number;
  dailyBalance: number;
  profit: number;
  selectedType: string;
  data: { isCreated?: boolean };
  isroot: boolean;
  level: number;
  parent: MindMapNode;
  direction;
  expanded: boolean;
  children: Array<any>;
  isCreated: boolean;
  selectable: boolean;
  private _data: NodeData;
  financeService: FinanceService;
  profitToSpend: number;
  netProfit: number;
  cPipe: CurrencyPipe;
  pPipe: PercentPipe;


  constructor(
    sId,
    iIndex,
    sTopic,
    oData,
    sRoot,
    oParent?,
    eDirection?,
    bExpanded?,
    selectedType?,
    level?,
    selectable?,
    sInterest?,
    sInvestment?,
    sDistribution?,
    sProfit?
  ) {

    if (!sId) {
      logger.error('invalid nodeid');
      return;
    }
    if (typeof iIndex !== 'number') {
      logger.error('invalid node index');
      return;
    }
    if (typeof bExpanded === 'undefined') {
      bExpanded = true;
    }
    this.id = sId;
    this.index = iIndex;
    this.topic = sTopic;
    this.interest = sInterest;
    this.distribution = sDistribution;
    this.investment = sInvestment;
    this.profit = sProfit;
    this.selectedType = selectedType;
    this.selectable = selectable;
    this.data = oData || {};
    this.isroot = sRoot;
    this.parent = oParent;
    this.direction = eDirection;
    this.expanded = !!bExpanded;
    this.level = level;
    this.children = [];
    this._data = {};
    this.isCreated = this.data.isCreated;
    if (this.isroot) {
      this.level = 1;
    }
    this.cPipe = new CurrencyPipe('en-US');
    this.pPipe = new PercentPipe('en-US');
  }

  show() {
    if (this.selectedType && this.selectable !== false) {
      return (
        '[' +
        this.selectedType +
        ']' +
        "<table id='calcinfo'>" +
        '<tr><td>Idea:</td><td>' +
        this.topic +
        '</td></tr>' +
        '<tr><td>Interest:</td><td>' +
        this.pPipe.transform(this.interest) +
        '</td></tr>' +
        '<tr><td>Distribution:</td><td>' +
        this.pPipe.transform(this.distribution) +
        '</td></tr>' +
        '<tr><td>Investment:</td><td>' +
        this.cPipe.transform(this.investment) +
        '</td></tr>' +
        '<tr><td>Daily Balance:</td><td>' +
        this.cPipe.transform(this.dailyBalance) +
        '</td></tr>' +
        '<tr><td>Profit:</td><td>' +
        this.cPipe.transform(this.profit) +
        '</td></tr>' +
        '<tr><td>3/4 of the profit:</td><td>' +
        this.cPipe.transform(this.profitToSpend) +
        '</td></tr>' +
        '<tr><td>Net-Profit:</td><td>' +
        this.cPipe.transform(this.netProfit) +
        '</td></tr>' +
        '</table>'
      );
    }
    if (this.isroot) {
      return (
        "<table id='calcinfo'>" +
        '<tr><td>Idea:</td><td>' +
        this.topic +
        '</td></tr>' +
        '<tr><td>Interest:</td><td>' +
        this.pPipe.transform(this.interest) +
        '</td></tr>' +
        '<tr><td>Distribution:</td><td>' +
        this.pPipe.transform(this.distribution) +
        '</td></tr>' +
        '<tr><td>Daily Balance:</td><td>' +
        this.cPipe.transform(this.dailyBalance) +
        '</td></tr>' +
        '<tr><td>Profit:</td><td>' +
        this.cPipe.transform(this.profit) +
        '</td></tr>' +
        '<tr><td>3/4 of the profit:</td><td>' +
        this.cPipe.transform(this.profitToSpend) +
        '</td></tr>' +
        '<tr><td>Net-Profit:</td><td>' +
        this.cPipe.transform(this.netProfit) +
        '</td></tr>' +
        '</table>'
      );
    } else {
      return (
        "<table id='calcinfo'>" +
        '<tr><td>Idea:</td><td>' +
        this.topic +
        '</td></tr>' +
        '<tr><td>Interest:</td><td>' +
        this.pPipe.transform(this.interest) +
        '</td></tr>' +
        '<tr><td>Distribution:</td><td>' +
        this.pPipe.transform(this.distribution) +
        '</td></tr>' +
        '<tr><td>Investment:</td><td>' +
        this.cPipe.transform(this.investment) +
        '</td></tr>' +
        '<tr><td>Daily Balance:</td><td>' +
        this.cPipe.transform(this.dailyBalance) +
        '</td></tr>' +
        '<tr><td>Profit:</td><td>' +
        this.cPipe.transform(this.profit) +
        '</td></tr>' +
        '<tr><td>3/4 of the profit:</td><td>' +
        this.cPipe.transform(this.profitToSpend) +
        '</td></tr>' +
        '<tr><td>Net-Profit:</td><td>' +
        this.cPipe.transform(this.netProfit) +
        '</td></tr>' +
        '</table>'
      );
    }
  }

  getThreeOfFour(value) {
    return 0.75*value;
  }

  getLocation() {
    const vd = this._data.view;
    return {
      x: vd.absX,
      y: vd.absY
    };
  }

  getSize() {
    const vd = this._data.view;
    return {
      w: vd.width,
      h: vd.height
    };
  }

  setProfit(profit: number) {
    this.profitToSpend = 0.75*profit;
    this.netProfit = profit - this.profitToSpend;
    this.profit = profit;
  }

  getProfit() {
    return this.profit;
  }

  setDailyBalance(dailyBalance: number) {
    this.dailyBalance = dailyBalance;
  }

  getDailyBalance() {
    return this.dailyBalance;
  }


}

MindMapNode.compare = (node1, node2) => {
  let r;
  const i1 = node1.index;
  const i2 = node2.index;
  if (i1 >= 0 && i2 >= 0) {
    r = i1 - i2;
  } else if (i1 === -1 && i2 === -1) {
    r = 0;
  } else if (i1 === -1) {
    r = 1;
  } else if (i2 === -1) {
    r = -1;
  } else {
    r = 0;
  }
  return r;
};

MindMapNode.inherited = (pnode, node) => {
  if (!!pnode && !!node) {
    if (pnode.id === node.id) {
      return true;
    }
    if (pnode.isroot) {
      return true;
    }
    const pid = pnode.id;
    let p = node;
    while (!p.isroot) {
      p = p.parent;
      if (p.id === pid) {
        return true;
      }
    }
  }
  return false;
};
