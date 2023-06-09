import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AppsubService {
  data: any;
  constructor() { }

  setMessage(message: any) {
    this.data = message;
  }


  getMessage() {
    return this.data;
  }
}
