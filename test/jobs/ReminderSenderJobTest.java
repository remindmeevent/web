package jobs;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;

import javax.mail.internet.InternetAddress;

import models.Event;
import models.Reminder;
import models.User;

import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;

public class ReminderSenderJobTest {

	private static final String EXPECTED_MESSAGE = "Hey small@head.com,\n\nDon't forget : \n - 5/10 : amazing event";
	private ReminderSenderJob reminderSenderJob = new ReminderSenderJob();

	@Test
	public void shouldCreateRemindMessage() {
		String message = reminderSenderJob.createRemindMessage(constructReminder());

		assertThat(message).isNotNull();
		assertThat(message).isEqualTo(EXPECTED_MESSAGE);

	}

	@Test
	public void shouldCreateMail() throws Exception {
		SimpleEmail email = reminderSenderJob.createMail(constructReminder());
		assertThat(email).isNotNull();
		assertThat(email.getFromAddress()).isEqualTo(new InternetAddress("yes@wecan.com"));
		assertThat(email.getToAddresses()).hasSize(1).contains(new InternetAddress("small@head.com"));
		assertThat(email.getSubject()).isEqualTo("Remind");
	}

	private Reminder constructReminder() {
		Reminder reminder = new Reminder();
		Event event = new Event();
		event.name = "amazing event";
		event.dayOfMonth = 5;
		event.monthOfYear = 10;
		User user = new User();
		user.email = "small@head.com";
		event.user = user;
		reminder.event = event;
		return reminder;
	}

}
