package models;

import org.joda.time.DateMidnight;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.Years;
import org.junit.Test;

import play.test.UnitTest;
import util.BaseUnitTest;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;


public class ReminderTest extends BaseUnitTest {

	@Test
	public void shouldComputeFirstFiringDate() throws Exception {
		assertNextFireDate(6, 6, 5, 1, 6); // 06/06  -> five days before -> 01/06
		assertNextFireDate(1, 6, 5, 27, 5); // 01/06  -> five days before -> 27/05
	}
	
	@Test
	public void shouldComputeNextFireDate() throws Exception {
		DateMidnight now = new DateMidnight();
		Event event = new Event();
		event.dayOfMonth = now.getDayOfMonth();
		event.monthOfYear = now.getMonthOfYear();
		
		Reminder reminder = new Reminder();
		reminder.numberOfDaysBeforeEvent = 0;
		event.addReminder(reminder);
		
		reminder.nextFireDate = now.toDate();
		reminder.computeNextFireDate();
		DateTime nextFireDate = new DateTime(reminder.nextFireDate.getTime());
		MutableDateTime tmp = new MutableDateTime();
		tmp.add(Years.ONE);
		DateMidnight inOneYear = new DateMidnight(tmp);
		assertThat(nextFireDate).isEqualTo(inOneYear);
	}

	private void assertNextFireDate(int dayOfMonth, int monthOfYear, int numberOfDaysBeforeEvent, int expectedDayOfMonth, int expectedMonthOfYear) {
		Event event = new Event();
		event.dayOfMonth = dayOfMonth;
		event.monthOfYear = monthOfYear;
		
		Reminder reminder = new Reminder();
		reminder.numberOfDaysBeforeEvent = numberOfDaysBeforeEvent;
		event.addReminder(reminder);
		
		reminder.computeNextFireDate();
		
		assertThat(reminder.nextFireDate).isNotNull();
		
		DateTime now = new DateTime();
		DateTime nextFireDate = new DateTime(reminder.nextFireDate.getTime());
		//Midnight
		assertThat(nextFireDate).isEqualTo(new DateMidnight(nextFireDate));
		
		// In the future
		assertThat(nextFireDate.isBefore(now)).isFalse();
		
		// Less than a year between now and reminder
		assertThat(Years.yearsBetween(now, nextFireDate).getYears()).isEqualTo(0);
		
		assertThat(nextFireDate.getDayOfMonth()).isEqualTo(expectedDayOfMonth);
		assertThat(nextFireDate.getMonthOfYear()).isEqualTo(expectedMonthOfYear);
	}
	
	@Test
	public void nextFireDateCanBeTodayOnFirstFire () throws Exception {
		DateMidnight today = new DateMidnight();
		Event event = new Event();
		event.dayOfMonth = today.getDayOfMonth();
		event.monthOfYear = today.getMonthOfYear();
		
		Reminder reminder = new Reminder();
		reminder.numberOfDaysBeforeEvent = 0;
		event.addReminder(reminder);
		
		reminder.computeNextFireDate();
		assertThat(new DateMidnight(reminder.nextFireDate.getTime())).isEqualTo(today);
	}
}
