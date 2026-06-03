import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Checkin as CheckinService } from '../../services/checkin';

@Component({
  selector: 'app-checkin',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './checkin.html',
  styleUrls: ['./checkin.css'],
})
export class Checkin {
  reservationData: any;
  data: any;
  noOfbags: any;
  constructor(private service: CheckinService, private router: Router) {}
  ngOnInit() {
    this.data = this.service.reservationData;
  }

  checkIn() {
    let request = {
      id: this.data.id,
      checkedIn: true,
      numberOfBags: this.noOfbags,
    };
    this.service.updateReservation(request).subscribe((data: any) => {
      console.log(data);
      this.router.navigate(['/confirm']);
    });
  }
}
