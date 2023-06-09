import { TestBed } from '@angular/core/testing';

import { PatientHealthRecordService } from './patient-health-record.service';

describe('PatientHealthRecordService', () => {
  let service: PatientHealthRecordService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PatientHealthRecordService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
