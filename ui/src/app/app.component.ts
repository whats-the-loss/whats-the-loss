import { Component } from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router, RouterOutlet} from '@angular/router';
import {MatDrawer, MatDrawerContainer, MatDrawerContent} from "@angular/material/sidenav";
import {NavSidebarComponent} from "./modules/nav-sidebar/nav-sidebar.component";
import {BreadcrumbsComponent} from "./modules/util/breadcrumbs/breadcrumbs.component";
import {filter} from "rxjs";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    MatDrawer,
    MatDrawerContainer,
    MatDrawerContent,
    NavSidebarComponent,
    BreadcrumbsComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'ui';

  breadcrumbsPath: string[] = []

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.router.events.subscribe(ev => {
      if (ev instanceof NavigationEnd) {
        this.setBreadcrumbsPath(this.activatedRoute);
      }
    });
  }

  private setBreadcrumbsPath(activatedRoute: ActivatedRoute) {
    this.breadcrumbsPath = [];
    var routeBreadcrumbPath: string[] = activatedRoute.snapshot.data['breadcrumbPath']
    if (routeBreadcrumbPath) {
      console.log(activatedRoute.snapshot.paramMap.get('project_id'))
      routeBreadcrumbPath = routeBreadcrumbPath.map((el: string) => {
        if (el.startsWith(':')) {
          return activatedRoute.snapshot.paramMap.get(el.substring(1))!
        }
        else {
          return el
        }
      })
      this.breadcrumbsPath.push(...routeBreadcrumbPath)
      console.log(this.breadcrumbsPath)
    }
    if (activatedRoute.firstChild !== null) {
      this.setBreadcrumbsPath(activatedRoute.firstChild)
    }
  }
}
