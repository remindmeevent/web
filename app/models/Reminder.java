package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.joda.time.DateMidnight;

import play.db.jpa.Model;

@Entity
public class Reminder extends Model {
	public Integer numberOfDaysBeforeEvent;
	public Date nextFireDate;
	
	@ManyToOne
	public Event event;
	
	
	public static List<Reminder> findFiringToday() {
		return find("nextFireDate", new DateMidnight().toDate()).fetch();
	}
}
