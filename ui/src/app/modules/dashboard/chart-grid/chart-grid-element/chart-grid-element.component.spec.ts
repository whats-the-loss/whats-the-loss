import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChartGridElementComponent } from './chart-grid-element.component';

describe('ChartGridElementComponent', () => {
  let component: ChartGridElementComponent;
  let fixture: ComponentFixture<ChartGridElementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChartGridElementComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ChartGridElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
