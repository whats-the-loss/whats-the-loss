import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-project-overview',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './project-overview.component.html',
  styleUrl: './project-overview.component.sass'
})
export class ProjectOverviewComponent {

  experiments: { name: string }[] = [
    {name: 'exp 1'},
    {name: 'exp 2'},
  ]
  project_name: string = 'Project 1';

}
