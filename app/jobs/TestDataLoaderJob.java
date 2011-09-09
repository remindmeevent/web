package jobs;

import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.Days;
import org.joda.time.MutableDateTime;

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
			user.email = "jsevellec@gmail.com";

			Event event = new Event();
			event.name = "My wife birthday";

			DateMidnight today = new DateMidnight();
			
			event.dayOfMonth = today.getDayOfMonth();
			event.monthOfYear = today.getMonthOfYear();

			Reminder reminder = new Reminder();
			reminder.numberOfDaysBeforeEvent = 0;
			reminder.nextFireDate = today.toDate();

			user.addEvent(event);
			event.addReminder(reminder);


			user.save();
			event.save();
		}
	}
}
