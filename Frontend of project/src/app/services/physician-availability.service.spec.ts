import { TestBed } from '@angular/core/testing';

import { PhysicianAvailabilityService } from './physician-availability.service';

describe('PhysicianAvailabilityService', () => {
  let service: PhysicianAvailabilityService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PhysicianAvailabilityService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
