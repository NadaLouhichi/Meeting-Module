import { Component, OnInit } from '@angular/core';
import { AttendeeService } from '../../services/attendee.service';

@Component({
  selector: 'app-attendee',
  templateUrl: './attendee.component.html',
  styleUrls: ['./attendee.component.scss']
})
export class AttendeeComponent implements OnInit {
  attendee: any = {
    name: '',
    email: ''
  };

  attendees: any[] = []; // List of attendees
  meetingTitle: string = ''; // To store the meeting title for filtering

  constructor(private attendeeService: AttendeeService) { }

  ngOnInit(): void {
    this.loadAttendees(); // Load attendees on component initialization
  }

  // Load attendees by meeting title
  loadAttendees() {
    if (this.meetingTitle) {
      this.attendeeService.getAttendeesByMeetingTitle(this.meetingTitle)
        .subscribe(
          (data: any[]) => {
            this.attendees = data;
          },
          (error) => {
            console.error('Error loading attendees', error);
          }
        );
    }
  }

  // Add an attendee
  onSubmit() {
    this.attendeeService.addAttendee(this.meetingTitle, this.attendee)
      .subscribe(response => {
        console.log('Attendee added successfully', response);
        alert('Attendee added successfully!');
        this.loadAttendees(); // Reload attendees after adding
        this.resetForm(); // Reset the form
      }, error => {
        console.error('Error adding attendee', error);
        alert('Error adding attendee!');
      });
  }

  // Delete an attendee
  onDelete(name: string) {
    if (confirm('Are you sure you want to delete this attendee?')) {
      this.attendeeService.deleteAttendeeByName(name)
        .subscribe(response => {
          console.log('Attendee deleted successfully', response);
          alert('Attendee deleted successfully!');
          this.loadAttendees(); // Reload attendees after deletion
        }, error => {
          console.error('Error deleting attendee', error);
          alert('Error deleting attendee!');
        });
    }
  }

  // Update an attendee
  onEdit(attendee: any) {
    this.attendee = { ...attendee }; // Populate the form with the selected attendee data
  }

  // Reset the form
  resetForm() {
    this.attendee = {
      name: '',
      email: ''
    };
  }
}