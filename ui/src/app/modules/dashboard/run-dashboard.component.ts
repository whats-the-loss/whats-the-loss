import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './run-dashboard.component.html',
  styleUrl: './run-dashboard.component.scss'
})
export class RunDashboardComponent implements OnInit {

  projectName: string | null = ""
  experimentId: string | null = "";

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.projectName = this.route.snapshot.paramMap.get('project_id')
    this.experimentId = this.route.snapshot.paramMap.get('experiment_id')
  }




}
