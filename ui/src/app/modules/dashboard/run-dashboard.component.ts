import {Component, effect, OnInit, signal} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ChartGridComponent} from "./chart-grid/chart-grid.component";
import {MatAccordion} from "@angular/material/expansion";
import {RunsSidepanelComponent} from "../runs-sidepanel/runs-sidepanel.component";
import {MatToolbar} from "@angular/material/toolbar";
import {MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {DASHBOARD_ECHARTS_GROUP_NAME, DashboardSettings} from "./run-dashboard.definitions"
import {reportUnhandledError} from "rxjs/internal/util/reportUnhandledError";
import {connect, disconnect} from "echarts";
import {MatTooltip} from "@angular/material/tooltip";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    ChartGridComponent,
    MatAccordion,
    RunsSidepanelComponent,
    MatToolbar,
    MatIconButton,
    MatIcon,
    MatTooltip
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

  settings = signal<DashboardSettings>({
    linkCharts: true,
    chartShowTooltip: true
  });

  constructor(private route: ActivatedRoute) {
    effect(() => {
      console.log(this.settings())
      if (this.settings().linkCharts) {
        connect(DASHBOARD_ECHARTS_GROUP_NAME)
      }
      else {
        disconnect(DASHBOARD_ECHARTS_GROUP_NAME)
      }
    });
  }


  ngOnInit(): void {
    this.projectName = this.route.snapshot.paramMap.get('project_id')
    this.experimentId = this.route.snapshot.paramMap.get('experiment_id')
  }


  toggleSettingsLinkCharts() {
    this.settings.update(s => ({...s, linkCharts: !s.linkCharts}))
  }

  toggleSettingsChartShowTooltip() {
    this.settings.update(s => ({...s, chartShowTooltip: !s.chartShowTooltip}))
  }

  /*
  toggleSettingsProperty(propName: string) {
    // this.settings.update(s => ({...s, linkCharts: !s.linkCharts}))
    this.settings.update(s => {s[propName] = !s[propName]; return s});
  }
  */
}
