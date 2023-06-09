import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { DeletePopupComponent } from '../delete-popup/delete-popup.component';
import { DialogPopComponent } from '../dialog-pop/dialog-pop.component';

export interface Availability {
  id: number;
  name1: string;
  email: string
}


const PHY_DATES: Availability[] = [
  { id: 1, name1: 'Aman Kukreti', email: 'Amankukreti123@gmail.com' },
  { id: 2, name1: 'AKshay', email: 'Akshay123@gmail.com' },
  { id: 3, name1: 'Deepak', email: 'Deepak123@gmail.com' },
  { id: 4, name1: 'Sriram', email: 'Sriram123@gmail.com' },
  { id: 5, name1: 'Shaikh', email: 'Shaikh123@gmail.com' },


];
@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {
  displayedColumns: string[] = ['id', 'name', 'email'];
  dataSource = new MatTableDataSource<Availability>(PHY_DATES);
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private matDialog: MatDialog) { }

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
