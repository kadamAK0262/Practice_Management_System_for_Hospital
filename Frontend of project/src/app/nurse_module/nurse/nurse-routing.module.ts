import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddpatientinfoComponent } from './components/addpatientinfo/addpatientinfo.component';
import { AppointmentsComponent } from './components/appointments/appointments.component';
import { IndexpageComponent } from './components/indexpage/indexpage.component';
import { NavbarComponent } from './components/navbar/navbar.component';

const routes: Routes = [
  {
    path: "", component: NavbarComponent,
    children: [
      { path: "dashboard", component: IndexpageComponent },
      { path: "", redirectTo: "/nurse/dashboard", pathMatch: "full" },
      { path: "appointments", component: AppointmentsComponent },
      { path: "addpatientinfo", component: AddpatientinfoComponent }]
  }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NurseRoutingModule { }
