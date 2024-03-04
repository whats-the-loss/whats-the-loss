import { Component } from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {RouterLink, RouterLinkActive} from "@angular/router";

@Component({
  selector: 'wtl-nav-sidebar',
  standalone: true,
  imports: [
    MatIcon,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './nav-sidebar.component.html',
  styleUrl: './nav-sidebar.component.scss'
})
export class NavSidebarComponent {
  navItems = [
    {name: 'Project', icon: 'home', routerLink: '/project/my-test-project'},
    {name: 'Run Charts', icon: 'insert_chart_outlined', routerLink: '/project/x/experiment/x/dashboard'},
    {name: 'Runs', icon: 'list', routerLink: ['/project', 'x', 'experiment', 'x', 'runs']},
    {name: 'Settings', icon: 'settings', routerLink: '/settings'}
  ]

}
