import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CustomDatePipePipe } from 'src/app/patient_service/custom-date-pipe.pipe';
import { BookappointmentComponent } from '../bookappointment/bookappointment.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AppointmentDto } from 'src/app/model_classes/appointment';
import { AppointmentService } from 'src/app/services/appointment.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-dialog-appointment',
  templateUrl: './dialog-appointment.component.html',
  styleUrls: ['./dialog-appointment.component.css']
})
export class DialogAppointmentComponent implements OnInit {
  hide = true;
  minDate: Date;
  bookAppointment: any
  maxDate: Date;
  submitted = false;
  private appointment: AppointmentDto;
  constructor(private _snackBar: MatSnackBar, private appointmentService: AppointmentService, private router: Router, private dialogRef: MatDialogRef<BookappointmentComponent>, @Inject(MAT_DIALOG_DATA) public data: BookappointmentComponent) {
    this.minDate = new Date(this.data.dataStartDate);
    this.maxDate = new Date(this.data.dataEndDate);
    this.appointment = new AppointmentDto();
  }
  ngOnInit() {
    this.bookAppointment = new FormGroup({
      date: new FormControl('', [Validators.required]),
      reason: new FormControl('', [Validators.required]),
    });
  }
  get book() {
    return this.bookAppointment.controls;
  }
  // get login() {
  //   return this.loginForm.controls;
  // }
  onBook(): void {
    this.submitted = true;
    if (this.bookAppointment.valid) {
      this.appointment.acceptance = "pending";
      this.appointment.date = new CustomDatePipePipe('en-us').transform(this.bookAppointment.value.date, 'dd-MMM-yyyy');
      this.appointment.reason = this.bookAppointment.value.reason;
      this.appointment.physcianEmail = this.data.dataEmail;
      this.appointment.patientId = sessionStorage.getItem("PATIENT_ID");
      this.appointment.submissionDate = new CustomDatePipePipe('en-us').transform(new Date(), 'dd-MMM-yyyy');
      console.log(this.appointment);
      this.appointmentService.bookAppointment(this.appointment).subscribe(result => {
        if (result != null) {
          this.openSnackBar();
          this.gotoPrevious();
        }
        else {
          this.errorSnackBar();
        }
      });
    }
  }
  gotoPrevious() {
    this.closeDialog();
    this.router.navigate(['/patient/bookappointment']);
  }
  closeDialog() {
    this.dialogRef.close();
  }
  openSnackBar() {
    this._snackBar.open('Appointment Booked Succesfully', 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 2000,
      panelClass: ['snackbar']
    });
  }

  errorSnackBar() {
    this._snackBar.open('Failed to Appoint Physician', 'Close', {
      duration: 2000
    });
  }
}
