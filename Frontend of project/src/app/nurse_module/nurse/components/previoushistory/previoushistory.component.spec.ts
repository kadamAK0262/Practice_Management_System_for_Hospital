import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrevioushistoryComponent } from './previoushistory.component';

describe('PrevioushistoryComponent', () => {
  let component: PrevioushistoryComponent;
  let fixture: ComponentFixture<PrevioushistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrevioushistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrevioushistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
