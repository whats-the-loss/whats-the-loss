import { Component } from '@angular/core';
import {
  MatAccordion,
  MatExpansionPanel,
  MatExpansionPanelDescription,
  MatExpansionPanelHeader
} from "@angular/material/expansion";
import {MatIcon} from "@angular/material/icon";
import {ChartGridElementComponent} from "./chart-grid-element/chart-grid-element.component";

@Component({
  selector: 'wtl-chart-grid',
  standalone: true,
  imports: [
    MatAccordion,
    MatExpansionPanel,
    MatExpansionPanelDescription,
    MatExpansionPanelHeader,
    MatIcon,
    ChartGridElementComponent
  ],
  templateUrl: './chart-grid.component.html',
  styleUrl: './chart-grid.component.scss'
})
export class ChartGridComponent {
  chartsElements = [
    {id: 0, name: 'Test'},
    {id: 1, name: 'Test'},
    {id: 3, name: 'Test'},
    {id: 3, name: 'Test'},
    {id: 3, name: 'Test'},
    {id: 3, name: 'Test'},
  ];

  chartGridNumRows = 10
  chartGridNumColumns = 20


  chartGridElementSize = {
    height: 5,
    width: 10,
  }


  chartGridStyle() {
    return `
      grid-template-rows: repeat(${this.chartGridNumRows}, 20px);
      grid-template-columns: repeat(${this.chartGridNumColumns}, minmax(10px, 1fr);
    `
  }


  chartElementStyle() {
    return `
       grid-row: span ${this.chartGridElementSize.height};
       grid-column: span ${this.chartGridElementSize.width};
    `
  }
}
