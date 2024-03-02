import {Component, Input, input, ViewEncapsulation} from '@angular/core';
import {
  MatAccordion,
  MatExpansionPanel,
  MatExpansionPanelDescription,
  MatExpansionPanelHeader, MatExpansionPanelTitle
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
    MatExpansionPanelTitle,
    MatIcon,
    ChartGridElementComponent
  ],
  templateUrl: './chart-grid.component.html',
  styleUrl: './chart-grid.component.scss',
  encapsulation: ViewEncapsulation.None  // to change style of expansion card
})
export class ChartGridComponent {
  @Input({ required: true }) panelPath: string = '??'


  chartsElements = [
    {id: 0, name: 'Test'},
    {id: 1, name: 'Test'},
    {id: 3, name: 'Test'},
    {id: 3, name: 'Test'},
    {id: 3, name: 'Test'},
    {id: 3, name: 'Test'},
  ];

  chartGridElementSize = {
    height: 200,
    width: 300,
  }


  // @todo add drag drop support (e.g. via ng-dnd: https://stackblitz.com/edit/867pxw?file=src%2Fexample%2Fchips-drag-drop-example.ts)
  chartElementStyle() {
    return `
       height: ${this.chartGridElementSize.height}px;
       width: ${this.chartGridElementSize.width}px;
    `
    //minmax(100px, ${this.chartGridElementSize.width}fr);
  }
}
