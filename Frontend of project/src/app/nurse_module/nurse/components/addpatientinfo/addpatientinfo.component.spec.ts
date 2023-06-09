import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddpatientinfoComponent } from './addpatientinfo.component';

describe('AddpatientinfoComponent', () => {
  let component: AddpatientinfoComponent;
  let fixture: ComponentFixture<AddpatientinfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddpatientinfoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddpatientinfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
