import { TestBed } from '@angular/core/testing';

import { ChartSyncService } from './chart-sync.service';

describe('ChartSyncService', () => {
  let service: ChartSyncService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChartSyncService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
