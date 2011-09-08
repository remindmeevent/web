package jobs;

import java.util.List;

import org.joda.time.DateMidnight;

import models.Event;
import models.Reminder;
import models.User;
import play.Logger;
import play.Play;
import play.jobs.Every;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class TestDataLoaderJob extends Job {
	 public void doJob() {
		Logger.debug("TestDataLoaderJob.doJob()");
		if (Play.mode == Play.Mode.DEV) {
			User user = new User();
			user.email="birtday@mail.com";
			
			Event event = new Event();
			event.name = "My wife birthday";
			
			Reminder reminder = new Reminder();
			reminder.nextFireDate = new DateMidnight().toDate();

			user.addEvent(event);
			event.addReminder(reminder);
			
			user.save();
			event.save();
		}
	 }
}
