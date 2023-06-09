import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestdetailsComponent } from './testdetails.component';

describe('TestdetailsComponent', () => {
  let component: TestdetailsComponent;
  let fixture: ComponentFixture<TestdetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestdetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TestdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
