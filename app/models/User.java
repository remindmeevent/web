package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class User extends Model {

	public String email;
	
	@OneToMany(mappedBy="user")
	public List<Event> events = new ArrayList<Event>();
	
	public void addEvent(Event event) {
		event.user = this;
		events.add(event);
	}
	
}
