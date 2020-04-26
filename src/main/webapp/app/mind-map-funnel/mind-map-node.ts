import { logger } from './config';
import { FinanceService } from 'app/finance.service';
import { CurrencyPipe } from '@angular/common';
import { PercentPipe } from '@angular/common';

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
  selectedType: string;
  description: string;
  active: boolean;
  logo: Blob;
  logoContentType: string;
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
  cPipe: CurrencyPipe;
  pPipe: PercentPipe;
  _this: MindMapNode;

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
    sDescription?,
    bActive?,
    logo?,
    logoContentType?
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
    this.selectedType = selectedType;
    this.description = sDescription;
    this.active = bActive;
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
    this.logo = logo;
    this.logoContentType = logoContentType;
    this._this = this;
  }

  show() {
    // this.init()
    const nodeView =
      /* '[' +
        this.selectedType +
        ']' + */
      '<table id="balanceinfo-' +
      this.id +
      '">' +
      '<tr><td><span class="title">Idea:</span></td><td>' +
      '<span class="title"><a href="/idea-pinwall;id=' +
      this.id +
      '">' +
      this.topic +
      '</a></span>' +
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
      '</table>' +
      '<table id="balanceprofit-' +
      this.id +
      '" class="balanceprofit" width="100%"></table>';
    /*
      '<h3>Logo</h3>' +
      '<div>' +
      '<img src="data:' +
      this.logoContentType +
      ';base64,' +
      this.logo +
      '" style="max-height: 200px;max-width: 550px;" alt="idea image"/>' +
      '</div>';
      */
    return nodeView;
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
  if (pnode && node) {
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
