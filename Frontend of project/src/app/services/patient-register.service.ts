import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Patient } from '../model_classes/Patient';

@Injectable({
  providedIn: 'root'
})
export class PatientRegisterService {

  private patientRegisterUrl: string;

  private loginUrl: string
  constructor(private http: HttpClient) {

    this.patientRegisterUrl = "http://localhost:9004/api/v1/authentication-service/patient/register";
    this.loginUrl = "http://localhost:9004/api/v1/authentication-service/patient/login";
  }

  public registerPatient(patient: Patient): Observable<Patient> {
    console.log("register");
    console.log(patient);
    return this.http.post<Patient>(this.patientRegisterUrl, patient);
  }

  public loginPatient(email: any, password: any): Observable<Patient> {
    console.log(email)
    console.log(password)
    let queryParams = new HttpParams();
    queryParams = queryParams.append("email", email);
    queryParams = queryParams.append("password", password);
    return this.http.post<Patient>(this.loginUrl, queryParams);
  }

  public forgotpassword(email: any) {
    return this.http.post("http://localhost:9004/api/v1/authentication-service/sendemail", email)
  }

  public updatePatientPassword(email: any, password: any) {
    let queryParams = new HttpParams();
    queryParams = queryParams.append("email", email);
    queryParams = queryParams.append("password", password);
    return this.http.put("http://localhost:9004/api/v1/authentication-service/patient", queryParams);
  }
}
