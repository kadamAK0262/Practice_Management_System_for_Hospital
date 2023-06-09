import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '@auth0/auth0-angular';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  email!: any
  constructor(public auth: AuthService, public route: Router) {
  }
  ngOnInit(): void {
    this.auth.user$.subscribe(res => {
      console.log(res)
      if (res?.['role'][0] == 'Physician') {
        this.email = res.email;
        sessionStorage.setItem("PHYSICIAN_EMAIL", this.email);
        this.route.navigateByUrl("/physician");
      }
      else if (res?.['role'][0] == 'Admin') {
        this.email = res.email;
        sessionStorage.setItem("ADMIN_EMAIL", this.email);
        this.route.navigateByUrl("/admin");
      }
      else if (res?.['role'][0] == 'Nurse') {
        this.email = res.email;
        sessionStorage.setItem("NURSE_EMAIL", this.email);
        console.log(this.email);
        this.route.navigateByUrl("/nurse");
      }
    }
    );
  }
}
