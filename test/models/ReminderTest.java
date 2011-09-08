package models;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Years;
import org.junit.Test;

import play.test.UnitTest;

public class ReminderTest extends UnitTest {

	@Test
	public void shouldComputeFiringDate() throws Exception {
		assertNextFireDate(6, 1, 5, 1, 1); // 06/06  -> five days before -> 01/06
		assertNextFireDate(1, 6, 5, 27, 5); // 01/06  -> five days before -> 27/05
	}

	private void assertNextFireDate(int dayOfMonth, int monthOfYear, int numberOfDaysBeforeEvent, int expectedDayOfMonth, int expectedMonthOfYear) {
		Event event = new Event();
		event.dayOfMonth = dayOfMonth;
		event.monthOfYear = monthOfYear;
		
		Reminder reminder = new Reminder();
		reminder.numberOfDaysBeforeEvent = numberOfDaysBeforeEvent;
		event.addReminder(reminder);
		
		reminder.computeNextFireDate();
		
		assertNotNull(reminder.nextFireDate);
		
		DateTime now = new DateTime();
		DateTime nextFireDate = new DateTime(reminder.nextFireDate.getTime());
		//Midnight
		assertEquals(new DateMidnight(nextFireDate), nextFireDate);
		
		// In the future
		assertTrue(now.isBefore(nextFireDate));
		
		// Less than a year between now and reminder
		assertEquals(0, Years.yearsBetween(now, nextFireDate).getYears());
		
		assertEquals(expectedDayOfMonth, nextFireDate.getDayOfMonth());
		assertEquals(expectedMonthOfYear, nextFireDate.getMonthOfYear());
	}
}
