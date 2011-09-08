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
			user.email="birtday@mail.com";
			
			Event event = new Event();
			event.name = "My wife birthday";
			
			MutableDateTime tomorrow = new MutableDateTime(new DateMidnight());
			Logger.debug("now : %s", tomorrow);
			tomorrow.add(Days.ONE);
			Logger.debug("Tomorrow : %s", tomorrow);
			
			event.dayOfMonth = tomorrow.getDayOfMonth();
			event.monthOfYear = tomorrow.getMonthOfYear();
			
			Reminder reminder = new Reminder();
			reminder.numberOfDaysBeforeEvent = 1 ;

			user.addEvent(event);
			event.addReminder(reminder);
			
			reminder.computeNextFireDate();
			
			user.save();
			event.save();
		}
	 }
}
