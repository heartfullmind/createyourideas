import { CalcProvider } from './mind-map-calc';
import { logger } from './config';
import { customizeFormat } from './customize-format';
import { MindMapModuleOpts, MindMapMain } from './mind-map-main';
import { MIND_TYPE } from './constants';
import { MindMapMind } from './mind-map-mind';

export class MindMapDataProvider {
  jm: any;
  calc: CalcProvider;

  constructor(jm, calc: CalcProvider) {
    this.jm = jm;
    this.calc = calc;
  }

  init() {
    logger.debug('data.init');
  }

  reset() {
    logger.debug('data.reset');
  }

  load(mind_data, opts: MindMapModuleOpts, calc: CalcProvider) {
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
        mind = customizeFormat.node_array.getMind(mind_data, calc);
      } else if (df === MIND_TYPE.NODE_TREE) {
        mind = customizeFormat.nodeTree.getMind(mind_data, calc);
      } else if (df === MIND_TYPE.FREE_MIND) {
        mind = customizeFormat.freemind.getMind(mind_data, calc);
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
