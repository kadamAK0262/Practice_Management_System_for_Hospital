import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PhysicianAvailabilityService } from 'src/app/services/physician-availability.service';
import { PhysicianComponent } from '../physician/physician.component';

@Component({
  selector: 'app-delete-popup',
  templateUrl: './delete-popup.component.html',
  styleUrls: ['./delete-popup.component.css']
})
export class DeletePopupComponent {

  email: string = "";
  physicianAvail: any;
  deletePhysician: any;
  constructor(@Inject(MAT_DIALOG_DATA) private data: any, physicianAvailabilty: PhysicianAvailabilityService) {
    this.email = this.data.physicianEmail;
    this.physicianAvail = physicianAvailabilty;
    this.deletePhysician = data.object;
  }
  delete() {
    this.physicianAvail.deletePhysicianAvailabilityById(this.email).subscribe({
      next: (res: any) => {
        this.deletePhysician.getAllPhysicianAvailability();
      }
    })
  }
}
