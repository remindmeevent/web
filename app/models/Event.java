package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import play.db.jpa.Model;

@Entity
public class Event extends Model{
	
	public String name;
	public Integer monthOfYear;
	public Integer dayOfMonth;
	@ManyToOne
	public User user;
	@OneToMany(mappedBy="event", cascade=CascadeType.ALL)
	public List<Reminder> reminders = new ArrayList<Reminder>();
	
	public void addReminder(Reminder reminder) {
		reminder.event = this;
		reminders.add(reminder);
	}

}
