import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RunsSidepanelComponent } from './runs-sidepanel.component';

describe('RunsSidepanelComponent', () => {
  let component: RunsSidepanelComponent;
  let fixture: ComponentFixture<RunsSidepanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RunsSidepanelComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RunsSidepanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
