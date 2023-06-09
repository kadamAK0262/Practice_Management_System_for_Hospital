import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AgePipe } from 'src/app/Pipes/age.pipe';
import { AuthModule } from '@auth0/auth0-angular';

@NgModule({
  declarations: [
    AgePipe
  ],
  imports: [
    CommonModule,
  ],
  exports: [AgePipe]
})
export class PipeModule { }
