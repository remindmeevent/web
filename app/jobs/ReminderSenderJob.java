package jobs;

import java.util.List;

import models.Reminder;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.joda.time.DateMidnight;

import com.sun.org.apache.xerces.internal.impl.dv.xs.MonthDayDV;

import play.Logger;
import play.db.jpa.JPA;
import play.jobs.Every;
import play.jobs.Job;
import play.libs.Mail;

@Every("1d")
public class ReminderSenderJob extends Job {

	private static final String EMAIL_SUBJECT = "Remind";
	private static final String EMAIL_FROM = "yes@wecan.com";

	public void doJob() {
		Logger.debug("ReminderSenderJob.doJob()");

		List<Reminder> remindersToFire = Reminder.findFiringToday();
		for (Reminder reminder : remindersToFire) {
			Logger.debug("Sending reminder for event named %s to %s ", reminder.event.name, reminder.event.user.email);
			try {
				send(reminder);
				reminder.computeNextFireDate();
				reminder.save();
			} catch (EmailException e) {
				Logger.error("There was an error when trying to send remind " + reminder.id + " by mail : "
						+ e.getMessage());
			}
		}
	}

	protected void send(Reminder reminder) throws EmailException {
		sendByMail(reminder);
	}

	protected void sendByMail(Reminder reminder) throws EmailException {
		SimpleEmail email = createMail(reminder);
		Mail.send(email);
	}

	protected SimpleEmail createMail(Reminder reminder) throws EmailException {
		SimpleEmail email = new SimpleEmail();
		email.setFrom(EMAIL_FROM);
		email.addTo(reminder.event.user.email);
		email.setSubject(EMAIL_SUBJECT);
		email.setMsg(createRemindMessage(reminder));
		return email;
	}

	protected String createRemindMessage(Reminder reminder) {
		StringBuffer message = new StringBuffer();
		message.append("Hey ");
		message.append(reminder.event.user.email + ",");
		message.append("\n\n");
		message.append("Don't forget : ");
		message.append("\n");
		message.append(" - " + reminder.event.dayOfMonth + "/" + reminder.event.monthOfYear + " : "
				+ reminder.event.name);
		return message.toString();
	}
}
