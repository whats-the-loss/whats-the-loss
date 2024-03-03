import {Injectable, signal} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ChartSyncService {

  chartUsedContext = signal({
    project: '',
    experiment: '',
  })
  chartStepCursor = signal(0)

  constructor() { }
}
