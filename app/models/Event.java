package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import play.data.validation.Max;
import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Event extends Model{
	@Required
	public String name;
	
	@Required @Min(1) @Max(12)
	public Integer monthOfYear;
	
	@Required @Min(1) @Max(31)
	public Integer dayOfMonth;
	
	@ManyToOne
	public User user;
	
	@OneToMany(mappedBy="event", cascade=CascadeType.ALL, orphanRemoval=true)
	public List<Reminder> reminders = new ArrayList<Reminder>();
	
	public void addReminder(Reminder reminder) {
		reminder.event = this;
		reminders.add(reminder);
	}

}
