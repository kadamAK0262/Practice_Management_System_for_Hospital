import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { IndexComponent } from "./components/index/index.component";
import { LoginComponent } from "./components/login/login.component";
import { PatientRegiComponent } from "./components/patient-regi/patient-regi.component";
import { ForgotpasswordComponent } from "./components/forgotpssword/forgotpssword.component";
import { PatientAuthGuard } from "./services/authguard.guard";
import { AuthGuard } from "@auth0/auth0-angular";

const routes: Routes = [
    { path: "", redirectTo: "/index", pathMatch: "full" },
    { path: "index", component: IndexComponent },
    { path: "login", component: LoginComponent },
    { path: "register", component: PatientRegiComponent },
    { path: "forgot-password", component: ForgotpasswordComponent },
    { path: "physician", loadChildren: () => import("./physician_module/physician/physician.module").then((m) => m.PhysicianModule), canActivate: [AuthGuard] },
    { path: "admin", loadChildren: () => import("./modules/admin/admin.module").then((n) => n.AdminModule) },
    { path: "patient", loadChildren: () => import("./patient_module/patient/patient.module").then((x) => x.PatientModule), canActivate: [PatientAuthGuard] },
    { path: "nurse", loadChildren: () => import("./nurse_module/nurse/nurse.module").then((y) => y.NurseModule) }
]
@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }