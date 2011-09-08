package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Event extends Model{
	
	public String name;
	public Integer monthOfYear;
	public Integer dayOfMonth;
	@ManyToOne
	public User user;
	@OneToMany(mappedBy="event")
	public List<Reminder> reminders = new ArrayList<Reminder>();

}
