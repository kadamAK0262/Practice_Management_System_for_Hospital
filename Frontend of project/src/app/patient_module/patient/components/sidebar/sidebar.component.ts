import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatAccordion } from '@angular/material/expansion';
import { Patient } from 'src/app/model_classes/Patient';
import { AuthService } from 'src/app/services/authservice.service';
import { LogoutDialogComponent } from '../dialog-pop/dialog-pop.component';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  @ViewChild(MatAccordion)
  accordian!: MatAccordion;
  patientId: any
  patientDetails!: Patient;
  constructor(public auth: AuthService, private matDialog: MatDialog) {

  }
  ngOnInit(): void {
    this.patientId = sessionStorage.getItem("PATIENT_ID");
    const obj = sessionStorage.getItem("PATIENT_OBJ")
    if (obj != null) {
      this.patientDetails = JSON.parse(obj);
    }
  }

  logout() {
    this.matDialog.open(LogoutDialogComponent, {
      width: '400px'
    })
  }
}
