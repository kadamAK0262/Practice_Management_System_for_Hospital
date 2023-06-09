import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Inject } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { PatientRegiComponent } from 'src/app/components/patient-regi/patient-regi.component';
import { AppointmentService } from 'src/app/services/appointment.service';
import { PatientRegisterService } from 'src/app/services/patient-register.service';
import { PhysicianAvailabilityService } from 'src/app/services/physician-availability.service';
import { Location } from '@angular/common';
import { AuthService } from 'src/app/services/authservice.service';
@Component({
  selector: 'app-dialog-pop',
  templateUrl: './dialog-pop.component.html',
  styleUrls: ['./dialog-pop.component.css']
})

export class LogoutDialogComponent {

  constructor(private router: Router, @Inject(MAT_DIALOG_DATA) private data: any, private auth: AuthService) {
  }

  logout() {
    this.auth.logout();
  }
}
