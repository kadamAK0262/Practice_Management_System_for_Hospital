import { Component, EventEmitter, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Patient } from 'src/app/model_classes/Patient';
import { PatientInfoDetails } from 'src/app/model_classes/visit_details';
import { AllergyService } from 'src/app/services/allergy.service';
import { AppointmentService } from 'src/app/services/appointment.service';
import { AppsubService } from 'src/app/services/appsub.service';

@Component({
  selector: 'app-submit-dialog',
  templateUrl: './submit-dialog.component.html',
  styleUrls: ['./submit-dialog.component.css']
})
export class SubmitDialogComponent {
  email: string = '';
  physicianAvail: any;
  deletePhysician: any;
  patientIdd: any;
  appointmentEvent: any;
  data: PatientInfoDetails = new PatientInfoDetails();

  constructor(
    private shared: AppsubService,
    private service: AppointmentService,
    private route: Router,
    private _snackBar: MatSnackBar,
    private event: AllergyService,
    @Inject(MAT_DIALOG_DATA) private dataa: any
  ) {
    this.data = this.dataa.submitPatienttDetails;
    this.patientIdd = this.dataa.patientInfoId;
  }
  submit() {
    this.appointmentEvent = new EventEmitter();
    this.service.sendPatientInfo(this.data, this.patientIdd).subscribe(result => {
      if (result != null) {
        this.openSnackBar();
        this.service.updateStatusById(this.dataa.appointmentId, "accepted").subscribe(() => {
          this.event.setAppointmentEvent(this.appointmentEvent);
          this.appointmentEvent.emit();
          this.route.navigateByUrl('/nurse/appointments',);
        });

      }
    });
  }
  openSnackBar() {
    this._snackBar.open('Visit Done Succesfully', 'Close', {
      horizontalPosition: 'left',
      verticalPosition: 'bottom',
      duration: 2000,
      panelClass: ['snackbar']
    });
  }
}
