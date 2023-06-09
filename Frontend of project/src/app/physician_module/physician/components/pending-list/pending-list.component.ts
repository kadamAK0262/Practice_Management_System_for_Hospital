import { Component, Input, OnInit, DoCheck, EventEmitter } from '@angular/core';
import { DatePipe, UpperCasePipe } from '@angular/common';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { Patient } from 'src/app/model_classes/Patient';
import { AppointmentDto } from 'src/app/model_classes/appointment';
import { AppointmentService } from 'src/app/services/appointment.service';
import { PatientBasicInfoService } from 'src/app/services/patient-basic-info.service';
import { PatientRegisterService } from 'src/app/services/patient-register.service';
import { MatDialog } from '@angular/material/dialog';
import { AcceptDialogComponent } from '../dialog-pop/dialog-pop.component';
import { MatTableDataSource } from '@angular/material/table';
import { AgePipe } from 'src/app/Pipes/age.pipe';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Physician } from 'src/app/model_classes/physician';
import { PhysicianAvailabilityService } from 'src/app/services/physician-availability.service';

export class Appointment {
  id: any
  patientId: any
  patientName: any
  patientEmail: any
  date: any;
  age: any;
  gender: any;
  contact: any;
  ailment: any;
}
@Component({
  selector: 'app-pending-list',
  templateUrl: './pending-list.component.html',
  styleUrls: ['./pending-list.component.css']
})
export class PendingListComponent implements OnInit {
  app: AppointmentDto[] = [];
  patientData: Patient[] = []
  dataSource: any;
  appointmentArr: Appointment[] = [];
  status: String = "pending"
  @Input() receivedParentMessage: string = '';

  physicianDetails!: Physician;
  patientdetails: any;
  appointmentUpdated: any
  email: any;

  constructor(private physician: PhysicianAvailabilityService, private matdialog: MatDialog, private service: AppointmentService, private patient: PatientBasicInfoService, private RegisterService: PatientRegisterService) {
    this.email = sessionStorage.getItem("PHYSICIAN_EMAIL");
    this.appointmentUpdated = new EventEmitter();

  }

  minDate: any;
  maxDate: any;
  selectDate: any
  selectByChooseDate: any

  currentDate: DatePipe = new DatePipe('en-us');
  now: DatePipe = new DatePipe('en-us');
  tmrw: DatePipe = new DatePipe('en-us');
  id: any = null;
  tran: any;
  selectDateAppointment: any
  appointment: any = {};
  ngOnInit(): void {
    var date = new Date();
    var current = this.currentDate.transform(date, 'dd-MMM-yyyy')
    this.physician.findPhysicianByEmailId(this.email).subscribe(data => {
      this.physicianDetails = data;
      this.minDate = new Date(this.physicianDetails.startDate);
      this.maxDate = new Date(this.physicianDetails.endDate);

    })
    if (this.selectDate == null) {
      this.selectDateAppointment = current;
      this.getAppointments(current);
      this.appointmentUpdated.subscribe(() => {
        this.app = [];
        this.appointmentArr = [];
        this.getAppointments(this.selectDateAppointment);
      });
    }
    else {
      this.appointmentUpdated.subscribe(() => {
        this.app = [];
        this.appointmentArr = [];
        this.getAppointments(this.selectDateAppointment);
      });
    }
  }

  getAppointments(date: any) {
    this.service.getAppointmentBYEmailAndDateAndStatus(this.email, date, this.status).subscribe((data: AppointmentDto[]) => {
      console.log("Inside get Appointments");
      console.log(this.app.length);
      if (data != null) {
        this.app = data;
        for (let i = 0; i < this.app.length; i++) {
          this.patient.getpatientdetails(this.app[i].patientId).subscribe((data1: Patient) => {
            this.patientData[i] = data1;
            this.appointmentArr[i] = new Appointment();
            this.appointmentArr[i].id = this.app[i].id;
            this.appointmentArr[i].patientId = this.patientData[i].patientId;
            this.appointmentArr[i].patientName = this.patientData[i].title + " " + this.patientData[i].firstName + " " + this.patientData[i].lastName;
            this.appointmentArr[i].patientEmail = this.patientData[i].email;
            this.appointmentArr[i].date = this.app[i].date;
            this.appointmentArr[i].age = new AgePipe().transform(this.patientData[i].dob);
            this.appointmentArr[i].gender = this.patientData[i].gender;
            this.appointmentArr[i].contact = this.patientData[i].contactNumber;
            this.appointmentArr[i].ailment = this.app[i].reason;
          });
        }
      }
      this.dataSource = new MatTableDataSource<Appointment>(this.appointmentArr);
    });
  }

  SelectBydate(SelectedDate: string) {
    this.selectDate = SelectedDate
    const SelectByDate = this.currentDate.transform(this.selectDate, 'dd-MMM-yyyy');
    this.selectByChooseDate = SelectByDate;
    this.selectDateAppointment = SelectByDate;
    this.app = [];
    this.appointmentArr = [];
    this.getAppointments(SelectByDate);
    this.appointmentUpdated.subscribe(() => {
      this.appointmentArr = [];
      this.app = [];
      this.getAppointments(this.selectDateAppointment);
    });
  }

  acceptAppointment(id: any, email: any) {
    this.matdialog.open(AcceptDialogComponent, {
      width: '400px',
      data: {
        newId: id,
        newEmail: email,
        status: "acceptance",
        physicianEmail: this.email,
        obj: this,
        selectedDate: this.selectByChooseDate,
        update: this.appointmentUpdated
      }
    })

  }

  rejectAppointment(id: any, email: any) {
    this.matdialog.open(AcceptDialogComponent, {
      width: '400px',
      data: {
        newId: id,
        newEmail: email,
        status: "rejected",
        physicianEmail: this.email,
        update: this.appointmentUpdated
      }
    })

  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }


}
