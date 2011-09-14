package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import play.Play;
import play.data.validation.CheckWith;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.Min;
import play.data.validation.MinSize;
import play.data.validation.Password;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.libs.Crypto;

@Entity
public class User extends Model {

	@Email
	@Required
	@CheckWith(UniqueEmailCheck.class)
	public String email;
	public String hashedPassword;

	@Transient
	@Required
	@MinSize(6)
	@Equals("passwordConfirm")
	public String password;

	@Transient
	@Required
	@MinSize(6)
	@Equals("password")
	public String passwordConfirm;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	public List<Event> events = new ArrayList<Event>();
	public State state = State.NOT_CONFIRMED;

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

	public static boolean isEmailAlreadyUsed(String email) {
		return findByEmail(email) != null;
	}

	public static User findByEmail(String email) {
		return find("byEmail", email).first();
	}

	public enum State {
		NOT_CONFIRMED, 
		CONFIRMED

	}

}
