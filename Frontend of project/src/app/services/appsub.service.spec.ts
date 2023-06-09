import { TestBed } from '@angular/core/testing';

import { AppsubService } from './appsub.service';

describe('AppsubService', () => {
  let service: AppsubService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AppsubService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
