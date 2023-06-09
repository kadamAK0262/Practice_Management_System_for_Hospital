import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PdfviewComponent } from './pdfview.component';

describe('PdfviewComponent', () => {
  let component: PdfviewComponent;
  let fixture: ComponentFixture<PdfviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PdfviewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PdfviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
