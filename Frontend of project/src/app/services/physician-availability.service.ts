import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Physician } from '../model_classes/physician';

@Injectable({
  providedIn: 'root'
})
export class PhysicianAvailabilityService {
  physicianAvailability: string;
  putUrl: string;
  deleteUrl: string
  availablePhysician: string;
  updateStatus: string;
  count: string;
  findByEmail: string;
  constructor(private http: HttpClient) {
    this.physicianAvailability = 'http://localhost:9008/api/v1/physician-availability/physician';
    this.putUrl = 'http://localhost:9008/api/v1/physician-availability/update-availability';
    this.deleteUrl = 'http://localhost:9008/api/v1/physician-availability/physician-available/';
    this.availablePhysician = 'http://localhost:9008/api/v1/physician-availability/physician-available/'
    this.updateStatus = "http://localhost:9008/api/v1/physician-availability/update-status";
    this.count = "http://localhost:9008/api/v1/physician-availability/count";
    this.findByEmail = "http://localhost:9008/api/v1/physician-availability/physician-details"
  }

  public findPhysician(page: number, size: number): Observable<Physician[]> {
    return this.http.get<Physician[]>(this.availablePhysician + page + "/" + size + "/true");
  }
  public findPhysicianByEmailId(email: string): Observable<Physician> {
    return this.http.get<Physician>(this.findByEmail + "/" + email);
  }
  //It is used to fetch all the physicians by the admin only
  public findAllAvailability(): Observable<Physician[]> {
    return this.http.get<Physician[]>(this.physicianAvailability);
  }
  //It is used to update the scheduling of the physician by the admin only
  public updateStartAndEndDate(available: Physician): Observable<Physician> {
    return this.http.put<Physician>(this.updateStatus, available)
  }

  //It is used to delete the physician by the admin only
  public deletePhysicianAvailabilityById(email: any) {
    return this.http.delete(this.deleteUrl + email);
  }

  //It is used to update today's availability
  public update(availability: Physician) {
    return this.http.put(this.updateStatus, availability);
  }

  //It is used to get the count of the physician
  public getCount() {
    return this.http.get(this.count);
  }
}
