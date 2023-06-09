import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Patient } from 'src/app/model_classes/Patient';
import { CustomDatePipePipe } from 'src/app/patient_service/custom-date-pipe.pipe';
import { PatientBasicInfoService } from 'src/app/services/patient-basic-info.service';
import { PatientRegisterService } from 'src/app/services/patient-register.service';
import { RegisterDialogComponent } from '../dialog-pop/dialog-pop.component';
import { MatDialog } from '@angular/material/dialog';
import { animate, style, transition, trigger } from '@angular/animations';
import { DatePipe, LowerCasePipe, UpperCasePipe } from '@angular/common';
@Component({
  selector: 'app-patient-regi',
  templateUrl: './patient-regi.component.html',
  styleUrls: ['./patient-regi.component.css'],
  animations: [
    trigger('fadein', [
      transition('void => *', [
        style({ opacity: 0 }),
        animate(1000, style({ opacity: 1 }))
      ])
    ])
  ]
})
export class PatientRegiComponent {
  private patient: Patient;
  myForm: any;
  submitted = false;
  hide = true;
  hide0 = true;
  currentDate!: Date;
  check = false;
  passwordFormControl: any;
  confirmPasswordFormControl: any;
  patients: Patient[] = [];
  constructor(private formBuilder: FormBuilder, private matDialog: MatDialog, private _snackBar: MatSnackBar, private patientInfo: PatientBasicInfoService, private patientRegister: PatientRegisterService, private router: Router) {
    this.patient = new Patient();
  }
  ngOnInit() {
    // this.currentDate = new DatePipe('en-us');
    this.currentDate = new Date();
    this.myForm = this.formBuilder.group({
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      contactNumber: new FormControl('', [Validators.required, Validators.pattern("^((\\+91-?)|0)?[6789][0-9]{9}$")]),
      dob: new FormControl('', [Validators.required]),
      gender: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
      confirmpassword: new FormControl('', [Validators.required]),
      title: new FormControl('', [Validators.required]),
      address: new FormControl('', [Validators.required]),
    },
      { validator: this.passwordMatchValidator }
    );
    this.passwordFormControl = this.myForm.get('password');
    this.confirmPasswordFormControl = this.myForm.get('confirmpassword');
    this.patientInfo.getPatients().subscribe(data => {
      this.patients = data;
    });
  }
  public get register() {
    return this.myForm.controls;
  }

  passwordMatchValidator(formGroup: FormGroup) {
    const password = formGroup.controls['password'];
    const confirmPassword = formGroup.controls['confirmpassword'];
    if (password.value !== confirmPassword.value) {
      confirmPassword.setErrors({ matchPassword: true });
    } else {
      confirmPassword.setErrors(null);
    }
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.myForm.valid) {
      if (this.patients.length != 0) {
        for (var p of this.patients) {
          if (p.email == this.myForm.value.email) {
            this.check = true;
            break;
          }
        }
        if (this.check) {
          this.openSnackBar();
          this.check = false;
        }
        else {
          console.log("registered")
          //register
          this.myForm.value.dob = new CustomDatePipePipe('en-us').transform(this.myForm.value.dob, 'dd-MMM-yyyy');
          console.log("post registration")
          this.myForm.value.email = new LowerCasePipe().transform(this.myForm.value.email);
          console.log(this.myForm.value);
          this.patient = this.myForm.value;
          this.patientRegister.registerPatient(this.patient).subscribe((result) => {
            if (result != null) {
              console.log("dddddddddddddddddd");
              console.log(result);
              this.patients.push(result);
              this.registerDialog();

            }
          });
        }
      }
      else {
        this.myForm.value.dob = new CustomDatePipePipe('en-us').transform(this.myForm.value.dob, 'dd-MMM-yyyy');
        console.log("post registration")
        this.patient = this.myForm.value;
        this.patientRegister.registerPatient(this.patient).subscribe((result) => {
          if (result != null) {
            console.log("sddddddddddddddd")
            console.log(result);
            this.patients.push(result);
            this.registerDialog();
          }
        });
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
        value: "Successfully Registered!"
      }
    })
    dialog.afterClosed().subscribe(result => {
      this.router.navigateByUrl('/login')
    })
  }
  openSnackBar() {
    this._snackBar.open('Please choose another email', 'Close', {
      horizontalPosition: 'left',
      verticalPosition: 'bottom',
      panelClass: ['red_snackbar'],
      duration: 3000
    });

  }
}
