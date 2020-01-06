import { Component, OnInit, OnDestroy } from '@angular/core';
import { IIdea } from 'app/shared/model/idea.model';
import { IdeaService } from 'app/entities/idea/idea.service';
import { FormBuilder } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { IncomeService } from 'app/entities/income/income.service';
import { IIncome } from 'app/shared/model/income.model';
import { IOutgoings } from 'app/shared/model/outgoings.model';
import { OutgoingsService } from 'app/entities/outgoings/outgoings.service';
import { WorksheetService } from 'app/entities/worksheet/worksheet.service';
import { IWorksheet } from 'app/shared/model/worksheet.model';
import * as FusionCharts from 'fusioncharts';


@Component({
  selector: 'jhi-total',
  templateUrl: './total.component.html',
  styleUrls: ['total.scss']
})
export class TotalComponent implements OnInit, OnDestroy {

  investPerIdea: number;
  allIncomesPerIdea: number;
  allOutgoingsPerIdea: number;
  allWorksheetsPerIdea: number;
  
  ideas: IIdea[];
  selectedIdea: IIdea; 

  incomes: IIncome[];
  outgoings: IOutgoings[];
  worksheets: IWorksheet[];

  dataSource: any;
  type: string;
  width: string;
  height: string;


  selectIdeaForm = this.fb.group({
    ideaName: ['']	
  });

  constructor(
    protected worksheetService: WorksheetService,
    protected outgoingsService: OutgoingsService,
    protected incomeService: IncomeService,
    protected ideaService: IdeaService,
    public fb: FormBuilder
  ) {
    this.loadSelect();

    this.type = 'timeseries';
    this.width = '100%';
    this.height = '400';
    // This is the dataSource of the chart
    this.dataSource = {
      // Initially data is set as null
      data: null,
      chart: {
        showLegend: 0
      },
      caption: {
        text: 'Daily Visitors Count of a Website'
      },
      yAxis: [
        {
          plot: {
            value: 'Daily Visitors',
            type: 'area'
          },
          title: 'Daily Visitors (in thousand)'
        }
      ]
    };
    this.fetchData();
  }

  ngOnInit(){
  }

  ngOnDestroy(){
  }

  fetchData() {
    const jsonify = res => res.json();
    const dataFetch = fetch(
      'https://s3.eu-central-1.amazonaws.com/fusion.store/ft/data/area-chart-with-time-axis-data.json'
    ).then(jsonify);
    const schemaFetch = fetch(
      'https://s3.eu-central-1.amazonaws.com/fusion.store/ft/schema/area-chart-with-time-axis-schema.json'
    ).then(jsonify);

    Promise.all([dataFetch, schemaFetch]).then(res => {
      const data = res[0];
      const schema = res[1];
      // First we are creating a DataStore
      const fusionDataStore = new FusionCharts.DataStore();
      // After that we are creating a DataTable by passing our data and schema as arguments
      const fusionTable = fusionDataStore.createDataTable(data, schema);
      // Afet that we simply mutated our timeseries datasource by attaching the above
      // DataTable into its data property.
      this.dataSource.data = fusionTable;
    });
  }

  calculateWorksheets() {
    this.ideaService.find(parseInt(this.selectIdeaForm.get("ideaName").value, 10)).subscribe((res: HttpResponse<IIdea>) => 
    { 
      this.selectedIdea = res.body;
    });  
    let total = 0;
    for(let i = 0; i < this.worksheets.length; i++) {
      total += this.worksheets[i].costHour * this.worksheets[i].hours;
    }
    this.allWorksheetsPerIdea = total;
  }

  calculateIncomes() {
    this.incomeService 
    .queryByIdeaId(
      this.selectedIdea.id)
    .subscribe((resi: HttpResponse<IIncome[]>) => { 
        this.incomes = resi.body;
        let total = 0;
        for(let i = 0; i < this.incomes.length; i++) {
          total += this.incomes[i].value;
        }
        this.allIncomesPerIdea = total; 
    });
  }

  calculateOutgoings() {
    this.outgoingsService
    .queryByIdeaId(
    this.selectedIdea.id)
    .subscribe((reso: HttpResponse<IOutgoings[]>) => {
      this.outgoings = reso.body;
      let total = 0;
      for(let i = 0; i < this.outgoings.length; i++) {
        total += this.outgoings[i].value;
      }
      this.allOutgoingsPerIdea = total;
    } );

  }

  changeIdea() {
    this.ideaService.find(parseInt(this.selectIdeaForm.get("ideaName").value, 10)).subscribe((res: HttpResponse<IIdea>) => 
      { 
        this.selectedIdea = res.body;
        this.investPerIdea = this.selectedIdea.investment;
        this.calculateIncomes();
        this.calculateOutgoings();
        this.calculateWorksheets();
      })
  }

  loadSelect() {
	  this.ideaService.queryByUser().subscribe((res: HttpResponse<IIdea[]>) => {
          this.ideas = res.body;
    }); 
  }	

}
