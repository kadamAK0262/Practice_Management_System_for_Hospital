import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Physician } from '../model_classes/physician';

@Injectable({
  providedIn: 'root'
})
export class DisplayDoctorsService {
  private registerUrl: string;

  constructor(private http: HttpClient) {
    this.registerUrl = 'http://localhost:9008/api/v1/physician-available?available=true';
  }

  public findPhysician(): Observable<Physician[]> {
    return this.http.get<Physician[]>(this.registerUrl);
  }
}
