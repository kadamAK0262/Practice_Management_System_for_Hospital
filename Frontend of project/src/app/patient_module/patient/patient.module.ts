import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientRoutingModule } from './patient-routing.module';
import { BookappointmentComponent } from './components/bookappointment/bookappointment.component';
import { DialogAppointmentComponent } from './components/dialog-appointment/dialog-appointment.component';
import { HealthRecordsComponent } from './components/health-records/health-records.component';
import { PatienthomeComponent } from './components/patienthome/patienthome.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ResetpasswordComponent } from './components/resetpassword/resetpassword.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { TestdetailsComponent } from './components/testdetails/testdetails.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatCardModule } from '@angular/material/card';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatDividerModule } from '@angular/material/divider';
import { MatDialogModule } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { HttpClientModule } from '@angular/common/http';
import { MatSortModule } from '@angular/material/sort';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { PrescriptionComponent } from './components/prescription/prescription.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { PatientBasicInfoService } from 'src/app/services/patient-basic-info.service';
import { PatientRegisterService } from 'src/app/services/patient-register.service';
import { PhysicianAvailabilityService } from 'src/app/services/physician-availability.service';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { PipeModule } from 'src/app/services/pipe/pipe.module';
import { LogoutDialogComponent } from './components/dialog-pop/dialog-pop.component';
import { PdfviewComponent } from './components/pdfview/pdfview.component';
import { UpdateProfileComponent } from './components/update-profile/update-profile.component';

@NgModule({
  declarations: [
    PdfviewComponent,
    LogoutDialogComponent,
    BookappointmentComponent,
    DialogAppointmentComponent,
    HealthRecordsComponent,
    PatienthomeComponent,
    ProfileComponent,
    ResetpasswordComponent,
    SidebarComponent,
    TestdetailsComponent,
    PrescriptionComponent,
    UpdateProfileComponent,
  ],
  imports: [
    FormsModule,
    PipeModule,
    FlexLayoutModule,
    CommonModule,
    PatientRoutingModule,
    MatTableModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSidenavModule,
    MatIconModule,
    MatToolbarModule,
    MatDialogModule,
    MatMenuModule,
    MatCardModule,
    MatExpansionModule,
    MatPaginatorModule,
    MatDividerModule,
    MatButtonToggleModule,
    MatDialogModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatRadioModule,
    MatSelectModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatSortModule,
    MatSnackBarModule,
    MatAutocompleteModule
  ],
  providers: [PatientBasicInfoService, PatientRegisterService, PhysicianAvailabilityService]
})
export class PatientModule {

}
