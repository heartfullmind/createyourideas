import { logger } from './config';
import { customizeFormat } from './customize-format';
import { MindMapModuleOpts } from './mind-map-main';
import { MIND_TYPE } from './constants';
import { OutgoingsService } from './../entities/outgoings/outgoings.service';
import { IncomeService } from './../entities/income/income.service';
import { IdeaService } from './../entities/idea/idea.service';

export class MindMapDataProvider {
  jm: any;
  ideaService: IdeaService;
  incomeService: IncomeService;
  outgoingsService: OutgoingsService;

  constructor(jm, ideaService?: IdeaService, incomeService?: IncomeService, outgoingsService?: OutgoingsService) {
    this.jm = jm;
    this.incomeService = incomeService;
    this.outgoingsService = outgoingsService;
    this.ideaService = ideaService;
  }

  init() {
    logger.debug('data.init');
  }

  reset() {
    logger.debug('data.reset');
  }

  load(mind_data, opts: MindMapModuleOpts, ideaService: IdeaService, incomeService: IncomeService, outgoingsService: OutgoingsService) {
    let df = null;
    let mind = null;
    if (typeof mind_data === 'object') {
      if (!!mind_data.format) {
        df = mind_data.format;
      } else {
        df = MIND_TYPE.NODE_TREE;
      }
    } else {
      df = MIND_TYPE.FREE_MIND;
    }
    customizeFormat.setSelectable(opts.selectable);

    if (df === MIND_TYPE.NODE_ARRAY) {
      mind = customizeFormat.node_array.getMind(mind_data, ideaService, incomeService, outgoingsService);
    } else if (df === MIND_TYPE.NODE_TREE) {
      mind = customizeFormat.nodeTree.getMind(mind_data, ideaService, incomeService, outgoingsService);
    } else if (df === MIND_TYPE.FREE_MIND) {
      mind = customizeFormat.freemind.getMind(mind_data);
    } else {
      logger.warn('unsupported format');
    }
    return mind;
  }

  getData(data_format) {
    let data = null;
    if (data_format === MIND_TYPE.NODE_ARRAY) {
      data = customizeFormat.node_array.getData(this.jm.mind);
    } else if (data_format === MIND_TYPE.NODE_TREE) {
      data = customizeFormat.nodeTree.getData(this.jm.mind);
    } else if (data_format === MIND_TYPE.FREE_MIND) {
      data = customizeFormat.freemind.getData(this.jm.mind);
    } else {
      logger.error('unsupported ' + data_format + ' format');
    }
    return data;
  }
}
