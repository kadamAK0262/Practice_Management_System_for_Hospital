import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { ProfileComponent } from '../profile/profile.component';

@Component({
  selector: 'app-resetpassword',
  templateUrl: './resetpassword.component.html',
  styleUrls: ['./resetpassword.component.css']
})
export class ResetpasswordComponent {
  hide = true;

  constructor(
    public dialogRef: MatDialogRef<ProfileComponent>) { }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
