import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AppointmentDto } from 'src/app/model_classes/appointment';
import { PatientInfoDetails } from 'src/app/model_classes/visit_details';
import { AppointmentService } from 'src/app/services/appointment.service';
import { PatientHealthRecordService } from 'src/app/services/patient-health-record.service';
import { PrescriptionComponent } from '../prescription/prescription.component';
import { TestdetailsComponent } from '../testdetails/testdetails.component';
import { Patient } from 'src/app/model_classes/Patient';
import { MatTableDataSource } from '@angular/material/table';
import { PageEvent } from '@angular/material/paginator';
import { PhysicianAvailabilityService } from 'src/app/services/physician-availability.service';


export interface HealthRecords {
  acceptance: string
  date: any;
  physician_email: any;
  nurse_email: any;
  ailment: any;
  height: any;
  weight: any;
  systolic: any;
  diSystolic: any
  respiration_rate: any;
}

@Component({
  selector: 'app-health-records',
  templateUrl: './health-records.component.html',
  styleUrls: ['./health-records.component.css']
})
export class HealthRecordsComponent {

  pendingLength: any;
  acceptedLength: any;
  pageSize = 10;
  pageIndex = 0;
  pageEvent!: PageEvent;

  pdfcomponent: any;
  visits: PatientInfoDetails[] = [];
  patientId: any;
  healthRecords: HealthRecords[] = []
  checked: any;
  appointmentId: number = 3;
  nodata = true;
  pendingDataSource: any;
  acceptedDataSource: any;
  pendingAppointment: AppointmentDto[] = [];
  acceptedAppointment: AppointmentDto[] = [];
  visit: PatientInfoDetails;
  patientDetails: any;
  constructor(public dialog: MatDialog, private savedVisit: PatientHealthRecordService, private savedAppointment: AppointmentService) {
    this.visit = new PatientInfoDetails()
    this.patientId = sessionStorage.getItem("PATIENT_ID");
    const obj = sessionStorage.getItem("PATIENT_OBJ")
    if (obj != null) {
      this.patientDetails = JSON.parse(obj);
      console.log(this.patientDetails)
    }
    this.checked = true;
  }

  openDialog(visitId: number) {
    this.dialog.open(TestdetailsComponent, {
      data: {
        id: visitId
      }
    });
  }

  openDialog1(visitId: number) {
    const dialogRef = this.dialog.open(PrescriptionComponent, {
      data: {
        id: visitId
      }
    });
  }

  ngOnInit() {
    this.savedAppointment.getCompletedAppointmentCountByPatientId(this.patientId).subscribe(count => {
      this.acceptedLength = count;
    })
    this.savedAppointment.getPendingAppointmentCountByPatientId(this.patientId).subscribe(count => {
      this.pendingLength = count;
    })
    this.getPendingAppointments(this.pageIndex, this.pageSize);
    this.getAcceptedAppointments(this.pageIndex, this.pageSize);
  }
  getAcceptedAppointments(page: any, size: any) {
    this.savedAppointment.getAppointmentByStatusAndId(this.patientId, 'completed', page, size).subscribe(result => {
      if (result == null)
        this.nodata = true;
      else {
        this.nodata = false;
        this.acceptedAppointment = result;
        for (let i = 0; i < result.length; i++) {
          this.savedVisit.getVisitDetails(this.acceptedAppointment[i].id).subscribe(data => {
            this.visits[i] = data;
          })
        }
      }
      this.acceptedDataSource = new MatTableDataSource<PatientInfoDetails>(this.visits)
    })
  }
  getPendingAppointments(page: any, size: any) {
    this.savedAppointment.getAppointmentByStatusAndId(this.patientId, "pending", page, size).subscribe(result => {

      if (result != null) {
        this.pendingAppointment = result;
      }
      this.pendingDataSource = new MatTableDataSource<AppointmentDto>(this.pendingAppointment);
    })
  }

  accepted() {
    this.checked = true;
  }
  pending() {
    this.checked = false;
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    if (this.checked)
      this.acceptedDataSource.filter = filterValue.trim().toLowerCase();
    else
      this.pendingDataSource.filter = filterValue.trim().toLowerCase();
  }

  handlePageEvent(e: PageEvent) {
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.getPendingAppointments(this.pageIndex, this.pageSize)
    this.getAcceptedAppointments(this.pageIndex, this.pageSize);
  }

  sendingAppointmentIdForPdfComponent(id: any) {
    sessionStorage.setItem("appointmentId", id);
    console.log("sssssssssssssssssssssssssssssssssssssssssssssssssss" + id);
  }
}
