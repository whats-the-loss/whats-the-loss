import {AfterViewInit, Component, effect, input, OnInit} from '@angular/core';
import type { ECharts, EChartsOption } from 'echarts';
import { connect, disconnect } from 'echarts';
import {NgxEchartsDirective, ThemeOption} from "ngx-echarts";
import {animation} from "@angular/animations";
import {retry} from "rxjs";
import {DASHBOARD_ECHARTS_GROUP_NAME} from "../../run-dashboard.definitions";

@Component({
  selector: 'wtl-chart-grid-element',
  standalone: true,
  imports: [
    NgxEchartsDirective
  ],
  templateUrl: './chart-grid-element.component.html',
  styleUrl: './chart-grid-element.component.scss'
})
export class ChartGridElementComponent implements OnInit, AfterViewInit {
  showTooltip = input(true)

  chartOptions: EChartsOption = {};
  private echartsInstance: ECharts | null = null;
  chartTheme: string | ThemeOption = 'dark';
  updateOptions: EChartsOption = {};


  data = [
    {name: 'Run1', data: [[0, 0]]},
    {name: 'Run2', data: [[0, 0]]},
    {name: 'Run3', data: [[0, 0]]},
  ]

  constructor() {
    effect(() => {
      this.updateOptions = {
        tooltip: {
          show: this.showTooltip()
        }
      }
    });
  }


  ngOnInit(): void {
    // setup chart
    const xAxisData = [];
    const data1 = [];
    const data2 = [];

    for (let i = 0; i < 100; i++) {
      xAxisData.push(i);
      data1.push((Math.sin(i / 5) * (i / 5 - 10) + i / 6) * 5);
      data2.push((Math.cos(i / 5) * (i / 5 - 10) + i / 6) * 5);
    }

    this.chartOptions = {
      animation: false,
      backgroundColor: 'rgba(0, 0, 0, 0)',
      textStyle: {
        //color: '#ffffff'
      },
      title: {
        name: 'test'
      },
      /*
      legend: {
        data: ['super long name 1, does it fit?', 'other long name'],
        align: 'right',
      },
      */
      tooltip: {
        trigger: 'axis',
        position: function (pos, params, el, elRect, size) {
          //return [pos[0]+100, '10%'];
          const posOut: any = { bottom: size.viewSize[1]/2 }
          if (! (pos[0] < size.viewSize[0] / 2)) {
            //posOut['right'] = -size.contentSize[0];//elRect?.width
            posOut['right'] = -pos[0] + size.viewSize[0] + 30//-size.contentSize[0];//elRect?.width
          }
          else {
            posOut['left'] = pos[0] + 30//-size.contentSize[0];
          }
          //posOut['right'] = -pos[0] + size.viewSize[0]
          return posOut
        },
        /*
        formatter: function(params) {
          return params[0].name + ': ' + params[0].value;
        },
        */
        axisPointer: {
          type: 'line',
        },
        backgroundColor: '#111111',//'#dadada',
        textStyle: {
          //overflow: 'breakAll'
          width: 20,
          color: '#b6b6b6',
          fontStyle: "normal",
          fontWeight: "normal"
        },
        borderColor: '#b6b6b6',
        transitionDuration: 0,
        confine: false,
        z: 100000
      },
      grid: {
        bottom: 35
      },
      xAxis: {
        data: xAxisData,
        silent: false,
        splitLine: {
          show: false,
        },
        name: 'Step',
        nameLocation: 'middle',
        nameGap: 20,
        axisLine: {
          onZero: false
        }
      },
      yAxis: {
      },
      dataZoom: {
        type: 'inside',
        zoomOnMouseWheel: 'shift',
        moveOnMouseMove: true,
        preventDefaultMouseMove: false,
        zoomLock: true
      },
      toolbox: {
        feature: {
          dataZoom: {
            show: false
            //show: true
          }
        }
      }
      /*
      series: [
        {
          name: 'super long name 1, does it fit?',
          type: 'line',
          data: ,
          //animationDelay: idx => idx * 10,
        },
        {
          name: 'other long name',
          type: 'line',
          data: data2,
          //animationDelay: idx => idx * 10 + 100,
        },
      ],
      */
      //animationEasing: 'elasticOut',
      //animationDelayUpdate: idx => idx * 5,
    };
  }

  ngAfterViewInit(): void {
    // update the chart
    //this.chartOptions.series = [...this.chartOptions.series]
    //this.echartsInstance.
    //this.chartOptions.series = [this.chartOptions.series![0]]

    setInterval(() => {
      this.updateFakeData()
    }, 200*10)
  }

  onChartInit(instance: ECharts) {
    this.echartsInstance = instance
    this.echartsInstance.group = DASHBOARD_ECHARTS_GROUP_NAME
    //connect('dashboard-charts')
    //this.echartsInstance.showLoading()

    this.updateFakeData(100)
  }


  fakeDataLastStep = 0
  fakeDataXs: number[] = []
  updateFakeData(num: number = 1) {
    for (let i = 0; i < num; i++) {
      for (let i = 0; i < this.data.length; i++) {
        const lastY = this.data[i].data[this.data[i].data.length - 1][1]
        this.data[i].data.push(
          [this.fakeDataLastStep, lastY + Math.random() - 0.5]
        )
      }
      this.fakeDataXs.push(this.fakeDataLastStep)
      this.fakeDataLastStep += 1
    }

    // @todo use dataset: https://echarts.apache.org/handbook/en/concepts/dataset
    this.updateOptions = {
      series: this.data.map(s => {
        return {
          name: s.name,
          type: 'line',
          data: s.data,
          smooth: false
        }
      }),
      /*
      legend: {
        data: this.data.map(s => s.name)
      },
       */
      xAxis: {
        max: this.fakeDataLastStep,
        data: this.fakeDataXs
      }
    };

  }
}
