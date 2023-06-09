import { Component, ViewChild, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { TestDetails } from 'src/app/model_classes/test';
import { PatientHealthRecordService } from 'src/app/services/patient-health-record.service';



@Component({
  selector: 'app-testdetails',
  templateUrl: './testdetails.component.html',
  styleUrls: ['./testdetails.component.css']
})
export class TestdetailsComponent {
  displayedColumns: string[] = ['demo-testname', 'demo-result', 'demo-status'];
  dataSource: any;
  // idString: string | null = "";
  id: any;
  // visit: PatientInfoDetails;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(@Inject(MAT_DIALOG_DATA) private data: any, private testService: PatientHealthRecordService) {
    this.id = this.data.id;
  }

  ngOnInit() {
    this.testService.getTests(this.id).subscribe(data => {
      console.log(data);
      this.dataSource = new MatTableDataSource<TestDetails>(data);
      this.dataSource.paginator = this.paginator;
    })
  }
}
