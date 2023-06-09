import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { AppointmentDto } from 'src/app/model_classes/appointment';
import { PrescriptionDetails } from 'src/app/model_classes/prescription';
import { TestDetails } from 'src/app/model_classes/test';
import { PatientInfoDetails } from 'src/app/model_classes/visit_details';
import { AppointmentService } from 'src/app/services/appointment.service';
import { PatientHealthRecordService } from 'src/app/services/patient-health-record.service';



@Component({
  selector: 'app-previoushistory',
  templateUrl: './previoushistory.component.html',
  styleUrls: ['./previoushistory.component.css']
})
export class PrevioushistoryComponent implements OnInit {
  patientVisitDetails: PatientInfoDetails[] = [];
  physicianData: AppointmentDto[] = []
  testDetails: TestDetails[][] = [];
  prescriptionDetails: PrescriptionDetails[][] = [];

  displayedColumnsPrescription: string[] = ['progress', 'name', 'fruit'];
  displayedColumnsTest: string[] = ['progress', 'name', 'fruit'];
  dataSourceTest: any[] = [];
  dataSourcePrescription: any[] = [];

  @ViewChild(MatPaginator) paginator: MatPaginator[] = [];
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private service: PatientHealthRecordService, private appointmentList: AppointmentService) {
  }

  pId: any;
  ngOnInit(): void {


    this.pId = sessionStorage.getItem('arraydata');

    this.appointmentList.getAppointmentByStatusAndId(this.pId, 'completed', 0, 30).subscribe((data) => {
      this.physicianData = data;
      for (let i = 0; i < this.physicianData.length; i++) {
        this.getVisitHistory(this.physicianData[i].id, i);
      }

    });

  }
  getVisitHistory(appointmentId: any, index: number) {
    this.service.getVisitDetails
      (appointmentId).subscribe((visitDetails) => {
        this.patientVisitDetails[index] = visitDetails;
        this.service
          .getPrescription(this.patientVisitDetails[index].visitId)
          .subscribe((prescriptionData) => {
            this.prescriptionDetails[index] = prescriptionData;
            this.dataSourcePrescription[index] =
              new MatTableDataSource<PrescriptionDetails>(
                this.prescriptionDetails[index]
              );
            console.log("Prescription Details");
          });
        this.service
          .getTests(this.patientVisitDetails[index].visitId)
          .subscribe((testData) => {
            this.testDetails[index] = testData;
            this.dataSourceTest[index] = new MatTableDataSource<TestDetails>(
              this.testDetails[index]
            );
            console.log("Test Details");
            console.log(this.testDetails[index]);
          });
      });

  }
}


