import { TestBed } from '@angular/core/testing';

import { DisplayDoctorsService } from './display-doctors.service';

describe('DisplayDoctorsService', () => {
  let service: DisplayDoctorsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DisplayDoctorsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
