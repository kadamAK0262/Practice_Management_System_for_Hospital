import { TestBed } from '@angular/core/testing';

import { PatientBasicInfoService } from './patient-basic-info.service';

describe('PatientBasicInfoService', () => {
  let service: PatientBasicInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PatientBasicInfoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
