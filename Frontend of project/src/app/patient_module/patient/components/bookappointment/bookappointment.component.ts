import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DisplayDoctorsService } from 'src/app/patient_service/display-doctors.service';
import { Physician } from 'src/app/model_classes/physician';
import { DialogAppointmentComponent } from '../dialog-appointment/dialog-appointment.component';
import { MatTableDataSource } from '@angular/material/table';
import { PhysicianAvailabilityService } from 'src/app/services/physician-availability.service';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-bookappointment',
  templateUrl: './bookappointment.component.html',
  styleUrls: ['./bookappointment.component.css']
})
export class BookappointmentComponent {

  length: any;
  pageSize = 10;
  pageIndex = 0;
  pageEvent!: PageEvent;

  physician: Physician[] = [];
  dataEmail: string = "";
  dataStartDate: string = "";
  dataEndDate: string = "";
  dataSource: any;
  page = 0;
  constructor(private dialog: MatDialog, private physicianService: PhysicianAvailabilityService) {

  }
  ngOnInit() {
    this.physicianService.getCount().subscribe(count => {
      this.length = count;
    })
    this.getPhysician(this.pageIndex, this.pageSize);
  }
  openDialog(value1: string, value2: string, value3: string) {
    this.dialog.open(DialogAppointmentComponent, { data: { dataEmail: value1, dataStartDate: value2, dataEndDate: value3 } });
  }
  applyFilter(event: Event) {

    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

  }

  getPhysician(newPage: number, newSize: number) {

    this.physicianService.findPhysician(newPage, newSize).subscribe(data => {
      this.physician = data;
      this.dataSource = new MatTableDataSource(this.physician);
    });

  }

  handlePageEvent(e: PageEvent) {
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    console.log(this.pageIndex + "pagenIndexs");
    this.getPhysician(this.pageIndex, this.pageSize)
  }



}
