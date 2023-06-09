import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForgotpsswordComponent } from './forgotpssword.component';

describe('ForgotpsswordComponent', () => {
  let component: ForgotpsswordComponent;
  let fixture: ComponentFixture<ForgotpsswordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ForgotpsswordComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ForgotpsswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
