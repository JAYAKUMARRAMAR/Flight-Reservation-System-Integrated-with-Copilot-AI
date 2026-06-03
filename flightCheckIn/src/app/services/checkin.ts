import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Checkin {
  private apiUrl = 'http://localhost:8080/flightreservation/reservations';
  reservationData: any;

  constructor(private http: HttpClient) {}

  getReservation(id: string | number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  updateReservation(reservation: any): Observable<any> {
    const url = `${this.apiUrl}`;
    return this.http.post<any>(url, reservation);
  }
}