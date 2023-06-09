import { Component, OnInit } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = "Deepak"
  constructor(public auth: AuthService) { }

  ngOnInit(): void {
    this.auth.user$.subscribe(res => {
      console.log(res);
    })
  }
}
