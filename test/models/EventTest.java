package models;

import static org.junit.Assert.*;

import org.junit.Test;

import play.db.jpa.JPA;
import play.test.UnitTest;

public class EventTest extends UnitTest {
	
	@Test
	public void shouldCreateReminderBidirectionalLink() {
		Event event = new Event();
		Reminder reminder = new Reminder();
		event.addReminder(reminder);
		
		assertNotNull(reminder.event);
		assertTrue(event.reminders.contains(reminder));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void shouldCascadindSaveReminders() throws Exception {
		Event event = new Event();
		Reminder reminder = new Reminder();
		event.addReminder(reminder);
		
		event = event.save();
		clearJPASession();
		
		event = Event.findById(event.id);
		assertNotNull(event);
		assertFalse(event.reminders.isEmpty());
	}

}
