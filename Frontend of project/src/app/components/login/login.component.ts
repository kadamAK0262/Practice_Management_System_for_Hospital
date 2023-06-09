import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Patient } from 'src/app/model_classes/Patient';
import { PatientBasicInfoService } from 'src/app/services/patient-basic-info.service';
import { PatientRegisterService } from 'src/app/services/patient-register.service';
import { RegisterDialogComponent } from '../dialog-pop/dialog-pop.component';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from 'src/app/services/authservice.service';
import { catchError, throwError } from 'rxjs';
import { LowerCasePipe } from '@angular/common';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: any;
  hide = true;
  submitted = false;
  patients: Patient[];
  responseData: any;
  constructor(private loginService: AuthService, private matDialog: MatDialog, private patientRegister: PatientRegisterService, private patientInfo: PatientBasicInfoService, private router: Router) {
    this.patients = [];
  }
  ngOnInit() {
    this.patientInfo.getPatients().subscribe(data => {
      this.patients = data;
      localStorage.clear();
    });
    this.loginForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required]),
    });
  }
  get login() {
    return this.loginForm.controls;
  }
  onLogin() {
    this.submitted = true;
    if (this.loginForm.valid) {
      this.patientRegister.loginPatient(new LowerCasePipe().transform(this.loginForm.value.email), this.loginForm.value.password)
        .pipe(
          catchError(error => {
            console.log('An error occurred:', error);
            this.loginDialog();
            return throwError(error);
          })
        )
        .subscribe(result => {
          if (result != null) {
            sessionStorage.clear();
            this.loginService.login(new LowerCasePipe().transform(this.loginForm.value.email));
            sessionStorage.setItem("PATIENT_ID", result.patientId);
            sessionStorage.setItem("PATIENT_OBJ", JSON.stringify(result));
            this.router.navigateByUrl("/patient/bookappointment")
          }
        })
    }
  }

  loginDialog() {
    this.matDialog.open(RegisterDialogComponent, {
      width: '300px',
      data: {
        value: "Login Unsuccessful!"
      }
    })
  }
}
