import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PhysicianRoutingModule } from './physician-routing.module';
import { MatDialogModule } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { AuthModule } from '@auth0/auth0-angular';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CardComponent } from './components/card/card.component';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatNativeDateModule, MatRippleModule } from '@angular/material/core';
import { MatSortModule } from '@angular/material/sort';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatBadgeModule } from '@angular/material/badge';
import { HttpClientModule } from '@angular/common/http';
import { MatCardModule } from '@angular/material/card';
import { RouterModule } from '@angular/router';
import { PendingListComponent } from './components/pending-list/pending-list.component';
import { ViewdetailsComponent } from './components/viewdetails/viewdetails.component';
import { AcceptDialogComponent } from './components/dialog-pop/dialog-pop.component';
import { PipeModule } from 'src/app/services/pipe/pipe.module';
import { MatSnackBarModule } from '@angular/material/snack-bar';

@NgModule({
  declarations: [
    NavBarComponent,
    DashboardComponent,
    CardComponent,
    PendingListComponent,
    ViewdetailsComponent,
    AcceptDialogComponent
  ],
  imports: [
    PipeModule,
    MatDialogModule,
    MatMenuModule,
    MatSlideToggleModule,
    MatDatepickerModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatExpansionModule,
    MatDividerModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule,
    MatTableModule,
    MatTabsModule,
    ReactiveFormsModule,
    CommonModule,
    PhysicianRoutingModule,
    MatListModule,
    MatInputModule,
    MatDatepickerModule,
    MatSlideToggleModule,
    MatDividerModule,
    MatSidenavModule,
    MatTooltipModule,
    MatCardModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    RouterModule,
    HttpClientModule,
    FormsModule,
    MatTabsModule,
    MatSnackBarModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatMenuModule,
    MatBadgeModule,
    CommonModule,
    MatGridListModule,
    MatRippleModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatNativeDateModule,
    MatToolbarModule,
    MatExpansionModule,
    FlexLayoutModule,
    MatSelectModule,
  ]
})
export class PhysicianModule { }
