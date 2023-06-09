import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppointmentDto } from '../model_classes/appointment';
import { Patient } from '../model_classes/Patient';
import { PatientInfoDetails } from '../model_classes/visit_details';
import { Allergy } from '../../app/model_classes/allergy'
@Injectable({
  providedIn: 'root',
})
export class AppointmentService {
  constructor(public http: HttpClient) { }

  //To get All the accepted appointments
  public getAppointments(date: any, index: any, size: any): Observable<AppointmentDto[]> {
    return this.http.get<AppointmentDto[]>(
      'http://localhost:9003/api/v1/appointment-service/indexed-appointments/acceptance/' + date + '/' + index + "/" + size
    );
  }
  //To get status of the appointment by the patient id
  public getAppointmentByStatusAndId(id: any, status: any, index: any, size: any): Observable<AppointmentDto[]> {
    return this.http.get<AppointmentDto[]>("http://localhost:9003/api/v1/appointment-service/appointment/" + id + "/" + status + "/" + index + "/" + size);
  }

  //to book the  appointment for patient
  public bookAppointment(appointment: AppointmentDto) {
    return this.http.post<AppointmentDto>("http://localhost:9003/api/v1/appointment-service/appointment", appointment);
  }

  //to update the status of the appointment by the id
  public updateStatusById(id: any, status: any): Observable<AppointmentDto> {
    return this.http.put<AppointmentDto>("http://localhost:9003/api/v1/appointment-service/appointment/" + id + "/" + status, "");
  }

  public getAppointmentBYEmailAndDateAndStatus(email: any, date: any, status: any): Observable<AppointmentDto[]> {
    return this.http.get<AppointmentDto[]>("http://localhost:9003/api/v1/appointment-service/appointments/" + email + "/" + date + "/" + status);
  }
  public findAppointmentById(id: any): Observable<AppointmentDto> {
    return this.http.get<AppointmentDto>("http://localhost:9003/api/v1/appointment-service/appointment/" + id);
  }
  public sendPatientInfo(patient: PatientInfoDetails, patientId: any) {
    return this.http.post<PatientInfoDetails>(
      'http://localhost:9007/api/v1/health-record/patient/' + patientId + '/visits',
      patient
    );
  }
  public getPreviousPatientAppointments(patientId: any): Observable<AppointmentDto[]> {
    return this.http.get<AppointmentDto[]>('http://localhost:9003/api/v1/appointment-service/appointment/' + patientId + '/completed');
  }

  public getAppointmentByPatientId(id: any): Observable<AppointmentDto> {
    return this.http.get<AppointmentDto>("http://localhost:9003/api/v1/appointment-service/patient-appointment/" + id);
  }

  public getAppointmentCount(email: any) {
    return this.http.get("http://localhost:9003/api/v1/appointment-service/appointment-count/" + email);
  }
  public getPendingAppointmentCount(email: any) {
    return this.http.get("http://localhost:9003/api/v1/appointment-service/pending-count/" + email);
  }
  public getAcceptedAppointmentCount(email: any) {
    return this.http.get("http://localhost:9003/api/v1/appointment-service/accepted-count/" + email);
  }

  public getCompletedAppointmentCountByPatientId(id: any) {
    return this.http.get("http://localhost:9003/api/v1/appointment-service/completed/appointment/patient/" + id);
  }

  public getPendingAppointmentCountByPatientId(id: any) {
    return this.http.get("http://localhost:9003/api/v1/appointment-service/pending/appointment/patient/" + id);
  }

  public getAllAcceptanceAppointmentCount(date: any) {
    return this.http.get("http://localhost:9003/api/v1/appointment-service/acceptance/appointment/count/" + date);
  }
}
