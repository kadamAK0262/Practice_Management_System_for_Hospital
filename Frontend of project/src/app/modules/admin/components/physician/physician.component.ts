import { MatDialog } from '@angular/material/dialog';
import { Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { DialogPopComponent } from '../dialog-pop/dialog-pop.component';
import { DeletePopupComponent } from '../delete-popup/delete-popup.component';
import { Physician } from 'src/app/model_classes/physician';
import { PhysicianAvailabilityService } from 'src/app/services/physician-availability.service';

@Component({
  selector: 'app-physician',
  templateUrl: './physician.component.html',
  styleUrls: ['./physician.component.css']
})


export class PhysicianComponent {
  physicianAvailabilityArr: Physician[] = [];
  displayedColumns: string[] = ['email', 'name', 'speciality', 'from', 'till', 'actions', 'delete'];
  dataSource: any;
  currentDate: any;
  matDialog: any;
  physicianAvail: any;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(private matDialogCon: MatDialog, private physicianAvailability: PhysicianAvailabilityService) {
    this.matDialog = matDialogCon;
    this.physicianAvail = physicianAvailability;
  }
  ngOnInit() {

    this.getAllPhysicianAvailability();
  }
  getAllPhysicianAvailability() {
    this.physicianAvail.findAllAvailability().subscribe((data: Physician[]) => {
      console.log(data);
      this.physicianAvailabilityArr = data;
      this.dataSource = new MatTableDataSource<Physician>(this.physicianAvailabilityArr);
      this.dataSource.paginator = this.paginator;
    });
  }
  openDialog(value: any) {
    this.matDialog.open(DialogPopComponent, {
      width: '400px',
      data: {
        availability: value,
      }
    })

  }
  deleteDialog(email: string) {
    this.matDialog.open(DeletePopupComponent, {
      width: '400px',
      data: {
        physicianEmail: email,
        object: this
      }
    })
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  toggle(value: any) {
    value.available = !value.available;
    console.log(value);
    console.log("inside toggle " + value);

    this.physicianAvail.update(value).subscribe();
    this.getAllPhysicianAvailability();
  }
}