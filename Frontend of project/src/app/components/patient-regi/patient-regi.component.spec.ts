import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientRegiComponent } from './patient-regi.component';

describe('PatientRegiComponent', () => {
  let component: PatientRegiComponent;
  let fixture: ComponentFixture<PatientRegiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientRegiComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PatientRegiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
