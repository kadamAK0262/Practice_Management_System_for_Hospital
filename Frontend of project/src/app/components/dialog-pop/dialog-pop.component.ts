import { DatePipe } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PhysicianAvailabilityService } from 'src/app/services/physician-availability.service';

@Component({
  selector: 'app-dialog-pop',
  templateUrl: './dialog-pop.component.html',
  styleUrls: ['./dialog-pop.component.css']
})
export class RegisterDialogComponent {
  value: any;
  constructor(@Inject(MAT_DIALOG_DATA) private data: any) {
    this.value = this.data.value;
  }
}
