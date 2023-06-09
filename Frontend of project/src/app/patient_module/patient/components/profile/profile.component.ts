import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ResetpasswordComponent } from '../resetpassword/resetpassword.component';
import { PatientBasicInfoService } from 'src/app/services/patient-basic-info.service';
import { Patient } from 'src/app/model_classes/Patient';
import { UpdateProfileComponent } from '../update-profile/update-profile.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  public patientId: any;
  public patient!: Patient;
  constructor(public dialog: MatDialog, public patientService: PatientBasicInfoService) {

  }
  ngOnInit() {
    this.patientId = sessionStorage.getItem("PATIENT_ID");
    this.patientService.getpatientdetails(this.patientId).subscribe(data => this.patient = data);
  }
  openDialog() {
    this.dialog.open(UpdateProfileComponent)
  }
}
