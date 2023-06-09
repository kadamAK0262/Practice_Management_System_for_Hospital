import { Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Patient } from 'src/app/model_classes/Patient';
import { PatientBasicInfoService } from 'src/app/services/patient-basic-info.service';

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent {
  patientArr: Patient[] = [];
  dataSourcePatient: any;
  displayedColumns = ['ID', 'title', 'name', 'email', 'gender'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(private patientService: PatientBasicInfoService) { }
  ngOnInit() {
    this.patientService.getPatients().subscribe(data => {
      this.patientArr = data;
      console.log(this.patientArr);
      this.dataSourcePatient = new MatTableDataSource<Patient>(this.patientArr);
      this.dataSourcePatient.paginator = this.paginator;
      console.log(this.dataSourcePatient.paginator.pa);
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSourcePatient.filter = filterValue.trim().toLowerCase();
  }
}
