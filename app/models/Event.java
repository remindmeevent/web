package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Event extends Model{
	
	String name;
	Integer monthOfYear;
	Integer dayOfMonth;
	List<Reminder> reminders;

}
