import {Component, Input, OnInit} from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {ActivatedRoute, ActivationEnd, Router, RouterLink, RouterLinkActive} from "@angular/router";
import {filter, map, Observable} from "rxjs";
import {AsyncPipe} from "@angular/common";

@Component({
  selector: 'wtl-nav-sidebar',
  standalone: true,
  imports: [
    MatIcon,
    RouterLink,
    RouterLinkActive,
    AsyncPipe
  ],
  templateUrl: './nav-sidebar.component.html',
  styleUrl: './nav-sidebar.component.scss'
})
export class NavSidebarComponent {
  project_id: string | null = null;
  experiment_id: number | null = null;


  navItems: any[] = []

  constructor(private route: ActivatedRoute, private router: Router) {
    this.updateNavItems()
    this.router.events
      .pipe(
        filter(e => (e instanceof ActivationEnd) && (Object.keys(e.snapshot.params).length > 0)),
        map(e => e instanceof ActivationEnd ? e.snapshot.params : {})
      )
      .subscribe(params => {
        console.log(params);
        this.project_id = params['project_id']
        this.experiment_id = params['experiment_id']
        this.updateNavItems()
      });
  }


  updateNavItems() {
    const expSet = this.experiment_id != null
    this.navItems = [
      {name: 'Project', icon: 'home', routerLink: ['project', this.project_id], enabled: true},
      {name: 'Run Charts', icon: 'insert_chart_outlined', routerLink: ['project', this.project_id, 'experiment', this.experiment_id, 'dashboard'], enabled: expSet},
      {name: 'Runs', icon: 'list', routerLink: ['/project', this.project_id, 'experiment',  this.experiment_id, 'runs'], enabled: expSet},
      {name: 'Settings', icon: 'settings', routerLink: '/settings', enabled: true}
    ]
  }

}
