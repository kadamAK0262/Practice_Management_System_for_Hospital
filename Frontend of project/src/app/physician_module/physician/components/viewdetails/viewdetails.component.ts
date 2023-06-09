import { Component, EventEmitter, OnInit, ViewChild } from '@angular/core';
import { AppointmentService } from 'src/app/services/appointment.service';
import { PatientBasicInfoService } from 'src/app/services/patient-basic-info.service';
import { PatientHealthRecordService } from 'src/app/services/patient-health-record.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { TestDetails } from 'src/app/model_classes/test';
import { PrescriptionDetails } from 'src/app/model_classes/prescription';
import { PatientInfoDetails } from 'src/app/model_classes/visit_details';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Allergy } from 'src/app/model_classes/allergy';
import { AllergyService } from 'src/app/services/allergy.service';
@Component({
  selector: 'app-viewdetails',
  templateUrl: './viewdetails.component.html',
  styleUrls: ['./viewdetails.component.css']
})
export class ViewdetailsComponent implements OnInit {
  showContent = false;
  prescription: any

  event: any;
  testDetails: TestDetails[][] = [];
  prescriptionDetails: PrescriptionDetails[][] = [];
  @ViewChild(MatPaginator) paginator: MatPaginator[] = [];
  displayedColumnsPrescription: string[] = ['progress', 'name', 'fruit'];
  displayedColumnsTest: string[] = ['progress', 'name', 'fruit'];
  dataSourceTest: any[] = [];
  dataSourcePrescription: any[] = [];


  @ViewChild(MatSort) sort!: MatSort;

  toggleContent() {
    this.showContent = !this.showContent;
  }
  panelOpenState = false;
  status: String = "completed";
  id: any
  // let resp1=this.service.getAppointments(this.email,transformdate,this.status);
  // resp1.subscribe((data: any)=>this.patientdetails=data);
  patientdetail: any
  patientVisitdetail!: PatientInfoDetails;
  Vistdetails: any
  prescriptions: any[] = [];
  PatientsTests: any[] = [];
  appointmentsHistory: any = []
  visitID: any
  healthRecordService!: PatientHealthRecordService;
  patientHistoryVisitdetail: any[] = []
  HistoryvisitId: any
  bloodgroup: any
  lastConsultationDate: any
  lastPhysicianEmail: any
  keyNotes: any;
  constructor(
    private route: Router,
    private eventService: AllergyService,
    private _snackBar: MatSnackBar,
    private patientdetails: PatientBasicInfoService,
    private healthRecord: PatientHealthRecordService,
    private appointment: AppointmentService
  ) {
    this.event = new EventEmitter();
  }
  ngOnInit(): void {
    const pId = sessionStorage.getItem('Pid')
    const Aid = sessionStorage.getItem('AppointmentId')
    const details = this.patientdetails.getpatientdetails(pId)
    details.subscribe((data) => this.patientdetail = data)
    const Vistdetails = this.healthRecord.getVisitDetails(Aid);
    Vistdetails.subscribe((data) => {
      this.patientVisitdetail = data
      this.visitID = this.patientVisitdetail.visitId;
      sessionStorage.setItem("VisitId", this.patientVisitdetail.visitId)
    })

    const appointmentHistory = this.appointment.getAppointmentByStatusAndId(pId, this.status, 0, 20)
    appointmentHistory.subscribe((data) => {
      if (data != null) {
        this.appointmentsHistory = data;
        for (let i = 0; i < this.appointmentsHistory.length; i++) {
          if (i == 0) {
            this.lastConsultationDate = this.appointmentsHistory[i].date;
          }
          const Vistdetails = this.healthRecord.getVisitDetails(this.appointmentsHistory[i].id);
          Vistdetails.subscribe((data) => {
            this.patientHistoryVisitdetail[i] = data
            if (i == 0) {
              this.keyNotes = data.keyNotes;
              this.lastPhysicianEmail = data.physicianEmail;
              this.bloodgroup = data.bloodGroup;
            }
            this.HistoryvisitId = this.patientHistoryVisitdetail[i].visitId;
            this.healthRecord.getPrescription(this.HistoryvisitId).subscribe((data) => {
              this.prescriptions[i] = data
              this.dataSourcePrescription[i] = new MatTableDataSource<PrescriptionDetails>(data);
              this.dataSourcePrescription[i].paginator = this.paginator[i];

            });
            this.healthRecord.getTests(this.HistoryvisitId).subscribe((data) => {
              this.PatientsTests[i] = data
              this.dataSourceTest[i] = new MatTableDataSource<TestDetails>(data);
              this.dataSourceTest[i].paginator = this.paginator[i];
            });
          });
        }
      }
      else {
        this.dataSourceTest[0] = new MatTableDataSource<TestDetails>(this.PatientsTests[0]);
        this.dataSourcePrescription[0] = new MatTableDataSource<PrescriptionDetails>(this.prescriptions[0]);
      }
    })
  }


