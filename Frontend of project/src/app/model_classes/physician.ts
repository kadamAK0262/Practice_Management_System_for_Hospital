import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class Physician {
    email: string = "";
    physicianName: string = "";
    startDate: string = "";
    endDate: string = "";
    isAvailable: boolean = true;
    speciality: string = "";
}
