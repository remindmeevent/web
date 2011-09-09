package models;


import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import util.BaseUnitTest;

public class EventTest extends BaseUnitTest {
	
	@Test
	public void shouldCreateReminderBidirectionalLink() {
		Event event = new Event();
		Reminder reminder = new Reminder();
		event.addReminder(reminder);
		
		assertThat(reminder.event).isNotNull();
		assertThat(event.reminders).contains(reminder);
	}
	

	@Test
	public void shouldCascadindSaveReminders() throws Exception {
		Event event = new Event();
		Reminder reminder = new Reminder();
		event.addReminder(reminder);
		
		event = event.save();
		clearJPASession();
		
		event = Event.findById(event.id);
		assertThat(event).isNotNull();
		assertThat(event.reminders).isNotEmpty();
	}

}
