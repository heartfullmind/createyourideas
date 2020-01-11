import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { HomeSharedModule } from 'app/shared/shared.module';
import { HomeCoreModule } from 'app/core/core.module';
import { HomeAppRoutingModule } from './app-routing.module';
import { HomeHomeModule } from './home/home.module';
import { HomeEntityModule } from './entities/entity.module';
import { HomeAppAboutUsModule } from './about-us/about-us.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { IdeaListComponent } from './idea-list/idea-list.component';
import { HomeIdeaListModule } from './idea-list/idea-list.module';
import { HomeAccountingModule } from './accounting/accounting.module';
import { HomeOutgoingsModule } from './entities/outgoings/outgoings.module';
import { HomeIncomeModule } from './entities/income/income.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { IdeaFunnelModule } from './idea-funnel/idea-funnel.module';
import { FooterModule } from './layouts/footer/footer.module';
import { HomeWorksheetModule } from './entities/worksheet/worksheet.module';
import { HomeIdeaPinwallModule } from './idea-pinwall/idea-pinwall.module';
import { HomeTotalModule } from './total/total.module';
// Import angular-fusioncharts
import { FusionChartsModule } from 'angular-fusioncharts';
// Import FusionCharts library and chart modules
import * as FusionCharts from 'fusioncharts';
import * as TimeSeries from 'fusioncharts/fusioncharts.timeseries'; // Import timeseries
import { CalculationIdeaFunnelModule } from './calculation-idea-funnel/calculation-idea-funnel.module';

FusionChartsModule.fcRoot(FusionCharts, TimeSeries);

@NgModule({
  imports: [
    BrowserModule,
    HomeSharedModule,
    HomeCoreModule,
    HomeHomeModule,
	  HomeIdeaListModule,
    HomeAppAboutUsModule,
	  HomeAccountingModule, 
	  HomeOutgoingsModule,
	  HomeIncomeModule,
    IdeaFunnelModule,
    HomeIdeaPinwallModule,
    HomeTotalModule,
    FusionChartsModule,
    CalculationIdeaFunnelModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    HomeEntityModule,
    HomeAppRoutingModule,
    BrowserAnimationsModule,
    FooterModule,
    HomeWorksheetModule,
    
    
  ],
  providers: [],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, IdeaListComponent],
  bootstrap: [JhiMainComponent]
})
export class HomeAppModule {}
