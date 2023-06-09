import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookappointmentComponent } from './components/bookappointment/bookappointment.component';
import { HealthRecordsComponent } from './components/health-records/health-records.component';
import { PatienthomeComponent } from './components/patienthome/patienthome.component';
import { ProfileComponent } from './components/profile/profile.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { TestdetailsComponent } from './components/testdetails/testdetails.component';
import { PdfviewComponent } from './components/pdfview/pdfview.component';

const routes: Routes = [
  {
    path: "", component: SidebarComponent, children: [
      {
        path: "home", component: PatienthomeComponent
      },
      {
        path: "", redirectTo: "/patient/home", pathMatch: "full"
      },
      {
        path: "bookappointment", component: BookappointmentComponent
      },
      {
        path: "health-records", component: HealthRecordsComponent
      },
      {
        path: "testdetails", component: TestdetailsComponent
      },

      {
        path: "profile", component: ProfileComponent
      },
      {
        path: "pdfView", component: PdfviewComponent
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PatientRoutingModule { }
