import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { PatientHealthRecordService } from 'src/app/services/patient-health-record.service';
import { DeletePopupComponent } from '../delete-popup/delete-popup.component';
import { DialogPopComponent } from '../dialog-pop/dialog-pop.component';


export interface Availability {
  id: number;
  name1: string;
  from: number;
  till: number;
}

@Component({
  selector: 'app-nurse',
  templateUrl: './nurse.component.html',
  styleUrls: ['./nurse.component.css']
})
export class NurseComponent {
  displayedColumns: string[] = ['id', 'name1', 'email'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  dataSource: any;
  constructor(private matDialog: MatDialog, private healthRecordService: PatientHealthRecordService) { }

  ngOnInit() {
    this.healthRecordService.getNurses().subscribe((data: any) => {
      this.dataSource = new MatTableDataSource<any>(data);
      this.dataSource.paginator = this.paginator;
    });
  }

  openDialog() {
    this.matDialog.open(DialogPopComponent, {
      width: '400px',
    })
  }

  deleteDialog() {
    this.matDialog.open(DeletePopupComponent, {
      width: '400px'
    })
  }



  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
