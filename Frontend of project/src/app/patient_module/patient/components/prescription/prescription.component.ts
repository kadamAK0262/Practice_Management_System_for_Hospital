import { Component, Inject, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { PrescriptionDetails } from 'src/app/model_classes/prescription';
import { PatientInfoDetails } from 'src/app/model_classes/visit_details';
import { PatientHealthRecordService } from 'src/app/services/patient-health-record.service';


export interface Prescription {
  prid: string;
  medicine: string;
  dosage: string;
  notes: string;
}

@Component({
  selector: 'app-prescription',
  templateUrl: './prescription.component.html',
  styleUrls: ['./prescription.component.css']
})
export class PrescriptionComponent {
  prescription: PrescriptionDetails[] = [];
  displayedColumns: string[] = ['prescriptionName', 'dosage', 'prescriptionNotes'];
  dataSource: any;
  // idString: string | null = "";
  id: any;
  // visit: PatientInfoDetails;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(@Inject(MAT_DIALOG_DATA) private data: any, private prescriptionService: PatientHealthRecordService, private visitService: PatientHealthRecordService) {
    this.id = this.data.id;
  }

  ngOnInit() {
    this.prescriptionService.getPrescription(this.id).subscribe(data => {
      console.log(data);
      this.dataSource = new MatTableDataSource<PrescriptionDetails>(data);
      this.dataSource.paginator = this.paginator;
    })
  }
}
