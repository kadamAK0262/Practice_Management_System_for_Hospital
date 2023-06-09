import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CardComponent } from './components/card/card.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { PendingListComponent } from './components/pending-list/pending-list.component';
import { ViewdetailsComponent } from './components/viewdetails/viewdetails.component';

const routes: Routes = [{
  path: "", component: NavBarComponent, children: [
    { path: "", redirectTo: "/physician/dashboard", pathMatch: "full" },
    { path: "dashboard", component: DashboardComponent },
    { path: 'appointments', component: CardComponent },
    { path: 'pending-appointments', component: PendingListComponent },
    { path: "viewDetails", component: ViewdetailsComponent }
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PhysicianRoutingModule { }
