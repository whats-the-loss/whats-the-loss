import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ChartGridComponent} from "./chart-grid/chart-grid.component";
import {MatAccordion} from "@angular/material/expansion";
import {RunsSidepanelComponent} from "../runs-sidepanel/runs-sidepanel.component";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    ChartGridComponent,
    MatAccordion,
    RunsSidepanelComponent
  ],
  templateUrl: './run-dashboard.component.html',
  styleUrl: './run-dashboard.component.scss'
})
export class RunDashboardComponent implements OnInit {

  projectName: string | null = ""
  experimentId: string | null = "";

  panels = [
    {path: 'dummy_panel1'},
    {path: 'dummy_panel2'},
    {path: 'dummy_panel3'},
  ];

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.projectName = this.route.snapshot.paramMap.get('project_id')
    this.experimentId = this.route.snapshot.paramMap.get('experiment_id')
  }




}
