import { Component, OnInit } from '@angular/core';
import { MeetingService } from '../../services/meeting.service'; // Import the service

@Component({
  selector: 'app-meeting',
  templateUrl: './meeting.component.html', // Ensure this path is correct
  styleUrls: ['./meeting.component.scss'] // Ensure this path is correct
})
export class MeetingComponent implements OnInit {
  meeting: any = {
    title: '',
    date: null,
    location: '',
    description: ''
  };

  meetings: any[] = []; // List of meetings
  isEditMode: boolean = false; // To track if we are in edit mode
  editMeetingId: number | null = null; // To store the ID of the meeting being edited

  constructor(private meetingService: MeetingService) { } // Inject the service

  ngOnInit(): void {
    this.loadMeetings(); // Load meetings on component initialization
  }

  // Load all meetings
  loadMeetings() {
    this.meetingService.getMeetings().subscribe(
      (data: any[]) => {
        this.meetings = data;
      },
      (error) => {
        console.error('Error loading meetings', error);
      }
    );
  }

  // Submit the form (create or update a meeting)
  onSubmit() {
    if (this.isEditMode) {
      // If in edit mode, call the update method
      this.meetingService.updateMeetingById(this.editMeetingId!, this.meeting)
        .subscribe(response => {
          console.log('Meeting updated successfully', response);
          alert('Meeting updated successfully!');
          this.loadMeetings(); // Reload meetings after updating
          this.resetForm(); // Reset the form
        }, error => {
          console.error('Error updating meeting', error);
          alert('Error updating meeting!');
        });
    } else {
      // If not in edit mode, call the create method
      this.meetingService.createMeeting(this.meeting)
        .subscribe(response => {
          console.log('Meeting added successfully', response);
          alert('Meeting added successfully!');
          this.loadMeetings(); // Reload meetings after adding a new one
          this.resetForm(); // Reset the form
        }, error => {
          console.error('Error adding meeting', error);
          alert('Error adding meeting!');
        });
    }
  }

  // Edit a meeting
  onEdit(meeting: any) {
    this.isEditMode = true;
    this.editMeetingId = meeting.id;
    this.meeting = { ...meeting }; // Populate the form with the selected meeting data
  }

  // Delete a meeting
  onDelete(meeting: any) {
    if (confirm('Are you sure you want to delete this meeting?')) {
      this.meetingService.deleteMeetingById(meeting.id)
        .subscribe(response => {
          console.log('Meeting deleted successfully', response);
          alert('Meeting deleted successfully!');
          this.loadMeetings(); // Reload meetings after deletion
        }, error => {
          console.error('Error deleting meeting', error);
          alert('Error deleting meeting!');
        });
    }
  }

  // Reset the form
  resetForm() {
    this.meeting = {
      title: '',
      date: null,
      location: '',
      description: ''
    };
    this.isEditMode = false;
    this.editMeetingId = null;
  }
}