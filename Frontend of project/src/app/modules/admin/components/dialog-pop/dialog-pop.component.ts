import { DatePipe } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PhysicianAvailabilityService } from 'src/app/services/physician-availability.service';
import { PhysicianComponent } from '../physician/physician.component';

@Component({
  selector: 'app-dialog-pop',
  templateUrl: './dialog-pop.component.html',
  styleUrls: ['./dialog-pop.component.css']
})
export class DialogPopComponent {
  available: any;
  physicianAvail: any;
  currentDate: any;
  constructor(@Inject(MAT_DIALOG_DATA) private data: any, physicianAvailabilty: PhysicianAvailabilityService) {
    this.available = this.data.availability;
    this.physicianAvail = physicianAvailabilty;
    this.currentDate = new Date();
  }
  range = new FormGroup({
    start: new FormControl(),
    end: new FormControl()
  },
  )
  update() {
    var newStartDate = new DatePipe('en-us').transform(this.range.value.start, 'dd-MMM-yyyy');
    var newEndDate = new DatePipe('en-us').transform(this.range.value.end, 'dd-MMM-yyyy');
    if (newStartDate == null || newEndDate == null)
      console.log("Date is invalied");
    else {
      this.available.startDate = newStartDate;
      this.available.endDate = newEndDate;
      this.physicianAvail.updateStartAndEndDate(this.available).subscribe();
    }
  }
}
