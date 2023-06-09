import { Component, ViewChild, ElementRef, Output, EventEmitter, NgModule } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import html2canvas from 'html2canvas';
import jspdf from 'jspdf';
// import * as jsPDF from 'jspdf';
import { AppointmentDto } from 'src/app/model_classes/appointment';
import { PrescriptionDetails } from 'src/app/model_classes/prescription';
import { TestDetails } from 'src/app/model_classes/test';
import { PatientInfoDetails } from 'src/app/model_classes/visit_details';
import { AppointmentService } from 'src/app/services/appointment.service';
import { PatientHealthRecordService } from 'src/app/services/patient-health-record.service';

var $: any;
@Component({
  selector: 'app-pdfview',
  templateUrl: './pdfview.component.html',
  styleUrls: ['./pdfview.component.css']
})
export class PdfviewComponent {

  visits: PatientInfoDetails[] = [];
  patientId: any;
  checked: any;
  appointmentId: number = 3;
  nodata = true;
  pendingAppointment: AppointmentDto[] = [];
  acceptedAppointment: AppointmentDto[] = [];
  visit: PatientInfoDetails;
  id2String: string | null = "";
  patientDetails: any;

  pdfiddetails: any
  pdfContent: any;

  constructor(public dialog: MatDialog, private savedVisit: PatientHealthRecordService, private savedAppointment: AppointmentService) {
    this.visit = new PatientInfoDetails()
    this.patientId = sessionStorage.getItem("PATIENT_ID");
    const obj = sessionStorage.getItem("PATIENT_OBJ")
    this.pdfiddetails = sessionStorage.getItem("appointmentId")
    // const obj = sessionStorage.getItem("PATIENT_OBJ")
    if (obj != null) {
      this.patientDetails = JSON.parse(obj);
      console.log(this.patientDetails)

    }
  }
  appointmentdetails!: AppointmentDto;
  docvisitdetails: any
  prescription: PrescriptionDetails[] = [];
  Test: TestDetails[] = []
  ngOnInit() {
    console.log(this.pdfiddetails + "vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");

    this.savedAppointment.findAppointmentById(this.pdfiddetails).subscribe
      (result => {
        this.appointmentdetails = result;
        console.log("tttttttttttttttttttttttttttttttttttt")
        console.log(this.appointmentdetails);

      })
    this.savedVisit.getVisitDetails(this.pdfiddetails).subscribe(data => {
      this.docvisitdetails = data;
      this.savedVisit.getTests(this.docvisitdetails.visitId).subscribe(Tdata => {
        this.Test = Tdata
      })
      this.savedVisit.getPrescription(this.docvisitdetails.visitId).subscribe(pdata => {
        this.prescription = pdata
      })
    })

    //     const doc = new jsPDF();
    //     const elementToPrint = document.getElementById('container');;
    // if(elementToPrint){
    //     html2canvas(elementToPrint).then(canvas => {
    //       const imgData = canvas.toDataURL('src/assets/headerlogo.png');
    //       const pdfWidth = doc.internal.pageSize.getWidth();
    //       const pdfHeight = (canvas.height * pdfWidth) / canvas.width;
    //       doc.addImage(imgData, 'PNG', 0, 0, pdfWidth, pdfHeight);
    //       doc.save('${fileName}.pdf');
    //     });
    //   }
    // this.myComponent.generatePDF();
  }



  // async generatePDF(fileName: string)  {
  //   console.log("abcdef");

  //   const element = document.getElementById('container');
  //   if(element){
  //   const canvas = await html2canvas(element, { scale: 2, useCORS: true });
  //   const pdf = new jsPDF('p', 'cm', 'a4');
  //   const imgData = canvas.toDataURL('image/png', 1.0);
  //   const imgProps = pdf.getImageProperties(imgData);
  //   const pdfWidth = pdf.internal.pageSize.getWidth();
  //   const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;
  //   pdf.addImage(imgData, 'PNG', 0, 0, pdfWidth, 24);
  //   pdf.save('${fileName}.pdf');
  //   }
  // }

  generatePDF(fileName: string) {
    var data = document.getElementById('container');
    if (data) {
      html2canvas(data).then(canvas => {
        // Few necessary setting options
        var imgWidth = 208;
        var pageHeight = 295;
        var imgHeight = canvas.height * imgWidth / canvas.width;
        var heightLeft = imgHeight;

        const contentDataURL = canvas.toDataURL('image/png')
        let pdf = new jspdf('p', 'mm', 'a4'); // A4 size page of PDF
        var position = 0;
        pdf.addImage(contentDataURL, 'PNG', 0, position, imgWidth, 200)
        pdf.save('new-file.pdf'); // Generated PDF
      });
    }
  }
}
