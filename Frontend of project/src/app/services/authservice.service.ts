import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isLoggedIn = false;
  constructor(private router: Router) { }
  login(value: string) {
    sessionStorage.setItem("KEY", value)
  }
  logout() {
    sessionStorage.removeItem("KEY");
    console.log("fghjklkkjhgfjklkjhgfxcvbn");
    this.router.navigateByUrl("/index");
  }
  isAuthenticated(): boolean {
    let value = sessionStorage.getItem("PATIENT_OBJ");
    if (value != null) {
      let newValue = JSON.parse(value);
      let key = sessionStorage.getItem("KEY")

      if (newValue.email == key)
        return true;
      else
        return false;
    }
    else
      return false;
  }
}
