import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { RegisterDialogComponent } from 'src/app/components/dialog-pop/dialog-pop.component';
import { Patient } from 'src/app/model_classes/Patient';
import { PatientBasicInfoService } from 'src/app/services/patient-basic-info.service';
import { PatientRegisterService } from 'src/app/services/patient-register.service';

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.css']
})
export class UpdateProfileComponent {
  private patient: Patient;
  myForm: any;
  submitted = false;
  hide = true;
  hide0 = true;
  currentDate!: Date;
  check = false;
  PatientId: any;
  patients: Patient[] = [];
  constructor(private formBuilder: FormBuilder, private matDialog: MatDialog, private _snackBar: MatSnackBar, private patientInfo: PatientBasicInfoService, private patientRegister: PatientRegisterService, private router: Router) {
    this.patient = new Patient();
  }
  ngOnInit() {
    // this.currentDate = new DatePipe('en-us');
    this.currentDate = new Date();
    this.myForm = new FormGroup({
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      contactNumber: new FormControl('', [Validators.required, Validators.pattern("^((\\+91-?)|0)?[6789][0-9]{9}$")]),
      address: new FormControl('', [Validators.required]),
    }
    );

    this.patientInfo.getPatients().subscribe(data => {
      this.patients = data;
    });
    this.PatientId = sessionStorage.getItem("PATIENT_ID")

    this.patientInfo.getpatientdetails(this.PatientId).subscribe(data => {
      this.patient = data;
    });
  }

  public get register() {
    return this.myForm.controls;
  }
  onSubmit(): void {
    this.submitted = true;
    console.log(this.patients);
    console.log(this.myForm.value);
    if (this.myForm.valid) {
      if (this.patients.length != 0) {
        for (var p of this.patients) {
          if (p.email == this.myForm.value.email && this.patient.email != this.myForm.value.email) {
            this.check = true;
            break;
          }
        }
        if (this.check) {
          this.openSnackBar();
          this.check = false;
        }
        else {

          this.patient = this.myForm.value;
          this.patientInfo.updatePatientById(this.PatientId, this.patient).subscribe((result) => {
            if (result != null) {
              console.log("dddddddddddddddddd");
              console.log(result);
              this.patients.push(result);
              this.registerDialog();

            }
          });
        }
      }
    }
    else {
      console.log("form not valid");
    }

  }
  gotoUserList() {
    this.router.navigate(['login']);
  }
  registerDialog() {
    const dialog = this.matDialog.open(RegisterDialogComponent, {
      width: '300px',
      data: {
        value: "Successfully Updated"
      }
    })
    dialog.afterClosed().subscribe(result => {
      this.router.navigateByUrl('/patient/profile')
    })
  }
  openSnackBar() {
    this._snackBar.open('Please choose another email', 'Close', {
      duration: 3000
    });

  }
}
