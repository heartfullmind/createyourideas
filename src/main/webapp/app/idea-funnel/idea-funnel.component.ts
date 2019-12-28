 
import { Component, OnInit } from '@angular/core';
import { customizeUtil, MindMapMain } from 'mind-map';
import { IdeaService } from 'app/entities/idea/idea.service';
import { HttpResponse } from '@angular/common/http';
import { IdeaFunnelService } from './idea-funnel.service';

const HIERARCHY_RULES = {
  ROOT: {
    name: 'root',
    backgroundColor: '#7EC6E1',
    getChildren: () => [
      HIERARCHY_RULES.SALES_MANAGER,
      HIERARCHY_RULES.SHOW_ROOM,
      HIERARCHY_RULES.SALES_TEAM
    ]
  },
  SALES_MANAGER: {
    name: 'sales_manager',
    color: '#fff',
    backgroundColor: '#616161',
    getChildren: () => [
      HIERARCHY_RULES.SHOW_ROOM,
      HIERARCHY_RULES.SALES_TEAM
    ]
  },
  SHOW_ROOM: {
    name: 'show_room',
    color: '#fff',
    backgroundColor: '#989898',
    getChildren: () => [
      HIERARCHY_RULES.SALES_TEAM
    ]
  },
  SALES_TEAM: {
    name: 'sales_team',
    color: '#fff',
    backgroundColor: '#C6C6C6',
    getChildren: () => []
  }
};

const option = {
  container: 'jsmind_container',
  theme: 'primary',
  editable: true,
  depth: 4,
  hierarchyRule: HIERARCHY_RULES,
  enableDraggable: false,
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

  constructor (protected ideaService: IdeaService, protected ideaFunnelService: IdeaFunnelService) {}

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

  getMindMapData() {
    const data = this.mindMap.getData().data;
    console.log('data: ', data);
  }

}
