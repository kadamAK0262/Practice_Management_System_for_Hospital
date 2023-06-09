import { Component, OnInit } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { AppointmentService } from 'src/app/services/appointment.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  email: any;
  acceptedCount: any;
  pendingCount: any;
  appointmentCount: any;
  constructor(public auth: AuthService, private appointment: AppointmentService) {
    this.email = sessionStorage.getItem("PHYSICIAN_EMAIL");
  }
  ngOnInit(): void {
    this.appointment.getAcceptedAppointmentCount(this.email).subscribe(count => {
      this.acceptedCount = 0;
      if (count != 0) {
        console.log(count);
        setInterval(() => {
          if (this.acceptedCount < count)
            this.acceptedCount++;
        }, 100);
      }
    })
    this.appointment.getPendingAppointmentCount(this.email).subscribe(count => {
      this.pendingCount = 0;

      if (count != 0) {
        console.log(count);
        setInterval(() => {
          if (this.pendingCount < count)
            this.pendingCount++;
        }, 100);
      }
    })
    this.appointment.getAppointmentCount(this.email).subscribe(count => {
      this.appointmentCount = 0;
      if (count != 0) {
        console.log(count);
        setInterval(() => {
          if (this.appointmentCount < count)
            this.appointmentCount++;
        }, 100);
      }
    })
  }
}
