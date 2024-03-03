import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RunsListPageComponent } from './runs-list-page.component';

describe('RunsListPageComponent', () => {
  let component: RunsListPageComponent;
  let fixture: ComponentFixture<RunsListPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RunsListPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RunsListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