  medicines: any[] = [];
  newMedicine: any = { prescriptionName: '', dosage: '', prescriptionNotes: '', visitId: '' };

  addMedicine() {
    if (this.newMedicine.prescriptionName != '' && this.newMedicine.dosage != '' && this.newMedicine.prescriptionNotes != '') {
      this.newMedicine.visitId = this.patientVisitdetail;
      this.medicines.push(this.newMedicine);
      this.newMedicine = { prescriptionName: '', dosage: '', prescriptionNotes: '' };
    }
    else {
      this.errorSnackBar('Medicine Details Not Found!');
    }
  }

  removeMedicine(medicine: any) {
    this.medicines = this.medicines.filter(m => m !== medicine);
  }



  Tests: any[] = [];
  newTest: any = { testName: '', testNotes: '', result: '', visitId: '' };

  addTest() {
    if (this.newTest.testName != '' && this.newTest.testNotes != '' && this.newTest.result != '') {
      this.newTest.visitId = this.patientVisitdetail;
      this.Tests.push(this.newTest);
      this.newTest = { testName: '', testNotes: '', result: '' };
    }
    else {
      this.errorSnackBar('Test Details Not Found!');
    }
  }

  removeTest(test: any) {
    this.Tests = this.Tests.filter(m => m !== test);
  }

  errorSnackBar(value: string) {
    this._snackBar.open(value, 'Close', {
      duration: 3000,
      horizontalPosition: 'left',
      verticalPosition: 'bottom',
      panelClass: ['red_snackbar']
    });
  }
  submitTestAndPrescription(id: any, testss: any, value: any) {
    let checkPrescription: boolean = false;
    let checkTest: boolean = false;
    testss.visitId = this.patientVisitdetail;
    this.newMedicine.visitId = this.patientVisitdetail;
    if (testss.testName != '' && testss.testNotes != '' && testss.result != '' &&
      value.prescriptionName != '' && value.prescriptionNotes != '' && value.dosage != '') {
      this.Tests.push(testss);
      this.medicines.push(value);
    }
    else {
      checkPrescription = true;
      checkTest = true;
    }

    for (let i = 0; i < this.medicines.length; i++) {
      if (this.medicines[i].prescriptionName == '' || this.medicines[i].dosage == '' || this.medicines[i].prescriptionNotes == '') {
        checkPrescription = true;
        break;
      }
    }
    for (let i = 0; i < this.Tests.length; i++) {
      if (this.Tests[i].testName == '' || this.Tests[i].testNotes == '' || this.Tests[i].result == '') {
        checkTest = true;
        break;
      }
    }
    if (checkPrescription || checkTest) {
      this.errorSnackBar('Input field is empty!');
    }
    else {
      this.appointment.updateStatusById(sessionStorage.getItem('AppointmentId'), "completed").subscribe(() => {
        this.healthRecord.postTest(this.Tests).subscribe();
        this.removeTest(testss);
        this.Tests = [];
        this.newTest = { testName: '', testNotes: '', result: '' };

        this.healthRecord.postPrescription(this.medicines).subscribe()
        this.removeMedicine(value);
        this.medicines = [];
        this.newMedicine = { prescriptionName: '', dosage: '', prescriptionNotes: '', visitId: '' };
        this.openSnackBar();
        this.eventService.setAppointmentEvent(this.event);
        this.event.emit();
        this.route.navigateByUrl('/physician/appointments')
      });
    }
  }
  openSnackBar() {
    this._snackBar.open('Prescription and Test Details Filled Successfully!', 'Close', {
      horizontalPosition: 'left',
      verticalPosition: 'bottom',
      duration: 2000,
      panelClass: ['snackbar']
    });
  }
}
