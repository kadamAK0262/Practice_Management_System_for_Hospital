import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule } from '@angular/forms';
import { MatRadioModule } from '@angular/material/radio';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatBadgeModule } from '@angular/material/badge';
import { MatExpansionModule } from '@angular/material/expansion';
import { AuthModule } from '@auth0/auth0-angular';
import { MatTabsModule } from '@angular/material/tabs';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { HeaderComponent } from './components/header/header.component';
import { CardsComponent } from './components/cards/cards.component';
import { PageComponent } from './components/page/page.component';
import { MatDividerModule } from '@angular/material/divider';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { RegisterDialogComponent } from './components/dialog-pop/dialog-pop.component';
import { FooterComponent } from './components/footer/footer.component';
import { IndexComponent } from './components/index/index.component';
import { ContentComponent } from './components/content/content.component';
import { HomeComponent } from './components/home/home.component';
import { AppRoutingModule } from './app-routing-module';
import { LoginComponent } from './components/login/login.component';
import { PatientRegiComponent } from './components/patient-regi/patient-regi.component';
import { ForgotpasswordComponent } from './components/forgotpssword/forgotpssword.component';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
@NgModule({
  declarations: [
    IndexComponent,
    HeaderComponent,
    HomeComponent,
    ContentComponent,
    CardsComponent,
    FooterComponent,
    RegisterDialogComponent,
    PageComponent,
    AppComponent,
    LoginComponent,
    PatientRegiComponent,
    ForgotpasswordComponent
  ],
  imports: [
    MatIconModule,
    MatDialogModule,
    AppRoutingModule,
    MatSelectModule,
    MatSnackBarModule,
    MatPaginatorModule,
    MatTableModule,
    MatCardModule,
    BrowserModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatIconModule,
    ReactiveFormsModule,
    MatRadioModule,
    MatDatepickerModule,
    MatNativeDateModule,
    HttpClientModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatGridListModule,
    MatBadgeModule,
    MatDividerModule,
    MatExpansionModule,
    MatTabsModule,
    AuthModule.forRoot({
      domain: 'dev-ipfjfrntvhuq88xm.us.auth0.com',
      clientId: 'C6lfvArrdGXNA4OiqtPaHZVkpEHnCUrV',
      authorizationParams: {
        redirect_uri: window.location.origin,
      }
    }),
    BrowserAnimationsModule
  ],
  providers: [{ provide: LocationStrategy, useClass: HashLocationStrategy }],
  bootstrap: [AppComponent]
})
export class AppModule { }
