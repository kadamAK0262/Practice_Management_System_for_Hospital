import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminModule } from './modules/admin/admin.module';
import { PhysicianModule } from './physician_module/physician/physician.module';

@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
    AdminModule,
    PhysicianModule
  ]
})
export class ModuleModule { }
