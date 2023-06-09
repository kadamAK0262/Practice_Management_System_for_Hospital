import { Component, ViewChild } from '@angular/core';
import { MatAccordion } from '@angular/material/expansion';

@Component({
  selector: 'app-side-nav-items',
  templateUrl: './side-nav-items.component.html',
  styleUrls: ['./side-nav-items.component.css']
})
export class SideNavItemsComponent {
  @ViewChild(MatAccordion)
  accordion!: MatAccordion;
  step = -1;
  setStep(index: number) {
    this.step = index;
  }
}
