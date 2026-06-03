import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Checkin } from '../../services/checkin';

@Component({
  selector: 'app-start-checkin',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './startcheckin.html',
  styleUrls: ['./startcheckin.css'],
})
export class StartCheckinComponent {
  reservationId: number = 0;
  constructor(private service: Checkin, private router: Router) {}

  onClick() {
    this.service.getReservation(this.reservationId).subscribe(data => {
      console.log(data);
      this.service.reservationData = data;
      this.router.navigate(['/checkin']);
    });
  }
}