package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.joda.time.DateMidnight;
import org.joda.time.MutableDateTime;
import org.joda.time.Years;

import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Reminder extends Model {
	@Required @Min(0)
	public Integer numberOfDaysBeforeEvent;
	public Date nextFireDate;
	
	@ManyToOne
	public Event event;
	
	public static List<Reminder> findFiringToday() {
		return find("nextFireDate", new DateMidnight().toDate()).fetch();
	}
	
	public void computeNextFireDate() {
		DateMidnight today = new DateMidnight();
		MutableDateTime time = new MutableDateTime(today);
		
		time.setDayOfMonth(event.dayOfMonth);
		time.setMonthOfYear(event.monthOfYear);
		time.addDays(-1 * numberOfDaysBeforeEvent);
		
		boolean isFirstCompute = nextFireDate == null;
		if (isFirstCompute) {
			if (time.isBefore(today)) {
				time.add(Years.ONE);
			}
		} else if (!time.isAfter(today)) {
			time.add(Years.ONE);
		}
		nextFireDate = time.toDate();
		
	}
}
