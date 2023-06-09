import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { DatePipe } from '@angular/common'
import { MatTabChangeEvent } from '@angular/material/tabs';
import { AppointmentDto } from 'src/app/model_classes/appointment';
import { Patient } from 'src/app/model_classes/Patient';
import { AppointmentService } from 'src/app/services/appointment.service';
import { PatientBasicInfoService } from 'src/app/services/patient-basic-info.service';
import { AgePipe } from 'src/app/Pipes/age.pipe';
import { MatTableDataSource } from '@angular/material/table';
import { PhysicianAvailabilityService } from 'src/app/services/physician-availability.service';
import { Physician } from 'src/app/model_classes/physician';
import { Allergy } from 'src/app/model_classes/allergy';
import { AllergyService } from 'src/app/services/allergy.service';


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
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CardComponent implements OnInit {
  app: AppointmentDto[] = []
  patientData: Patient[] = []
  dataSource: any;
  selectDate: any;
  patientdetails: any;
  today: any;
  appointmentArr: Appointment[] = [];
  selectedDate: any;
  status: String = "accepted";
  email: any;
  maxDate: any;
  minDate: any;
  currentDate: DatePipe = new DatePipe('en-us');
  now: DatePipe = new DatePipe('en-us');
  tmrw: DatePipe = new DatePipe('en-us');
  tran: any;
  physicianDetails!: Physician;
  datePicker: any;
  constructor(private eventService: AllergyService, private service: AppointmentService, private patient: PatientBasicInfoService, private physician: PhysicianAvailabilityService) {
    this.email = sessionStorage.getItem("PHYSICIAN_EMAIL");
  }


  public onDate(event: any): void {
    this.datePicker = event;
  }

  ngOnInit(): void {

    var date = new Date();
    var transformdate = this.currentDate.transform(date, 'dd-MMM-yyyy')
    this.selectedDate = transformdate;
    this.physician.findPhysicianByEmailId(this.email).subscribe(data => {
      this.physicianDetails = data;
      this.minDate = new Date(this.physicianDetails.startDate);
      this.maxDate = new Date(this.physicianDetails.endDate);
    })
    if (this.eventService.getAppointmentEvent() != null) {
      console.log("ngOnit Again and angain")
      this.appointmentArr = [];
      this.eventService.getAppointmentEvent().subscribe(() => {
        this.getAppointment(this.email, transformdate, this.status)
      });
    }
    this.getAppointment(this.email, transformdate, this.status);

  }


  SelectBydate(selectDate: string) {
    const selectByDate = this.currentDate.transform(selectDate, 'dd-MMM-yyyy');
    this.selectedDate = selectByDate;
    this.app = [];
    this.appointmentArr = [];
    this.getAppointment(this.email, selectByDate, this.status);
  }


  getCurrentDate(event: MatTabChangeEvent) {
    var date = new Date();
    var transformdate = this.currentDate.transform(date, 'dd-MMM-yyyy')
    this.selectedDate = transformdate;
    const tab = event.tab.textLabel;
    if (tab == 'Today') {
      this.app = [];
      this.appointmentArr = [];
      this.getAppointment(this.email, transformdate, this.status);
    }
    else if (tab == 'Tommorrow') {
      this.app = [];
      this.appointmentArr = [];
      var date = new Date();
      const transform = this.currentDate.transform(date, 'dd-MMM-yyyy');
      const tmrw = new Date(date.setDate(date.getDate() + 1));
      const tmm = this.tmrw.transform(tmrw, 'dd-MMM-yyyy');
      this.selectedDate = tmm;
      this.getAppointment(this.email, tmm, this.status);
    }



  }
  setid: any

  getAppointment(email: any, date: any, status: any) {
    this.service.getAppointmentBYEmailAndDateAndStatus(email, date, status).subscribe((data: AppointmentDto[]) => {
      console.log("This is app data");
      console.log(this.app)
      if (data != null) {
        this.app = data;
        for (let i = 0; i < this.app.length; i++) {
          console.log(this.app[i].patientId + 'inside data');
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
  setpaientId(id: any) {
    const pId = sessionStorage.setItem('Pid', id)
  }
  setAppointmentId(Aid: any) {
    const Appointmentid = sessionStorage.setItem("AppointmentId", Aid)
  }


  applyFilter(event: Event) {

    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

  }

}
