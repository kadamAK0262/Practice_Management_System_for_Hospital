import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Inject } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { PatientRegiComponent } from 'src/app/components/patient-regi/patient-regi.component';
import { AppointmentService } from 'src/app/services/appointment.service';
import { PatientRegisterService } from 'src/app/services/patient-register.service';
import { PhysicianAvailabilityService } from 'src/app/services/physician-availability.service';
import { Location } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';
@Component({
  selector: 'app-dialog-pop',
  templateUrl: './dialog-pop.component.html',
  styleUrls: ['./dialog-pop.component.css']
})

export class AcceptDialogComponent {
  email: any;
  id: any;
  status: any;
  physicianEmail: any;
  objPending: any
  update: any;
  constructor(private _snackBar: MatSnackBar, private router: Router, @Inject(MAT_DIALOG_DATA) private data: any,
    private service: AppointmentService, private registerService: PatientRegisterService,
    private location: Location, private route: ActivatedRoute) {
    this.email = this.data.newEmail;
    this.id = this.data.newId;
    this.status = this.data.status;
    this.physicianEmail = this.data.physicianEmail;
    this.objPending = this.data.obj;
    this.update = this.data.update;
  }

  onAcceptAndReject() {
    const emailData = {
      toMail: [this.email],
      subject: 'Appointment ' + this.status,
      message: 'Your Appointment with the ' + this.physicianEmail + ' has been ' + this.status
    };
    this.service.updateStatusById(this.id, this.status).subscribe(data => {
      if (this.status == 'acceptance')
        this.openSnackBar();
      else if (this.status == 'rejected')
        this.rejectSnackBar();
      this.update.emit();
    });
    this.registerService.forgotpassword(emailData).subscribe();
  }

  openSnackBar() {
    this._snackBar.open('Appointment Accepted Succesfully!\nNurse Will update basic Diagnosis', 'Close', {
      horizontalPosition: 'left',
      verticalPosition: 'bottom',
      duration: 2000,
      panelClass: ['snackbar']
    });
  }
  rejectSnackBar() {
    this._snackBar.open('Appointment Rejected Succesfully', 'Close', {
      horizontalPosition: 'left',
      verticalPosition: 'bottom',
      duration: 2000,
      panelClass: ['red_snackbar']
    });
  }
}
