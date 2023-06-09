import { animate, style, transition, trigger } from '@angular/animations';
import { LowerCasePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { PatientBasicInfoService } from 'src/app/services/patient-basic-info.service';
import { PatientRegisterService } from 'src/app/services/patient-register.service';

@Component({
  selector: 'app-forgotpssword',
  templateUrl: './forgotpssword.component.html',
  styleUrls: ['./forgotpssword.component.css'],
  animations: [
    trigger('fadein', [
      transition('void => *', [
        style({ opacity: 0 }),
        animate(1000, style({ opacity: 1 }))
      ])
    ])
  ]
})
export class ForgotpasswordComponent implements OnInit {


  patientEmail!: string;
  newPassword!: string;
  retypePassword!: string;
  showOTP: boolean = false;
  showNewPassword: boolean = false;
  showResendOTP: boolean = false;
  showPasswordMismatch: boolean = false;

  emailbutton: boolean = true
  verifybutton: boolean = true

  patiets: any[] = []

  // email:any[]=[]
  hide: boolean = true;
  hide0: boolean = true;
  emailExists: boolean | undefined;
  emailToCheck: any;
  patientemail: any;
  userEmail!: string;
  userOtp!: string;
  randomNum: any
  forgotPassword: any;
  submitted = false;
  otpVerify: any;
  password: any
  constructor(private _snackBar: MatSnackBar, private router: Router, private service: PatientRegisterService, private patients: PatientBasicInfoService) { }

  //  
  ngOnInit(): void {
    this.forgotPassword = new FormGroup({
      userEmail: new FormControl('', [Validators.required, Validators.email])
    });
    this.otpVerify = new FormGroup({
      otp: new FormControl('', Validators.required)
    });
    this.password = new FormGroup({
      newpassword: new FormControl('', [Validators.required]),
      confirmpassword: new FormControl('', Validators.required)
    })
  }
  get forgot() {
    return this.forgotPassword.controls;
  }

  get getOtp() {
    return this.otpVerify.controls;
  }
  get register() {
    return this.password.controls;
  }
  sendOTP() {
    this.submitted = true;
    if (this.forgotPassword.valid) {

      this.patients.getPatients().subscribe((data: any) => {
        this.patiets = data
        this.patientEmail = this.forgotPassword.value.userEmail
        this.emailExists = this.patiets.some(user => new LowerCasePipe().transform(this.patientEmail) === user.email)
        if (this.emailExists) {
          this.openSnackBar('OTP sent on registered email!');
          this.showOTP = true;
          const min = 100000; // 6 digit number starting from 100000
          const max = 999999; // 6 digit number ending at 99999
          this.randomNum = Math.floor(Math.random() * (max - min + 1)) + min;

          sessionStorage.setItem("ResetOtp", this.randomNum)
          const emailData = {
            toMail: [this.patientEmail],
            subject: 'Forgot password',
            message: 'enter otp to reset password  ' + this.randomNum
          };
          this.service.forgotpassword(emailData).subscribe()
          this.emailbutton = false
        } else {
          this.errorSnackBar('Email does not exist!');
        }
      });
    }
  }
  resendOtp() {
    this.openSnackBar('OTP resent on registered email!');

    this.showOTP = true;
    const min = 100000; // 6 digit number starting from 100000
    const max = 999999; // 6 digit number ending at 99999
    this.randomNum = Math.floor(Math.random() * (max - min + 1)) + min;

    sessionStorage.setItem("ResetOtp", this.randomNum)
    const emailData = {
      toMail: [this.patientEmail],
      subject: 'Forgot password',
      message: 'enter otp to reset password  ' + this.randomNum
    };
    this.service.forgotpassword(emailData).subscribe()
  }
  verifyOTP() {
    this.submitted = true;
    if (this.otpVerify.valid) {
      if (sessionStorage.getItem("ResetOtp") === this.otpVerify.value.otp) {
        this.showNewPassword = true;
        this.showOTP = false
        this.showResendOTP = false;
      } else {
        this.errorSnackBar('Wrong Otp entered!');
        this.showResendOTP = true;
      }
    }
  }

  resetPassword() {
    this.submitted = true;
    if (this.password.valid) {
      if (this.password.value.newpassword === this.password.value.confirmpassword) {
        this.service.updatePatientPassword(this.forgotPassword.value.userEmail, this.password.value.newpassword).subscribe()
        this.openSnackBar('Successfully Updated Password!');
        this.router.navigateByUrl('/login');
      } else {
        this.errorSnackBar('Password Not Matched!');
      }
    }
  }
  openSnackBar(value: string) {
    this._snackBar.open(value, 'Close', {
      horizontalPosition: 'left',
      verticalPosition: 'bottom',
      duration: 3000,
      panelClass: ['snackbar']
    });
  }

  errorSnackBar(value: string) {
    this._snackBar.open(value, 'Close', {
      duration: 3000,
      horizontalPosition: 'left',
      verticalPosition: 'bottom',
      panelClass: ['red_snackbar']
    });
  }
}
