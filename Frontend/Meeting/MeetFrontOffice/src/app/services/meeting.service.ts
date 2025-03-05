import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root' // Makes the service available application-wide
})
export class MeetingService {
  private apiUrl = 'http://localhost:8050/PI_Project/meetings'; // Base URL for the backend API

  constructor(private http: HttpClient) { }

  // Get all meetings
  getMeetings(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // Create a new meeting
  createMeeting(meeting: any): Observable<any> {
    return this.http.post(this.apiUrl, meeting);
  }

  // Update a meeting by ID
  updateMeetingById(id: number, meeting: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, meeting);
  }

  // Delete a meeting by ID
  deleteMeetingById(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
   // Export meetings to PDF
   exportMeetingsToPdf(): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/export/pdf`, {
      responseType: 'blob', // Specify the response type as Blob (binary data)
    });
  }

  // Export meetings to Excel
  exportMeetingsToExcel(): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/export/excel`, {
      responseType: 'blob', // Specify the response type as Blob (binary data)
    });
  }
}