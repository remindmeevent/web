package jobs;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

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
import util.Config;

@Every("1h")
public class ReminderSenderJob extends Job {

	private static final String EMAIL_SUBJECT = "Remind";
	
	public void doJob() {
		Logger.debug("ReminderSenderJob.doJob()");

		List<Reminder> remindersToFire = Reminder.findFiringToday();
		Logger.debug("Found %d reminders to send ", remindersToFire.size());
		for (Reminder reminder : remindersToFire) {
			Logger.debug("Sending reminder for event named %s to %s ", reminder.event.name, reminder.event.user.email);
			if (send(reminder)) {
				reminder.computeNextFireDate();
				reminder.save();
			} else {
				Logger.error("There was an error when trying to send reminder.id " + reminder.id + " by mail");
			}
		}
	}

	protected boolean send(Reminder reminder) {
		return sendByMail(reminder);
	}

	protected boolean sendByMail(Reminder reminder) {
		try {
			SimpleEmail email = createMail(reminder);
			return Mail.send(email).get();
		} catch (EmailException e) {
			return false;
		} catch (InterruptedException e) {
			return false;
		} catch (ExecutionException e) {
			return false;
		}
	}

	protected SimpleEmail createMail(Reminder reminder) throws EmailException {
		SimpleEmail email = new SimpleEmail();
		email.setFrom(Config.getEmailFromAddress(), Config.getEmailFromName());
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
