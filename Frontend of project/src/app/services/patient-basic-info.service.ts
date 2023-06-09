import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Patient } from '../model_classes/Patient';

@Injectable({
  providedIn: 'root'
})
export class PatientBasicInfoService {

  patientsUrl: string = "";
  constructor(private http: HttpClient) {
    this.patientsUrl = 'http://localhost:9006/api/v1/patient-info/patient';
  }

  public getPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(this.patientsUrl);
  }

  public getpatientdetails(id: any): Observable<Patient> {
    return this.http.get<Patient>("http://localhost:9006/api/v1/patient-info/patient/" + id)
  }

  public updatePatientById(id: any, patientData: Patient): Observable<Patient> {
    return this.http.put<Patient>("http://localhost:9006/api/v1/patient-info/patient/" + id, patientData);
  }
  public getCount() {
    return this.http.get("http://localhost:9006/api/v1/patient-info/patient/count");
  }
}
