package jobs;

import java.util.List;

import models.Reminder;
import play.Logger;
import play.jobs.Every;
import play.jobs.Job;

@Every("10s")
public class ReminderSenderJob extends Job {
	 public void doJob() {
		Logger.debug("ReminderSenderJob.doJob()");

		 List<Reminder> remindersToFire = Reminder.findFiringToday();
		 for (Reminder reminder : remindersToFire) {
			Logger.debug("Sending reminder for event named %s to %s ", reminder.event.name, reminder.event.user.email);
		}
	 }
}
