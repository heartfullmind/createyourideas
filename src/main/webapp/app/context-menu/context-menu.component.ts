import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { DynamicScriptLoaderService } from 'app/dynamic-scriptoader-service.service';

@Component({
  selector: 'jhi-home',
  templateUrl: './context-menu.component.html',
  styleUrls: ['context-menu.scss']
})
export class ContextMenuComponent implements OnInit, OnDestroy {
  account: Account;
  authSubscription: Subscription;
  modalRef: NgbModalRef;

  constructor(
    private accountService: AccountService,
    private eventManager: JhiEventManager,
    private dynamicScriptLoader: DynamicScriptLoaderService
  ) {}

  ngOnInit() {
    this.loadScripts();
  }

  ngOnDestroy() {}

  private loadScripts() {
    // You can load multiple scripts by just providing the key as argument into load method of the service
    this.dynamicScriptLoader
      .load('contextmenu', 'jquery')
      .then(data => {
        // Script Loaded Successfully
      })
      .catch(error => console.log(error));
  }
}
