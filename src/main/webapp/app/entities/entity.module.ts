import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'idea',
        loadChildren: () => import('./idea/idea.module').then(m => m.HomeIdeaModule)
      },
      {
        path: 'income',
        loadChildren: () => import('./income/income.module').then(m => m.HomeIncomeModule)
      },
      {
        path: 'outgoings',
        loadChildren: () => import('./outgoings/outgoings.module').then(m => m.HomeOutgoingsModule)
      },
      {
        path: 'worksheet',
        loadChildren: () => import('./worksheet/worksheet.module').then(m => m.HomeWorksheetModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class HomeEntityModule {}
