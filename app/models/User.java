package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import play.Play;
import play.db.jpa.Model;
import play.libs.Crypto;

@Entity
public class User extends Model {

	public String email;
	public String hashedPassword;
	@Transient
	public String password; 
	
	@OneToMany(mappedBy="user", cascade=CascadeType.REMOVE)
	public List<Event> events = new ArrayList<Event>();
	
	public void addEvent(Event event) {
		event.user = this;
		events.add(event);
	}
	
	public void hashPassword() {
		hashedPassword = hash(password);
	}

	private String hash(String string) {
		String salt = Play.configuration.getProperty("application.secret").substring(0, 16);
		return Crypto.passwordHash(salt + string);
	}
	
	public boolean authenticate(String submitedPassword) {
		return hashedPassword.equals(hash(submitedPassword));
	}
	
	
	
}
