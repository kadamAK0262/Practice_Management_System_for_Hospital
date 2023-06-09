import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldControl } from '@angular/material/form-field';
import { Patient } from 'src/app/model_classes/Patient';
import { PatientInfoDetails } from 'src/app/model_classes/visit_details';
import { AppsubService } from 'src/app/services/appsub.service';
import { PatientBasicInfoService } from 'src/app/services/patient-basic-info.service';
import { PrevioushistoryComponent } from '../previoushistory/previoushistory.component';
import { SubmitDialogComponent } from '../submit-dialog/submit-dialog.component';
import { AllergyService } from 'src/app/services/allergy.service';
import { AppointmentService } from 'src/app/services/appointment.service';
import { Allergy } from 'src/app/model_classes/allergy';
import { PatientHealthRecordService } from 'src/app/services/patient-health-record.service';
import { AppointmentsComponent } from '../appointments/appointments.component';
import { AppointmentDto } from 'src/app/model_classes/appointment';

@Component({
  selector: 'app-addpatientinfo',
  templateUrl: './addpatientinfo.component.html',
  styleUrls: ['./addpatientinfo.component.css']
})
export class AddpatientinfoComponent {
  [x: string]: any;
  public myForm: any;
  constructor(
    public dialog: MatDialog,
    public patientService: PatientBasicInfoService,
    private allergyService: AllergyService,
    private healthRecord: PatientHealthRecordService,
    private appointment: AppointmentService
  ) {
  }
  selectedItem: any;
  nurseEmail = sessionStorage.getItem("NURSE_EMAIL");
  selectedBlood: any;
  bloodgroup: string[] = [
    'A+',
    'A-',
    'B+',
    'B-',
    'AB+',
    'AB-',
    'O+',
    'O-'
  ];

  openDialog() {
    const dialogRef = this.dialog.open(PrevioushistoryComponent, {
      width: '80%',
    });
    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
    });
  }
  appointmentAppointmentsId: any;
  phys: any;
  patientData: any;
  patient!: Patient;
  submitted = false;
  appointmentsHistory: AppointmentDto[] = [];
  allergyData: Allergy[] = [];
  allergyIds: Allergy[] = [];
  toppings = new FormControl();
  selectedToppings: [] = [];
  ailment: any;
  keyNotes: any;
  lastPhysicianEmail: any;
  lastConsultationDate: any;
  ngOnInit(): void {
    this.patientData = sessionStorage.getItem('arraydata');
    this.ailment = sessionStorage.getItem('ailment');
    this.patientService.getpatientdetails(this.patientData).subscribe((data) => {
      this.patient = data;
    });
    this.appointmentAppointmentsId = sessionStorage.getItem('appId');
    this.phys = sessionStorage.getItem('physicianEmail');
    this.allergyService.getAllergies().subscribe((data: any) => {
      this.allergyData = data;
    });
    this.getRecentAppointment();
    this.myForm = new FormGroup({
      heightt: new FormControl('', [Validators.required, Validators.max(200), Validators.min(20), Validators.pattern('[0-9]+')]),
      bpSystolic: new FormControl('', [Validators.required, Validators.min(20), Validators.max(140), Validators.pattern('[0-9]+')]),
      weightt: new FormControl('', [Validators.required, Validators.max(500), Validators.min(3), Validators.pattern('[0-9]+')]),
      bpDiastolic: new FormControl('', [Validators.required, Validators.min(20), Validators.max(140), Validators.pattern('[0-9]+')]),
      respirationRate: new FormControl('', [Validators.required, Validators.min(12), Validators.max(40), Validators.pattern('[0-9]+')]),
      bodyTemperature: new FormControl('', [Validators.required, Validators.min(50), Validators.max(115), Validators.pattern('[0-9]+')]),
      allergies: new FormControl('', [Validators.required]),
      bloodgroup: new FormControl(),
      sugar: new FormControl('', [Validators.required, Validators.min(15), Validators.max(300), Validators.pattern('[0-9]+')]),
      somethingData: new FormControl('', [Validators.required, Validators.pattern("[a-zA-Z0-9 ]+"), Validators.maxLength(300)]),
    });

  }

  getRecentAppointment() {
    const appointmentHistory = this.appointment.getAppointmentByStatusAndId(this.patientData, 'completed', 0, 20)
    appointmentHistory.subscribe((data) => {

      if (data != null) {
        this.appointmentsHistory = data;
        for (let i = 0; i < 1; i++) {
          this.lastConsultationDate = this.appointmentsHistory[i].date;
          const Vistdetails = this.healthRecord.getVisitDetails(this.appointmentsHistory[i].id);
          Vistdetails.subscribe((data) => {
            this.keyNotes = data.keyNotes;
            this.lastPhysicianEmail = data.physicianEmail;
            this.selectedBlood = data.bloodGroup;
          });
        }
      }
      if (this.appointmentsHistory.length == 0) {
        this.myForm.value.bloodgroup.required;
        this.selectedBlood = this.myForm.value.bloodgroup;
      }
    });
  }

  public get data() {
    return this.myForm.controls;
  }
  patientDetails: PatientInfoDetails = new PatientInfoDetails();
  patientDataa: PatientInfoDetails = new PatientInfoDetails();
  onSubmitt() {
    this.submitted = true;
    let data = '';
    for (let i = 0; i < this.myForm.value.allergies.length; i++) {
      data = data + this.myForm.value.allergies[i] + ", ";
    }
    if (this.appointmentsHistory.length == 0) {
      this.selectedBlood = this.myForm.value.bloodgroup;
    }
    console.log("Allegry Data");
    console.log(this.bloodgroup);
    console.log(this.myForm.value);
    console.log(this.selectedBlood);
    console.log(data);
    if (this.myForm.valid) {
      this.patientDetails.allergies = data;
      this.patientDetails.bloodGroup = this.selectedBlood;
      this.patientDetails.nurseEmail = this.nurseEmail;
      this.patientDetails.height = this.myForm.value.heightt;
      this.patientDetails.weight = this.myForm.value.weightt;
      this.patientDetails.bpSystolic = this.myForm.value.bpSystolic;
      this.patientDetails.bpDiastolic = this.myForm.value.bpDiastolic;
      this.patientDetails.bodyTemparature = this.myForm.value.bodyTemperature;
      this.patientDetails.respirationRate = this.myForm.value.respirationRate;
      this.patientDetails.physicianEmail = this.phys;
      this.patientDetails.appointmentId = this.appointmentAppointmentsId;
      this.patientDetails.keyNotes = this.myForm.value.somethingData;
      this.selectedToppings = this.myForm.value

      for (let j = 0; j < this.selectedToppings.length; j++) {
        for (let i = 0; i < this.allergyData.length; i++) {
          if (this.selectedToppings[j] == this.allergyData[i].allergyName) {
            this.allergyIds[j] = this.allergyData[i].allergyId;
          }
        }
      }
      this.dialog.open(SubmitDialogComponent, {
        width: '500px',
        data: {
          submitPatienttDetails: this.patientDetails,
          patientInfoId: this.patientData,
          appointmentId: this.appointmentAppointmentsId
        },
      });
    }
  }
}
